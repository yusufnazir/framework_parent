package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.entities.ApplicationUserRequestResetPassword;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IApplicationUserRequestResetPasswordRepository extends IGenericRepository {

	ApplicationUserRequestResetPassword getByUser(Long userId);

	ApplicationUserRequestResetPassword updateUserResetKey(Long userId, String resetKey);

	ApplicationUserRequestResetPassword getByUserByResetKey(String resetPasswordKey) throws FrameworkException;

}
