package software.simple.solutions.framework.core.config.mail;

import java.util.List;

import software.simple.solutions.framework.core.constants.MailTemplatePlaceholderItem;

public interface MailTemplatePlaceholder {

	List<MailTemplatePlaceholderItem> getItems();

}
