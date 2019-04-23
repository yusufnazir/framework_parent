package software.simple.solutions.framework.core.service;

import javax.transaction.Transactional;

import software.simple.solutions.framework.core.constants.MailState;
import software.simple.solutions.framework.core.entities.GeneratedMail;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

@Transactional
public interface IGeneratedMailService extends ISuperService {

	public default GeneratedMail setError(Long id, String errorMessage) throws FrameworkException {
		GeneratedMail generatedMail = get(GeneratedMail.class, id);
		generatedMail.setErrorMessage(errorMessage);
		generatedMail.setState(MailState.ERROR);
		generatedMail = saveOrUpdate(generatedMail, false);
		return generatedMail;
	}

}
