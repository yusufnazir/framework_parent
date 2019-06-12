package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.service.IApplicationUserService;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.valueobjects.PasswordChangeVO;

public class ApplicationUserServiceFacade extends SuperServiceFacade<IApplicationUserService>
		implements IApplicationUserService {

	public static final long serialVersionUID = 2921037060748131115L;

	public ApplicationUserServiceFacade(UI ui, Class<IApplicationUserService> s) {
		super(ui, s);
	}

	public static ApplicationUserServiceFacade get(UI ui) {
		return new ApplicationUserServiceFacade(ui, IApplicationUserService.class);
	}

	@Override
	public SecurityValidation validateLogin(String username, String password) throws FrameworkException {
		return service.validateLogin(username, password);
	}

	@Override
	public SecurityValidation validateChangePassword(Long userId, String password, String newPassword,
			String confirmPassword) throws FrameworkException {
		return service.validateChangePassword(userId, password, newPassword, confirmPassword);
	}

	@Override
	public ApplicationUser getByUsername(String username) throws FrameworkException {
		return service.getByUsername(username);
	}

	@Override
	public void changePassword(PasswordChangeVO changePasswordVO) throws FrameworkException {
		service.changePassword(changePasswordVO);
	}

	@Override
	public Boolean isEmailUnique(String email) {
		return service.isEmailUnique(email);
	}

	@Override
	public Boolean isUsernameUnique(String username) {
		return service.isUsernameUnique(username);
	}

	@Override
	public boolean updateAlias(Long id, String alias) throws FrameworkException {
		return service.updateAlias(id, alias);
	}

	@Override
	public ApplicationUser changePassword(Long userId, String newPassword) throws FrameworkException {
		return service.changePassword(userId, newPassword);
	}

	@Override
	public ApplicationUser resetUserPassword(Long userId, Long updatedBy) throws FrameworkException {
		return service.resetUserPassword(userId, updatedBy);
	}

	@Override
	public SecurityValidation resetUserPasswordByKey(String resetPasswordKey, String newPassword)
			throws FrameworkException {
		return service.resetUserPasswordByKey(resetPasswordKey, newPassword);
	}

	@Override
	public SecurityValidation resetUserPassword(Long userId, String password, String repeatedPassword)
			throws FrameworkException {
		return service.resetUserPassword(userId, password, repeatedPassword);
	}

	@Override
	public List<ApplicationUser> findAll() throws FrameworkException {
		return service.findAll();
	}

	@Override
	public void removeOauthAccessToken(String authenticationId) throws FrameworkException {
		service.removeOauthAccessToken(authenticationId);
	}

}
