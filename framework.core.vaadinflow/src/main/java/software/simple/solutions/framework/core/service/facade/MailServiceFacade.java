package software.simple.solutions.framework.core.service.facade;

import java.util.concurrent.ConcurrentMap;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IMailService;

public class MailServiceFacade extends SuperServiceFacade<IMailService> implements IMailService {

	public static final long serialVersionUID = 1080438957363880088L;

	public MailServiceFacade(UI ui, Class<IMailService> s) {
		super(ui, s);
	}

	public static MailServiceFacade get(UI ui) {
		return new MailServiceFacade(ui, IMailService.class);
	}

	@Override
	public void createRequestResetMailMessage(String username, String contextPath) throws FrameworkException {
		service.createRequestResetMailMessage(username, contextPath);
	}

	@Override
	public void createRequestResetMailMessage(String username) throws FrameworkException {
		service.createRequestResetMailMessage(username);
	}

	@Override
	public void createResetFailedMailMessage(String username, String contextPath) throws FrameworkException {
		service.createResetFailedMailMessage(username, contextPath);
	}

	@Override
	public void createResetPasswordSuccessMailMessage(String username) {
		service.createResetPasswordSuccessMailMessage(username);
	}

	@Override
	public boolean sendTestMail(String host, String port, String username, String password, String systemEmail,
			String subject, String message, String validateEmail) throws FrameworkException {
		return service.sendTestMail(host, port, username, password, systemEmail, subject, message, validateEmail);
	}

	@Override
	public void createImmediateMailMessage(Long mailTemplateId, String email, Long userId,
			ConcurrentMap<String, Object> placeholders) throws FrameworkException {
		service.createImmediateMailMessage(mailTemplateId, email, userId, placeholders);
	}
}
