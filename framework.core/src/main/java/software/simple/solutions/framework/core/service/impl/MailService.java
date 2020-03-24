package software.simple.solutions.framework.core.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import software.simple.solutions.framework.core.constants.MailState;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.GeneratedMail;
import software.simple.solutions.framework.core.entities.MailTemplate;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.mailing.Mail;
import software.simple.solutions.framework.core.mailing.MailSettings;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IApplicationUserRepository;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IGeneratedMailService;
import software.simple.solutions.framework.core.service.IMailService;
import software.simple.solutions.framework.core.service.IMailTemplateService;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.valueobjects.GeneratedMailVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
public class MailService extends SuperService implements IMailService {

	protected static final Logger logger = LogManager.getLogger(MailService.class);

	@Autowired
	private IApplicationUserRepository applicationUserRepository;

	@Autowired
	private IPersonInformationService personContactService;

	@Autowired
	private IConfigurationService configurationService;

	@Autowired
	private IMailTemplateService mailTemplateService;

	@Autowired
	private IGeneratedMailService generatedMailService;

	@Override
	public void createRequestResetMailMessage(String username, String contextPath) throws FrameworkException {
		ApplicationUser applicationUser = applicationUserRepository.getByUserName(username);

		if (applicationUser != null && applicationUser.getPerson() != null) {

			// String resetPasswordKey = generateResetPasswordKey(username);
			String resetPasswordKey = "";
			String url = contextPath + "/" + username + "/" + resetPasswordKey;

			String email = personContactService.getEmail(applicationUser.getPerson().getId());

			String subject = "Koopapp password reset requested";
			String messageBody = "We received a request to reset the password associated with this e-mail address. "
					+ "If you made this request, please follow the instructions below."
					+ "<br><br>Click the link below to have your password reset:" + url
					+ "<br><br>If you did not request to have your password reset you can safely ignore this email. Rest assured your customer account is safe.";

			MailSettings mailSettings = getMailSettings();
			Mail mail = new Mail(mailSettings);
			try {
				mail.createMail(null, Arrays.asList(email), subject, messageBody);
				mail.sendMail();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	public MailSettings getMailSettings() throws FrameworkException {
		MailSettings mailSettings = new MailSettings();
		mailSettings.setMailServerSettings("smtp.gmail.com", "587", "koopapp.goodbuy@gmail.com", "k00papp.g00dbuy");
		mailSettings.setDefaultNoReply("koopapp.goodbuy@gmail.com");

		return mailSettings;
	}

	@Override
	public void createRequestResetMailMessage(String username) throws FrameworkException {
		ApplicationUser applicationUser = applicationUserRepository.getByUserName(username);

		if (applicationUser != null && applicationUser.getPerson() != null) {
			// String resetPasswordKey = generateResetPasswordKey(username);
			String resetPasswordKey = "";
			String url = "";
			// contextPath + "/" + username + "/" + resetPasswordKey;

			String email = personContactService.getEmail(applicationUser.getPerson().getId());

			String subject = "Koopapp password reset requested";
			String messageBody = "We received a request to reset the password associated with this e-mail address. "
					+ "If you made this request, please follow the instructions below."
					+ "<br><br>Click the link below to have your password reset:" + url
					+ "<br><br>If you did not request to have your password reset you can safely ignore this email. Rest assured your customer account is safe.";

			MailSettings mailSettings = getMailSettings();
			Mail mail = new Mail(mailSettings);
			try {
				mail.createMail(null, Arrays.asList(email), subject, messageBody);
				mail.sendMail();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createResetFailedMailMessage(String username, String contextPath) throws FrameworkException {
		ApplicationUser applicationUser = applicationUserRepository.getByUserName(username);

		if (applicationUser != null && applicationUser.getPerson() != null) {

			// String resetPasswordKey = generateResetPasswordKey(username);
			String resetPasswordKey = "";
			String url = contextPath + "?username=" + username + "&resetPasswordKey=" + resetPasswordKey;

			String email = personContactService.getEmail(applicationUser.getPerson().getId());

			String subject = "Koopapp password reset requested";
			String messageBody = "We received a request to reset the password associated with this e-mail address. "
					+ "If you made this request, please follow the instructions below."
					+ "<br><br>Click the link below to reset your password using our secure server:" + url
					+ "<br><br>If you did not request to have your password reset you can safely ignore this email. Rest assured your customer account is safe.";

			MailSettings mailSettings = getMailSettings();
			Mail mail = new Mail(mailSettings);
			try {
				mail.createMail(null, Arrays.asList(email), subject, messageBody);
				mail.sendMail();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createResetPasswordSuccessMailMessage(String username) {
		// TODO Auto-generated method stub

	}

	public JavaMailSender getJavaMailSender() throws FrameworkException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		List<Configuration> mailServerConfigurations = configurationService.getMailServerConfiguration();

		MailAuthentication mailAuthentication = new MailAuthentication();

		mailServerConfigurations.forEach(configuration -> {
			switch (configuration.getCode()) {
			case ConfigurationProperty.SMTP_HOST:
				mailSender.setHost(configuration.getValue());
				break;
			case ConfigurationProperty.SMTP_PORT:
				mailSender.setPort(NumberUtil.getInteger(configuration.getValue()));
				break;
			case ConfigurationProperty.SMTP_USERNAME:
				mailSender.setUsername(configuration.getValue());
				if (StringUtils.isNotBlank(configuration.getValue())) {
					mailAuthentication.useAuth = true;
				}
				break;
			case ConfigurationProperty.SMTP_PASSWORD:
				mailSender.setPassword(configuration.getValue());
				break;
			default:
				break;
			}
		});

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", String.valueOf(mailAuthentication.useAuth));
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		return mailSender;
	}

	class MailAuthentication {
		boolean useAuth = false;
	}

	@Override
	public boolean sendTestMail(String host, String port, String username, String password, String systemEmail,
			String subject, String message, String validateEmail) throws FrameworkException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(NumberUtil.getInteger(port));
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		boolean useAuth = false;
		if (StringUtils.isNotBlank(username)) {
			useAuth = true;
		}
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", String.valueOf(useAuth));
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(validateEmail);
		simpleMailMessage.setFrom(systemEmail);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);

		try {
			mailSender.send(simpleMailMessage);
			return true;
		} catch (MailException e) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.UNABLE_TO_SEND_MAIL, e);
		}
	}

	@Override
	public void createImmediateMailMessage(Long mailTemplateId, String email, Long userId,
			ConcurrentMap<String, Object> placeholders) throws FrameworkException {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		executorService.execute(new Runnable() {
			public void run() {
				GeneratedMail generatedMail = null;
				try {
					MailTemplate mailTemplate = mailTemplateService.getById(MailTemplate.class, mailTemplateId);
					freemarker.template.Configuration cfg = new freemarker.template.Configuration(
							freemarker.template.Configuration.VERSION_2_3_28);
					Template subjectTemplate = new Template("subject", new StringReader(mailTemplate.getSubject()),
							cfg);
					StringWriter stringWriter = new StringWriter();
					subjectTemplate.process(placeholders, stringWriter);
					String subject = stringWriter.toString();

					Template messageTemplate = new Template("message", new StringReader(mailTemplate.getMessage()),
							cfg);
					stringWriter = new StringWriter();
					messageTemplate.process(placeholders, stringWriter);
					String message = stringWriter.toString();

					String systemEmail = configurationService.getSystemEmail();
					GeneratedMailVO generatedMailVO = new GeneratedMailVO();
					generatedMailVO.setUpdatedBy(userId);
					generatedMailVO.setSubject(subject);
					generatedMailVO.setMessage(message);
					generatedMailVO.setFrom(systemEmail);
					generatedMailVO.setTo(email);
					generatedMailVO.setNew(true);
					generatedMailVO.setState(MailState.IMMEDIATE);

					generatedMail = generatedMailService.updateSingle(generatedMailVO);

					JavaMailSender javaMailSender = getJavaMailSender();
					MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
					helper.setTo(email);
					helper.setFrom(systemEmail);
					helper.setSubject(subject);
					mimeMessage.setContent(message, "text/html");

					javaMailSender.send(mimeMessage);
				} catch (MailSendException | IOException | TemplateException | FrameworkException | MessagingException
						| IllegalArgumentException | NullPointerException e) {
					if (generatedMail != null) {
						String stackTrace = ExceptionUtils.getStackTrace(e);
						try {
							generatedMailService.setError(generatedMail.getId(), stackTrace);
						} catch (FrameworkException e1) {
							logger.error(e.getMessage(), e);
						}
					} else {
						logger.error(e.getMessage(), e);
					}
				}
			}
		});

		executorService.shutdown();

	}

}
