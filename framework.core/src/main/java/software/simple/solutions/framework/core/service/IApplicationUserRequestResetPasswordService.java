package software.simple.solutions.framework.core.service;

import software.simple.solutions.framework.core.entities.ApplicationUserRequestResetPassword;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;

public interface IApplicationUserRequestResetPasswordService extends ISuperService {

	Boolean requestPasswordReset(String username) throws FrameworkException;

	SecurityValidation isResetPasswordValid(String resetPasswordKey) throws FrameworkException;

	ApplicationUserRequestResetPassword getByUserByResetKey(String resetPasswordKey) throws FrameworkException;

}
