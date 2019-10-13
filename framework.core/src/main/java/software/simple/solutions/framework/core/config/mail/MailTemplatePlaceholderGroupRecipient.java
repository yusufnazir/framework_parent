package software.simple.solutions.framework.core.config.mail;

import java.util.ArrayList;
import java.util.List;

import software.simple.solutions.framework.core.annotations.MailPlaceholders;
import software.simple.solutions.framework.core.config.MailTemplateGroup;
import software.simple.solutions.framework.core.constants.MailTemplatePlaceholderItem;

@MailPlaceholders
public class MailTemplatePlaceholderGroupRecipient implements MailTemplatePlaceholder {
	public static final String MAIL_TEMPLATE_GROUP_RECIPIENT = "mail.template.group.recipient";

	public static final String RECIPIENT_FIRST_NAME = MailTemplateGroup.PREFIX + "recipient.first.name";
	public static final String RECIPIENT_LAST_NAME = MailTemplateGroup.PREFIX + "recipient.last.name";
	public static final String RECIPIENT_USERNAME = MailTemplateGroup.PREFIX + "recipient.username";
	public static final String RECIPIENT_PASSWORD = MailTemplateGroup.PREFIX + "recipient.password";
	public static final String RECIPIENT_EMAIL = MailTemplateGroup.PREFIX + "recipient.email";

	public List<MailTemplatePlaceholderItem> getItems() {
		List<MailTemplatePlaceholderItem> items = new ArrayList<MailTemplatePlaceholderItem>();
		items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_RECIPIENT, RECIPIENT_FIRST_NAME));
		items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_RECIPIENT, RECIPIENT_LAST_NAME));
		items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_RECIPIENT, RECIPIENT_USERNAME));
		items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_RECIPIENT, RECIPIENT_PASSWORD));
		return items;
	}

}
