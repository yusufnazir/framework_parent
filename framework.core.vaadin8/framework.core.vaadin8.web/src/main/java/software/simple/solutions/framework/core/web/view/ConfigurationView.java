package software.simple.solutions.framework.core.web.view;

import java.util.List;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.CxodeConfigurationModuleContext;
import software.simple.solutions.framework.core.web.CxodeConfigurationModuleContext.CxodeConfigurationItem;

public class ConfigurationView extends AbstractBaseView {

	private static final long serialVersionUID = 3645781346904098642L;
	private TabSheet tabSheet;

	public ConfigurationView() {
		super();
	}

	@Override
	public void executeBuild() throws FrameworkException {
		tabSheet = new TabSheet();
		addComponent(tabSheet);
		tabSheet.setSizeFull();

		List<CxodeConfigurationItem> items = CxodeConfigurationModuleContext.getItems();
		for (CxodeConfigurationItem configurationItem : items) {
			try {
				Component newInstance = configurationItem.getConfigurationClass().newInstance();
				tabSheet.addTab(newInstance, PropertyResolver
						.getPropertyValueByLocale(configurationItem.getCaptionKey(), UI.getCurrent().getLocale()));
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// tabSheet.addTab(new SystemConfiguration(),
		// PropertyResolver.getPropertyValueByLocale(ConfigurationProperty.APPLICATION_CONFIGURATION));
		//
		// tabSheet.addTab(new SmtpServerConfiguration(),
		// PropertyResolver.getPropertyValueByLocale(ConfigurationProperty.SMTP_CONFIGURATION));
		//
		// tabSheet.addTab(new SecurityConfiguration(),
		// PropertyResolver.getPropertyValueByLocale(ConfigurationProperty.PASSWORD_SECURITY_CONFIGURATION));
	}

}
