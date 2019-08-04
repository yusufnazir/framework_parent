package software.simple.solutions.framework.core.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.constants.MailTemplates;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.ApplicationUserRequestResetPassword;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SecurityProperty;
import software.simple.solutions.framework.core.repository.IApplicationUserRequestResetPasswordRepository;
import software.simple.solutions.framework.core.service.IApplicationUserRequestResetPasswordService;
import software.simple.solutions.framework.core.service.IApplicationUserService;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IMailService;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.util.Placeholders;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IApplicationUserRequestResetPasswordRepository.class)
public class ApplicationUserRequestResetPasswordService extends SuperService
		implements IApplicationUserRequestResetPasswordService {

	@Autowired
	private IApplicationUserService applicationUserService;

	@Autowired
	private IApplicationUserRequestResetPasswordRepository applicationUserRequestResetPasswordRepository;

	@Autowired
	private IPersonInformationService personInformationService;

	@Autowired
	private IConfigurationService configurationService;

	@Autowired
	private IMailService mailService;

	@Override
	public Boolean requestPasswordReset(String username) throws FrameworkException {
		if (StringUtils.isBlank(username)) {
			return false;
		}
		ApplicationUser applicationUser = applicationUserService.getByUsername(username);
		if (applicationUser != null && applicationUser.getActive()) {

			String email = personInformationService.getEmail(applicationUser.getPerson().getId());
			if (StringUtils.isNotBlank(email)) {

				ApplicationUserRequestResetPassword applicationUserRequestResetPassword = new ApplicationUserRequestResetPassword();
//				applicationUserRequestResetPassword.setUpdatedDate(LocalDateTime.now());
//				applicationUserRequestResetPassword.setUpdatedByUser(applicationUser);
				applicationUserRequestResetPassword.setApplicationUser(applicationUser);

				Configuration configuration = configurationService
						.getByCode(ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY);
				Long hours = 2L;
				if (configuration != null && configuration.getLong() == null
						&& configuration.getLong().compareTo(0L) != 0) {
					hours = configuration.getLong();
				}
				LocalDateTime validDateTime = LocalDateTime.now().plusHours(hours);
				applicationUserRequestResetPassword.setValidateDateTime(validDateTime);

				UUID randomUUID = UUID.randomUUID();
				String key = randomUUID.toString();
				applicationUserRequestResetPassword.setResetKey(randomUUID.toString());
				saveOrUpdate(applicationUserRequestResetPassword, true);

				Placeholders placeholders = Placeholders.build().addApplicationUser(applicationUser)
						.addRecipient(applicationUser.getPerson()).addResetPasswordToken(key);
				mailService.createImmediateMailMessage(MailTemplates.SEND_REQUEST_PASSWORD_RESET, email,
						applicationUser.getId(), placeholders.getMap());
				return true;
			}
		}
		return false;
	}

	@Override
	public SecurityValidation isResetPasswordValid(String resetPasswordKey) throws FrameworkException {
		SecurityValidation loginValidation = new SecurityValidation(true);
		if (StringUtils.isBlank(resetPasswordKey)) {
			loginValidation.setSuccess(false);
			loginValidation.setMessageKey(SecurityProperty.RESET_PASSWORD_INVALID_CREDENTIALS);
			return loginValidation;
		}
		ApplicationUserRequestResetPassword applicationUserRequestResetPassword = getByUserByResetKey(resetPasswordKey);
		if (applicationUserRequestResetPassword == null) {
			loginValidation.setSuccess(false);
			loginValidation.setMessageKey(SecurityProperty.RESET_PASSWORD_INVALID_CREDENTIALS);
			return loginValidation;
		}

		ApplicationUser applicationUser = applicationUserRequestResetPassword.getApplicationUser();
		if (applicationUser == null) {
			loginValidation.setSuccess(false);
			loginValidation.setMessageKey(SecurityProperty.RESET_PASSWORD_INVALID_CREDENTIALS);
			return loginValidation;
		}

		String resetKey = applicationUserRequestResetPassword.getResetKey();
		if (!resetPasswordKey.equals(resetKey)) {
			loginValidation.setSuccess(false);
			loginValidation.setMessageKey(SecurityProperty.RESET_PASSWORD_INVALID_CREDENTIALS);
			return loginValidation;
		}

		LocalDateTime validateDateTime = applicationUserRequestResetPassword.getValidateDateTime();
		if (validateDateTime == null) {
			loginValidation.setSuccess(false);
			loginValidation.setMessageKey(SecurityProperty.RESET_PASSWORD_INVALID_CREDENTIALS);
			return loginValidation;
		}

		if (LocalDateTime.now().isAfter(validateDateTime)) {
			loginValidation.setSuccess(false);
			loginValidation.setMessageKey(SecurityProperty.RESET_PASSWORD_EXPIRED_SECURITY_TOKEN);
			return loginValidation;
		}

		return loginValidation;
	}

	private ApplicationUserRequestResetPassword getByUser(Long userId) {
		return applicationUserRequestResetPasswordRepository.getByUser(userId);
	}

	@Override
	public ApplicationUserRequestResetPassword getByUserByResetKey(String resetPasswordKey) throws FrameworkException {
		return applicationUserRequestResetPasswordRepository.getByUserByResetKey(resetPasswordKey);
	}

}
