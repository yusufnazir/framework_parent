package software.simple.solutions.framework.core.config.mail;

import java.util.ArrayList;
import java.util.List;

import software.simple.solutions.framework.core.annotations.MailPlaceholders;
import software.simple.solutions.framework.core.config.MailTemplateGroup;
import software.simple.solutions.framework.core.constants.MailTemplatePlaceholderItem;

@MailPlaceholders
public class MailTemplatePlaceholderGroupSystem implements MailTemplatePlaceholder {

	public static final String MAIL_TEMPLATE_GROUP_SYSTEM = "mail.template.group.system";

	public static final String APPLICATION_NAME = MailTemplateGroup.PREFIX + "application.name";
	public static final String APPLICATION_BASE_URL = MailTemplateGroup.PREFIX + "application.base.url";
	public static final String RESET_PASSWORD_LINK = MailTemplateGroup.PREFIX
			+ "application.security.reset.password.link";
	public static final String REQUEST_PASSWORD_RESET_VALIDITY = MailTemplateGroup.PREFIX
			+ "application.security.request.password.reset.validity";

	@Override
	public List<MailTemplatePlaceholderItem> getItems() {
		List<MailTemplatePlaceholderItem> items = new ArrayList<MailTemplatePlaceholderItem>();
		items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_SYSTEM, APPLICATION_NAME));
		items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_SYSTEM, RESET_PASSWORD_LINK));
		items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_SYSTEM, REQUEST_PASSWORD_RESET_VALIDITY));
		return items;
	}

}
