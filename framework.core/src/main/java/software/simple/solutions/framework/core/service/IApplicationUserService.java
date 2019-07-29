package software.simple.solutions.framework.core.service;

import java.util.List;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.valueobjects.ApplicationUserVO;
import software.simple.solutions.framework.core.valueobjects.PasswordChangeVO;

public interface IApplicationUserService extends ISuperService {

	/**
	 * Validate the user login credentials.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws FrameworkException
	 */
	SecurityValidation validateLogin(String username, String password) throws FrameworkException;

	SecurityValidation validateChangePassword(Long userId, String password, String newPassword, String confirmPassword)
			throws FrameworkException;

	/**
	 * Retrieve the user by its username.
	 * 
	 * @param username
	 * @return
	 * @throws FrameworkException
	 */
	ApplicationUser getByUsername(String username) throws FrameworkException;

	void changePassword(PasswordChangeVO changePasswordVO) throws FrameworkException;

	Boolean isEmailUnique(String email);

	Boolean isUsernameUnique(String username);

	boolean updateAlias(Long id, String alias) throws FrameworkException;

	ApplicationUser changePassword(Long userId, String newPassword) throws FrameworkException;

	ApplicationUser resetUserPassword(Long userId, Long updatedBy) throws FrameworkException;

	SecurityValidation resetUserPasswordByKey(String resetPasswordKey, String newPassword) throws FrameworkException;

	SecurityValidation resetUserPassword(Long userId, String password, String repeatedPassword)
			throws FrameworkException;

	List<ApplicationUser> findAll() throws FrameworkException;

	void removeOauthAccessToken(String authenticationId) throws FrameworkException;

	void updatePasswordForLdapAccess(Long userId, String password) throws FrameworkException;

	SecurityValidation registerUser(ApplicationUserVO vo) throws FrameworkException;

	void sendRegistrationMailToNewUser(ApplicationUser applicationUser, ApplicationUserVO vo) throws FrameworkException;

}
