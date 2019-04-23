package software.simple.solutions.framework.core.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MailTemplateGroup {

	public static final String PREFIX = "mail.template.key.";

	private List<MailTemplatePlaceholderItem> items;

	public MailTemplateGroup() {
		super();
		items = new ArrayList<MailTemplatePlaceholderItem>();
		createList();
	}

	private void createList() {
		items.addAll(new MailTemplatePlaceholderGroupSystem().getItems());
		items.addAll(new MailTemplatePlaceholderGroupRecipient().getItems());
	}

	public Map<String, List<MailTemplatePlaceholderItem>> getItems() {
		return items.stream().collect(Collectors.groupingBy(MailTemplatePlaceholderItem::getGroup));
	}

	public class MailTemplatePlaceholderGroupSystem {
		public static final String MAIL_TEMPLATE_GROUP_SYSTEM = "mail.template.group.system";

		public static final String APPLICATION_NAME = PREFIX + "application.name";
		public static final String APPLICATION_BASE_URL = PREFIX + "application.base.url";
		public static final String RESET_PASSWORD_LINK = PREFIX + "application.security.reset.password.link";
		public static final String REQUEST_PASSWORD_RESET_VALIDITY = PREFIX
				+ "application.security.request.password.reset.validity";

		public List<MailTemplatePlaceholderItem> getItems() {
			List<MailTemplatePlaceholderItem> items = new ArrayList<MailTemplatePlaceholderItem>();
			items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_SYSTEM, APPLICATION_NAME));
			items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_SYSTEM, RESET_PASSWORD_LINK));
			items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_SYSTEM, REQUEST_PASSWORD_RESET_VALIDITY));
			return items;
		}
	}

	public class MailTemplatePlaceholderGroupRecipient {
		public static final String MAIL_TEMPLATE_GROUP_RECIPIENT = "mail.template.group.recipient";

		public static final String RECIPIENT_FIRST_NAME = PREFIX + "recipient.first.name";
		public static final String RECIPIENT_LAST_NAME = PREFIX + "recipient.last.name";
		public static final String RECIPIENT_USERNAME = PREFIX + "recipient.username";
		public static final String RECIPIENT_PASSWORD = PREFIX + "recipient.password";
		public static final String RECIPIENT_EMAIL = PREFIX + "recipient.email";

		public List<MailTemplatePlaceholderItem> getItems() {
			List<MailTemplatePlaceholderItem> items = new ArrayList<MailTemplatePlaceholderItem>();
			items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_RECIPIENT, RECIPIENT_FIRST_NAME));
			items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_RECIPIENT, RECIPIENT_LAST_NAME));
			items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_RECIPIENT, RECIPIENT_USERNAME));
			items.add(new MailTemplatePlaceholderItem(MAIL_TEMPLATE_GROUP_RECIPIENT, RECIPIENT_PASSWORD));
			return items;
		}
	}

}
