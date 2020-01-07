package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.ApplicationUserRequestResetPassword;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.service.IApplicationUserRequestResetPasswordService;

public class ApplicationUserRequestResetPasswordServiceFacade
		extends SuperServiceFacade<IApplicationUserRequestResetPasswordService>
		implements IApplicationUserRequestResetPasswordService {

	public static final long serialVersionUID = -1637019968960438879L;

	public ApplicationUserRequestResetPasswordServiceFacade(UI ui, Class<IApplicationUserRequestResetPasswordService> s) {
		super(ui, s);
	}

	public static ApplicationUserRequestResetPasswordServiceFacade get(UI ui) {
		return new ApplicationUserRequestResetPasswordServiceFacade(ui,
				IApplicationUserRequestResetPasswordService.class);
	}

	@Override
	public Boolean requestPasswordReset(String username) throws FrameworkException {
		return service.requestPasswordReset(username);
	}

	@Override
	public SecurityValidation isResetPasswordValid(String resetPasswordKey) throws FrameworkException {
		return service.isResetPasswordValid(resetPasswordKey);
	}

	@Override
	public ApplicationUserRequestResetPassword getByUserByResetKey(String resetPasswordKey) throws FrameworkException {
		return service.getByUserByResetKey(resetPasswordKey);
	}

}
