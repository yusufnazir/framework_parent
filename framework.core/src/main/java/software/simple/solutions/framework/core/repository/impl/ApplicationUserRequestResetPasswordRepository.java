package software.simple.solutions.framework.core.repository.impl;

import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.ApplicationUserRequestResetPassword;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IApplicationUserRequestResetPasswordRepository;

@Repository
public class ApplicationUserRequestResetPasswordRepository extends GenericRepository
		implements IApplicationUserRequestResetPasswordRepository {

	@Override
	public ApplicationUserRequestResetPassword getByUser(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationUserRequestResetPassword updateUserResetKey(Long userId, String resetKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationUserRequestResetPassword getByUserByResetKey(String resetPasswordKey) throws FrameworkException {
		String query = "from ApplicationUserRequestResetPassword where resetKey=:resetKey";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("resetKey", resetPasswordKey);
		return getByQuery(query, paramMap);
	}

}
