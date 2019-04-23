package software.simple.solutions.framework.core.util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import software.simple.solutions.framework.core.constants.MailTemplateGroup;
import software.simple.solutions.framework.core.constants.MailTemplateGroup.MailTemplatePlaceholderGroupRecipient;
import software.simple.solutions.framework.core.constants.MailTemplateGroup.MailTemplatePlaceholderGroupSystem;
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

	private Placeholders() {
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
		map.put(key, value);
		return this;
	}

	public Placeholders addPassword(String password) {
		map.put(MailTemplatePlaceholderGroupRecipient.RECIPIENT_PASSWORD.replace(MailTemplateGroup.PREFIX, "")
				.replace(".", "_"), password);
		return this;
	}

	public Placeholders addApplicationUser(ApplicationUser applicationUser) {
		map.put(MailTemplatePlaceholderGroupRecipient.RECIPIENT_USERNAME.replace(MailTemplateGroup.PREFIX, "")
				.replace(".", "_"), applicationUser.getUsername());
		return this;
	}

	public Placeholders addRecipient(Person person) {
		try {
			map.put(MailTemplatePlaceholderGroupRecipient.RECIPIENT_FIRST_NAME.replace(MailTemplateGroup.PREFIX, "")
					.replace(".", "_"), person.getFirstName());
			map.put(MailTemplatePlaceholderGroupRecipient.RECIPIENT_LAST_NAME.replace(MailTemplateGroup.PREFIX, "")
					.replace(".", "_"), person.getLastName());
			IPersonInformationService personInformationService = ContextProvider
					.getBean(IPersonInformationService.class);
			String email = personInformationService.getEmail(person.getId());
			map.put(MailTemplatePlaceholderGroupRecipient.RECIPIENT_EMAIL.replace(MailTemplateGroup.PREFIX, "")
					.replace(".", "_"), email);
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
		}
		return this;
	}

	public Placeholders addResetPasswordToken(String key) {
		map.put(MailTemplatePlaceholderGroupSystem.REQUEST_PASSWORD_RESET_VALIDITY.replace(MailTemplateGroup.PREFIX, "")
				.replace(".", "_"), key);

		String requestPasswordResetLink = (String) map.get(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK
				.replace(MailTemplateGroup.PREFIX, "").replace(".", "_"));
		if (requestPasswordResetLink != null) {
			requestPasswordResetLink += "?key=" + key;
			map.put(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK.replace(MailTemplateGroup.PREFIX, "")
					.replace(".", "_"), requestPasswordResetLink);
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
					map.put(MailTemplatePlaceholderGroupSystem.APPLICATION_NAME.replace(MailTemplateGroup.PREFIX, "")
							.replace(".", "_"), configuration.getValue());
					break;
				case ConfigurationProperty.APPLICATION_URL:
					map.put(MailTemplatePlaceholderGroupSystem.APPLICATION_BASE_URL
							.replace(MailTemplateGroup.PREFIX, "").replace(".", "_"), configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_LINK:
					map.put(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK.replace(MailTemplateGroup.PREFIX, "")
							.replace(".", "_"), configuration.getValue());
					break;
				case ConfigurationProperty.PASSWORD_SECURITY_REQUEST_PASSWORD_RESET_VALIDITY:
					map.put(MailTemplatePlaceholderGroupSystem.REQUEST_PASSWORD_RESET_VALIDITY
							.replace(MailTemplateGroup.PREFIX, "").replace(".", "_"), configuration.getValue());
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

					map.put(MailTemplatePlaceholderGroupSystem.RESET_PASSWORD_LINK.replace(MailTemplateGroup.PREFIX, "")
							.replace(".", "_"), requestPasswordResetLink);
				}
			}

		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
		}
	}

}
