package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IMessagePerLocaleRepository extends IGenericRepository {

	public <T> T getByMessageAndLocale(String messageKey, String language) throws FrameworkException;

}
