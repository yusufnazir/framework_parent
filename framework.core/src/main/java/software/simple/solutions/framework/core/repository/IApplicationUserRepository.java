package software.simple.solutions.framework.core.repository;

import java.util.List;

import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IApplicationUserRepository extends IGenericRepository {

	ApplicationUser getByUserName(String username) throws FrameworkException;

	public List<ApplicationUser> findAll() throws FrameworkException;

	void removeOauthAccessToken(String authenticationId) throws FrameworkException;

}
