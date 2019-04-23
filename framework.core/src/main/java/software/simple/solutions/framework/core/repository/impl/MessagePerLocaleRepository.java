package software.simple.solutions.framework.core.repository.impl;

import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IMessagePerLocaleRepository;

@Repository
public class MessagePerLocaleRepository extends GenericRepository implements IMessagePerLocaleRepository {

	@Override
	public <T> T getByMessageAndLocale(String messageKey, String language) throws FrameworkException {
		String query = "from MessagePerLocale where message.code=:messageKey and lower(language.code) = :language";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("messageKey", messageKey);
		paramMap.put("language", language.toLowerCase());
		return getByQuery(query, paramMap);
	}
}
