package software.simple.solutions.framework.core.service;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IMessagePerLocaleService extends ISuperService {

	public <T> T getByMessageAndLocale(String messageKey, String language) throws FrameworkException;

}
