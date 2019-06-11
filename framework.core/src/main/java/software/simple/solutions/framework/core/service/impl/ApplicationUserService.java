package software.simple.solutions.framework.core.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.constants.MailTemplates;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.ApplicationUserRequestResetPassword;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IApplicationUserRepository;
import software.simple.solutions.framework.core.security.PasswordUtil;
import software.simple.solutions.framework.core.service.IApplicationUserRequestResetPasswordService;
import software.simple.solutions.framework.core.service.IApplicationUserService;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IMailService;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.util.ActiveDirectoryConnectionUtils;
import software.simple.solutions.framework.core.util.Placeholders;
import software.simple.solutions.framework.core.util.StringUtil;
import software.simple.solutions.framework.core.util.ThreadAttributes;
import software.simple.solutions.framework.core.util.ThreadContext;
import software.simple.solutions.framework.core.valueobjects.ApplicationUserVO;
import software.simple.solutions.framework.core.valueobjects.PasswordChangeVO;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = IApplicationUserRepository.class)
public class ApplicationUserService extends SuperService implements IApplicationUserService {

	@Autowired
	private IApplicationUserRepository applicationUserRepository;

	@Autowired
	private IApplicationUserRequestResetPasswordService applicationUserRequestResetPasswordService;

	@Autowired
	private IPersonInformationService personInformationService;

	@Autowired
	private IMailService mailService;

	@Autowired
	private IConfigurationService configurationService;
	
	@Override
	public <T, R extends SuperVO> T updateSingle(R entityVO) throws FrameworkException {
		ApplicationUserVO vo = (ApplicationUserVO) entityVO;

		if (vo.getPersonId() == null) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED, new Arg().key(PersonProperty.PERSON));
		}
		if (StringUtils.isBlank(vo.getUsername())) {
			throw new FrameworkException(SystemMessageProperty.FIELD_IS_REQUIRED,
					new Arg().key(ApplicationUserProperty.USERNAME));
		}

		if (vo.isNew() && !vo.getUseLdap()) {
			SecurityValidation securityValidation = validatePasswords(vo.getPassword(), vo.getPasswordConfirm());
			if (!securityValidation.isSuccess()) {
				throw new FrameworkException(SystemMessageProperty.PASSWORD_VALIDATION,
						new Arg().key(securityValidation.getMessageKey()));
			}
		}

		ApplicationUser applicationUser = null;
		if (vo.isNew()) {
			applicationUser = new ApplicationUser();

			PasswordUtil passwordUtil = new PasswordUtil();
			String encryptedPassword = passwordUtil.getEncryptedPassword(vo.getPassword());
			applicationUser.setPassword(encryptedPassword);
			applicationUser.setPasswordChangeDate(LocalDateTime.now());
		} else {
			applicationUser = getById(ApplicationUser.class, vo.getId());
		}
		applicationUser.setUsername(vo.getUsername().toLowerCase());
		// applicationUser.setUpdatedDate(vo.getUpdatedDate());
		// applicationUser.setUpdatedByUser(get(ApplicationUser.class,
		// vo.getUpdatedBy()));

		applicationUser.setActive(vo.getActive());
		applicationUser.setResetPassword(vo.getResetPassword());
		applicationUser.setAlias(vo.getAlias());
		applicationUser.setPerson(getById(Person.class, vo.getPersonId()));
		applicationUser.setUseLdap(vo.getUseLdap());

		applicationUser = saveOrUpdate(applicationUser, vo.isNew());
		updateUserEmail(vo);

		if (vo.isNew()) {
			sendPasswordToNewUser(applicationUser, vo);
		}

		return (T) applicationUser;
	}

	private void sendPasswordToNewUser(ApplicationUser applicationUser, ApplicationUserVO vo)
			throws FrameworkException {
		String email = personInformationService.getEmail(applicationUser.getPerson().getId());
		Configuration configuration = configurationService.getByCode(ConfigurationProperty.SMTP_ENABLE);
		if (configuration != null && configuration.getBoolean() && StringUtils.isNotBlank(email)) {
			Placeholders placeholders = Placeholders.build().addApplicationUser(applicationUser)
					.addRecipient(applicationUser.getPerson()).addPassword(vo.getPassword());
			mailService.createImmediateMailMessage(MailTemplates.SEND_PASSWORD_TO_NEW_USER, email, vo.getUpdatedBy(),
					placeholders.getMap());
		}
	}

	private void updateUserEmail(ApplicationUserVO vo) throws FrameworkException {
		PersonInformationVO personInformationVO = new PersonInformationVO();
		personInformationVO.setPersonId(vo.getPersonId());
		personInformationVO.setPrimaryEmail(vo.getEmail());
		personInformationVO.setUpdatedBy(vo.getUpdatedBy());
		// personInformationVO.setUpdatedDate(vo.getUpdatedDate());
		personInformationService.updateApplicationUserEmail(personInformationVO);
	}

	private SecurityValidation validatePasswords(String password, String confirmPassword) throws FrameworkException {
		List<Configuration> passwordSecurityConfiguration = configurationService.getPasswordSecurityConfiguration();
		Map<String, Configuration> configurationMap = passwordSecurityConfiguration.parallelStream()
				.collect(Collectors.toMap(Configuration::getCode, configuration -> configuration));

		// are passwords filled
		if (StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_CANNOT_BE_EMPTY);
		}

		// are passwords identical
		if (StringUtils.compare(password, confirmPassword) != 0) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_MUST_MATCH);
		}

		// minimum length
		Configuration configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_MINIMUM_LENGTH);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0
				&& password.length() < configuration.getLong()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_MINIMUM_LENGTH_LIMIT);
		}

		// maximum length
		configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_MAXIMUM_LENGTH);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0
				&& password.length() > configuration.getLong().longValue()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_MAXIMUM_LENGTH_LIMIT);
		}

		// uppercase
		configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_UPPERCASE);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0 && password.chars()
				.filter(s -> Character.isUpperCase(s)).count() < configuration.getLong().longValue()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_UPPERCASE_LIMIT);
		}

		// special
		configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_SPECIAL);
		Stream<Character> passwordChars = password.codePoints().mapToObj(c -> (char) c);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0 && passwordChars
				.filter(p -> StringUtil.containsSpecial(p)).count() < configuration.getLong().longValue()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_SPECIAL_CHARACTERS_LIMIT);
		}

		// digits
		configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_DIGITS);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0
				&& password.chars().filter(s -> Character.isDigit(s)).count() < configuration.getLong().longValue()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_DIGITS_LIMIT);
		}

		// used history
		// configuration =
		// configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_USED_HISTORY);
		// if (configuration != null && configuration.getLong().compareTo(0L) !=
		// 0
		// && password.chars().filter(s -> Character.isDigit(s)).count() <
		// configuration.getLong().longValue()) {
		// return
		// SecurityValidation.build(ApplicationUserProperty.PASSWORD_HISTORY_LIMIT);
		// }

		return new SecurityValidation();
	}

	@Override
	public SecurityValidation validateLogin(String username, String password) throws FrameworkException {
		if (StringUtils.isBlank(username)) {
			/*
			 * check is username is filled and not blank.
			 */
			return SecurityValidation.build(ApplicationUserProperty.LOGIN_INVALID_CREDENTIALS);
		}

		if (StringUtils.isBlank(password)) {
			/*
			 * check is password is filled and not blank.
			 */
			return SecurityValidation.build(ApplicationUserProperty.LOGIN_INVALID_CREDENTIALS);
		}

		String applicationUserName = null;
		if (username.contains("@")) {
			applicationUserName = username.substring(0, username.indexOf('@'));
		} else if (username.contains("\\")) {
			applicationUserName = username.substring(username.indexOf('\\') + 1, username.length());
		}

		ApplicationUser applicationUser = getByUsername(applicationUserName == null ? username : applicationUserName);
		if (applicationUser == null) {
			/**
			 * Invalid username.
			 */
			return SecurityValidation.build(ApplicationUserProperty.LOGIN_INVALID_CREDENTIALS);
		}

		Boolean useLdap = applicationUser.getUseLdap();
		if (applicationUser.getId().compareTo(1L) != 0 && useLdap) {
			Configuration configuration = configurationService
					.getByCode(ConfigurationProperty.LDAP_CONFIGURATION_USE_LDAP);
			if (configuration != null && configuration.getBoolean()) {
				Configuration hostConfig = configurationService
						.getByCode(ConfigurationProperty.LDAP_CONFIGURATION_HOST);
				ActiveDirectoryConnectionUtils activeDirectoryConnectionUtils = new ActiveDirectoryConnectionUtils();
				boolean validated = activeDirectoryConnectionUtils.createContext(hostConfig.getValue(), username,
						password);
				if (validated) {
					return new SecurityValidation(validated);
				} else {
					return SecurityValidation.build(ApplicationUserProperty.LOGIN_INVALID_CREDENTIALS);
				}
			}
		}

		PasswordUtil passwordUtil = new PasswordUtil();
		Boolean correctPassword = passwordUtil.isCorrectPassword(password, applicationUser.getPassword());
		if (!correctPassword) {
			/**
			 * Is the password correct for the username.
			 */
			return SecurityValidation.build(ApplicationUserProperty.LOGIN_INVALID_CREDENTIALS);
		}

		Boolean active = applicationUser.getActive();
		if (!active) {
			return SecurityValidation.build(ApplicationUserProperty.LOGIN_USER_IS_INACTIVE);
		}

		/*
		 * TODO: check if account should be locked after a certain count of
		 * invalid logins. Reset invalid login counts after a successful login.
		 * Check if password has expired and needs to be changed. Check if there
		 * is a threshhold before a password can be used again.
		 */

		return new SecurityValidation(true);
	}

	@Override
	public ApplicationUser getByUsername(String username) throws FrameworkException {
		ApplicationUser applicationUser = applicationUserRepository.getByUserName(username);
		return applicationUser;

	}

	@Override
	public void changePassword(PasswordChangeVO vo) throws FrameworkException {
		ApplicationUser applicationUser = getByUsername(vo.getUsername());
		applicationUser.setPassword(vo.getNewPassword());
		applicationUser.setPasswordChangeDate(LocalDateTime.now());

		// applicationUser.setUpdatedByUser(get(ApplicationUser.class,
		// vo.getUpdatedBy()));
		// applicationUser.setUpdatedByUser(get(ApplicationUser.class,
		// vo.getUpdatedBy()));

		saveOrUpdate(applicationUser, false);
	}

	@Override
	public Boolean isEmailUnique(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isUsernameUnique(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateAlias(Long id, String alias) throws FrameworkException {
		ApplicationUser applicationUser = getById(ApplicationUser.class, id);
		applicationUser.setAlias(alias);
		saveOrUpdate(applicationUser, false);
		return true;
	}

	@Override
	public ApplicationUser changePassword(Long userId, String newPassword) throws FrameworkException {
		ApplicationUser applicationUser = get(ApplicationUser.class, userId);
		applicationUser.setResetPassword(false);
		applicationUser.setPasswordChangeDate(LocalDateTime.now());
		// applicationUser.setUpdatedByUser(applicationUser);
		String encryptedPassword = new PasswordUtil().getEncryptedPassword(newPassword);
		applicationUser.setPassword(encryptedPassword);
		return saveOrUpdate(applicationUser, false);
	}

	@Override
	public ApplicationUser resetUserPassword(Long userId, Long updatedBy) throws FrameworkException {
		ApplicationUser applicationUser = getById(ApplicationUser.class, userId);
		if (applicationUser != null) {

			PasswordUtil passwordUtil = new PasswordUtil();
			String randomPassword = String.valueOf(passwordUtil.getRandomPassword(8, 8, 2, 2, 2));

			applicationUser.setPassword(passwordUtil.getEncryptedPassword(randomPassword));
			// applicationUser.setUpdatedDate(LocalDateTime.now());
			// applicationUser.setUpdatedByUser(getById(ApplicationUser.class,
			// updatedBy));
			applicationUser.setActive(true);
			applicationUser.setResetPassword(true);
			saveOrUpdate(applicationUser, false);

			String email = personInformationService.getEmail(applicationUser.getPerson().getId());
			if (StringUtils.isNotBlank(email)) {
				Placeholders placeholders = Placeholders.build().addApplicationUser(applicationUser)
						.addRecipient(applicationUser.getPerson()).addPassword(randomPassword);
				mailService.createImmediateMailMessage(MailTemplates.SEND_PASSWORD_TO_NEW_USER, email, updatedBy,
						placeholders.getMap());
			}
		}
		return applicationUser;
	}

	@Override
	public SecurityValidation resetUserPasswordByKey(String resetPasswordKey, String newPassword)
			throws FrameworkException {

		SecurityValidation securityValidation = applicationUserRequestResetPasswordService
				.isResetPasswordValid(resetPasswordKey);
		if (!securityValidation.isSuccess()) {
			return securityValidation;
		}

		ApplicationUserRequestResetPassword applicationUserRequestResetPassword = applicationUserRequestResetPasswordService
				.getByUserByResetKey(resetPasswordKey);
		ApplicationUser applicationUser = applicationUserRequestResetPassword.getApplicationUser();

//		ThreadContext.add(new ThreadAttributes(applicationUser.getId(), applicationUser.getUsername()));

		PasswordUtil passwordUtil = new PasswordUtil();
		String encryptedPassword = passwordUtil.getEncryptedPassword(newPassword);

		applicationUser.setPassword(encryptedPassword);
		applicationUser.setPasswordChangeDate(LocalDateTime.now());
		saveOrUpdate(applicationUser, false);

		applicationUserRequestResetPassword.setActive(false);
		saveOrUpdate(applicationUserRequestResetPassword, false);
		securityValidation = new SecurityValidation(true);

		return securityValidation;
	}

	@Override
	public SecurityValidation validateChangePassword(Long userId, String password, String newPassword,
			String confirmPassword) throws FrameworkException {
		List<Configuration> passwordSecurityConfiguration = configurationService.getPasswordSecurityConfiguration();
		Map<String, Configuration> configurationMap = passwordSecurityConfiguration.parallelStream()
				.collect(Collectors.toMap(Configuration::getCode, configuration -> configuration));

		// are passwords filled
		if (StringUtils.isBlank(password) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(confirmPassword)) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_CANNOT_BE_EMPTY);
		}

		// are current and new passwords identical
		if (StringUtils.compare(password, newPassword) == 0) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORDS_MUST_NOT_MATCH);
		}

		// are passwords identical
		if (StringUtils.compare(newPassword, confirmPassword) != 0) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_MUST_MATCH);
		}

		ApplicationUser applicationUser = get(ApplicationUser.class, userId);
		if (applicationUser == null) {
			/**
			 * Invalid username.
			 */
			return SecurityValidation.build(ApplicationUserProperty.LOGIN_INVALID_CREDENTIALS);
		}

		PasswordUtil passwordUtil = new PasswordUtil();
		Boolean correctPassword = passwordUtil.isCorrectPassword(password, applicationUser.getPassword());
		if (!correctPassword) {
			/**
			 * Is the password correct for the username.
			 */
			return SecurityValidation.build(ApplicationUserProperty.LOGIN_INVALID_CREDENTIALS);
		}

		// minimum length
		Configuration configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_MINIMUM_LENGTH);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0
				&& password.length() < configuration.getLong()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_MINIMUM_LENGTH_LIMIT);
		}

		// maximum length
		configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_MAXIMUM_LENGTH);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0
				&& password.length() > configuration.getLong().longValue()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_MAXIMUM_LENGTH_LIMIT);
		}

		// uppercase
		configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_UPPERCASE);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0 && password.chars()
				.filter(s -> Character.isUpperCase(s)).count() < configuration.getLong().longValue()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_UPPERCASE_LIMIT);
		}

		// special
		configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_SPECIAL);
		Stream<Character> passwordChars = password.codePoints().mapToObj(c -> (char) c);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0 && passwordChars
				.filter(p -> StringUtil.containsSpecial(p)).count() < configuration.getLong().longValue()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_SPECIAL_CHARACTERS_LIMIT);
		}

		// digits
		configuration = configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_NO_OF_DIGITS);
		if (configuration != null && configuration.getLong().compareTo(0L) != 0
				&& password.chars().filter(s -> Character.isDigit(s)).count() < configuration.getLong().longValue()) {
			return SecurityValidation.build(ApplicationUserProperty.PASSWORD_DIGITS_LIMIT);
		}

		// used history
		// configuration =
		// configurationMap.get(ConfigurationProperty.PASSWORD_SECURITY_USED_HISTORY);
		// if (configuration != null && configuration.getLong().compareTo(0L) !=
		// 0
		// && password.chars().filter(s -> Character.isDigit(s)).count() <
		// configuration.getLong().longValue()) {
		// return
		// SecurityValidation.build(ApplicationUserProperty.PASSWORD_HISTORY_LIMIT);
		// }

		return new SecurityValidation();
	}

	@Override
	public SecurityValidation resetUserPassword(Long userId, String password, String repeatedPassword)
			throws FrameworkException {
		SecurityValidation securityValidation = validatePasswords(password, repeatedPassword);
		if (securityValidation.isSuccess()) {
			changePassword(userId, password);
		}
		return securityValidation;
	}

	@Override
	public List<ApplicationUser> findAll() throws FrameworkException {
		return applicationUserRepository.findAll();
	}

	@Override
	public void removeOauthAccessToken(String authenticationId) throws FrameworkException {
		applicationUserRepository.removeOauthAccessToken(authenticationId);
	}

}
