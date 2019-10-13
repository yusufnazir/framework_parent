package software.simple.solutions.framework.core.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import software.simple.solutions.framework.core.annotations.MailPlaceholders;
import software.simple.solutions.framework.core.config.mail.MailTemplatePlaceholder;
import software.simple.solutions.framework.core.constants.MailTemplatePlaceholderItem;

@Component
public class MailTemplateGroup {

	private static final Logger logger = LogManager.getLogger(MailTemplateGroup.class);

	public static final String PREFIX = "mail.template.key.";

	public static final List<MailTemplatePlaceholderItem> items;

	static {
		items = new ArrayList<MailTemplatePlaceholderItem>();
	}

	@PostConstruct
	public void init() {
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(MailPlaceholders.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("software.simple.solutions")) {
			String beanClassName = bd.getBeanClassName();
			try {
				Class<?> class1 = Class.forName(beanClassName);
				MailTemplatePlaceholder newInstance = (MailTemplatePlaceholder) class1.newInstance();
				List<MailTemplatePlaceholderItem> list = newInstance.getItems();
				if (list != null && !list.isEmpty()) {
					items.addAll(list);
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}

		}
	}

	public static Map<String, List<MailTemplatePlaceholderItem>> getItems() {
		return items.stream().collect(Collectors.groupingBy(MailTemplatePlaceholderItem::getGroup));
	}

}
