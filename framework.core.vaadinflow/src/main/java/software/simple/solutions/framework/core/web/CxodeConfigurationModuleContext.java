package software.simple.solutions.framework.core.web;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import software.simple.solutions.framework.core.annotations.CxodeConfigurationComponent;

@Component
public class CxodeConfigurationModuleContext {
	private static final Logger logger = LogManager.getLogger(CxodeConfigurationModuleContext.class);

	private static List<CxodeConfigurationItem> items;

	@PostConstruct
	public void init() {
		items = new ArrayList<CxodeConfigurationModuleContext.CxodeConfigurationItem>();

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(CxodeConfigurationComponent.class));
		Set<BeanDefinition> components = scanner.findCandidateComponents("software.simple.solutions");
		for (BeanDefinition component : components) {
			CxodeConfigurationItem cxodeConfigurationItem = new CxodeConfigurationItem();

			String className = component.getBeanClassName();
			Class<? extends com.vaadin.flow.component.Component> class1 = (Class<? extends com.vaadin.flow.component.Component>) ClassUtils
					.resolveClassName(className, null);
			cxodeConfigurationItem.setConfigurationClass(class1);
			CxodeConfigurationComponent cxodeConfigurationComponent = class1
					.getAnnotation(CxodeConfigurationComponent.class);
			cxodeConfigurationItem.setCaptionKey(cxodeConfigurationComponent.captionKey());
			cxodeConfigurationItem.setOrder(cxodeConfigurationComponent.order());
			items.add(cxodeConfigurationItem);

			logger.debug("Added entity: " + component);
		}

		items.sort(new CxodeConfigurationItem());
	}

	public static List<CxodeConfigurationItem> getItems() {
		return items;
	}

	public static void setItems(List<CxodeConfigurationItem> items) {
		CxodeConfigurationModuleContext.items = items;
	}

	public class CxodeConfigurationItem implements Comparator<CxodeConfigurationItem> {
		private Class<? extends com.vaadin.flow.component.Component> configurationClass;
		private int order;
		private String captionKey;

		public Class<? extends com.vaadin.flow.component.Component> getConfigurationClass() {
			return configurationClass;
		}

		public void setConfigurationClass(Class<? extends com.vaadin.flow.component.Component> configurationClass) {
			this.configurationClass = configurationClass;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		public String getCaptionKey() {
			return captionKey;
		}

		public void setCaptionKey(String captionKey) {
			this.captionKey = captionKey;
		}

		@Override
		public int compare(CxodeConfigurationItem o1, CxodeConfigurationItem o2) {
			return o1.order - o2.order;
		}

	}

}
