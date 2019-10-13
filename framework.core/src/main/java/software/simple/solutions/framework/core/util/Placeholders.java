package software.simple.solutions.framework.core.util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.simple.solutions.framework.core.config.MailTemplateGroup;
import software.simple.solutions.framework.core.config.mail.MailTemplatePlaceholderGroupRecipient;
import software.simple.solutions.framework.core.config.mail.MailTemplatePlaceholderGroupSystem;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IPersonInformationService;

public class Placeholders {

	protected static final Logger logger = LogManager.getLogger(Placeholders.class);

	private ConcurrentMap<String, Object> map;

	protected Placeholders() {
		map = new ConcurrentHashMap<String, Object>();
		createDefaultMapValues();
	}

	public static Placeholders build() {
		return new Placeholders();
	}

	public ConcurrentMap<String, Object> getMap() {
		return map;
	}

	public Placeholders add(String key, Object value) {
		String replacedKey = key.replace(MailTemplateGroup.PREFIX, "").replace(".", "_");
		map.put(replacedKey, value);
		return this;
	}

	public Placeholders addPassword(String password) {
		add(MailTemplatePlaceholderGroupRecipient.RECIPIENT_PASSWORD, password);
		return this;
	}

	public Placeholders addApplicationUser(ApplicationUser applicationUser) {
		add(MailTemplatePlaceholderGroupRecipient.RECIPIENT_USERNAME, applicationUser.getUsername());
		return this;
	}

	public Placeholders addRecipient(Person person) {
		try {
			add(MailTemplatePlaceholderGroupRecipient.RECIPIENT_FIRST_NAME, person.getFirstName());
			add(MailTemplatePlaceholderGroupRecipient.RECIPIENT_LAST_NAME, person.getLastName());
			IPersonInformationService personInformationService = ContextProvider
					.getBean(IPersonInformationService.class);
			String email = personInformationService.getEmail(person.getId());
			add(MailTemplatePlaceholderGroupRecipient.RECIPIENT_EMAIL, email);
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
		}
		return this;
	}

	public Placeholders addResetPasswordToken(String key) {
		add(MailTemplatePlaceholderGroupSystem.REQUEST_PASSWORD_RESET_VALIDITY, key);

		String requestPasswordResetLink = (String) map.get(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK
				.replace(MailTemplateGroup.PREFIX, "").replace(".", "_"));
		if (requestPasswordResetLink != null) {
			requestPasswordResetLink += "?key=" + key;
			add(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK, requestPasswordResetLink);
		}

		return this;
	}

	private void createDefaultMapValues() {
		try {
			IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
			List<Configuration> applicationConfiguration = configurationService.getMailPlaceholderConfiguration();

			applicationConfiguration.forEach(configuration -> {
				switch (configuration.getCode()) {
				case ConfigurationProperty.APPLICATION_NAME:
					add(MailTemplatePlaceholderGroupSystem.APPLICATION_NAME, configuration.getValue());
					break;
				case ConfigurationProperty.APPLICATION_URL:
					add(MailTemplatePlaceholderGroupSystem.APPLICATION_BASE_URL, configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_LINK:
					add(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK, configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY:
					add(MailTemplatePlaceholderGroupSystem.REQUEST_PASSWORD_RESET_VALIDITY, configuration.getValue());
					break;
				default:
					break;
				}
			});

			String baseUrl = (String) map.get(MailTemplatePlaceholderGroupSystem.APPLICATION_BASE_URL
					.replace(MailTemplateGroup.PREFIX, "").replace(".", "_"));
			if (baseUrl != null) {
				if (!baseUrl.endsWith("/")) {
					baseUrl += "/";
				}

				String requestPasswordResetLink = (String) map
						.get(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK
								.replace(MailTemplateGroup.PREFIX, "").replace(".", "_"));
				if (requestPasswordResetLink != null) {
					if (requestPasswordResetLink.startsWith("/")) {
						requestPasswordResetLink = requestPasswordResetLink.substring(1);
					}
					requestPasswordResetLink = baseUrl + requestPasswordResetLink;

					add(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK, requestPasswordResetLink);
				}
			}

		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
