package software.simple.solutions.framework.core.service;

import java.util.concurrent.ConcurrentMap;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IMailService extends ISuperService {

	public void createRequestResetMailMessage(String username, String contextPath) throws FrameworkException;

	public void createRequestResetMailMessage(String username) throws FrameworkException;

	public void createResetFailedMailMessage(String username, String contextPath) throws FrameworkException;

	public void createResetPasswordSuccessMailMessage(String username);

	public boolean sendTestMail(String host, String port, String username, String password, String systemEmail,
			String subject, String message, String validateEmail) throws FrameworkException;

	void createImmediateMailMessage(Long mailTemplateId, String email,
			Long userId, ConcurrentMap<String, Object> placeholders) throws FrameworkException;

}
