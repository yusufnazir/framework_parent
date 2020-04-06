package software.simple.solutions.framework.core.web.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import software.simple.solutions.framework.core.components.filter.CDateIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDecimalNumberIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDiscreetNumberIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CFormLayout extends FormLayout {

	private static final Logger logger = LogManager.getLogger(CFormLayout.class);

	private static final long serialVersionUID = -3761260727823808576L;

	public CFormLayout() {
		// setWidthFull();
		// setResponsiveSteps(new ResponsiveStep("25em", 1), new
		// ResponsiveStep("32em", 2), new ResponsiveStep("40em", 3));
	}

	public <T extends Component> T add(Class<?> componentClass, String key) {
		Component component = null;
		try {
			component = (Component) componentClass.newInstance();

			if (key != null) {
				if (component instanceof TextField) {
					((TextField) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CTextArea) {
					((CTextArea) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof LookUpField) {
					((LookUpField) component).setCaptionByKey(key);
				} else if (component instanceof CCheckBox) {
					((CCheckBox) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CustomCheckBox) {
					((CustomCheckBox) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CPasswordField) {
					((CPasswordField) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CPopupDateField) {
					((CPopupDateField) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CComboBox) {
					((CComboBox) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CEmailField) {
					((CEmailField) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CDiscreetNumberField) {
					((CDiscreetNumberField) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CTwinColSelect) {
					((CTwinColSelect) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CRichTextEditor) {
					((CRichTextEditor) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof CButton) {
					((CButton) component)
							.setText(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				} else if (component instanceof EditEmailField) {
					((EditEmailField) component)
							.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
				}
			}
			// setUpComponent(component);

			add(component);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}

		return (T) component;
	}

	private void setUpComponent(Component component) {
		if (component instanceof LookUpField) {
			((LookUpField) component).setWidth("300px");
		} else if (component instanceof CTextField) {
			((CTextField) component).setWidth("300px");
		} else if (component instanceof CTextArea) {
			((CTextArea) component).setWidth("300px");
		} else if (component instanceof CPasswordField) {
			// ((CPasswordField) component).setWidth("300px");
		} else if (component instanceof CDiscreetNumberField) {
			((CDiscreetNumberField) component).setWidth("300px");
		} else if (component instanceof CComboBox) {
			((CComboBox) component).setWidth("300px");
		} else if (component instanceof CPopupDateField) {
		} else if (component instanceof CDateIntervalLayout) {
			((CDateIntervalLayout) component).setWidth("300px");
		}
		// else if (component instanceof CDateTimeIntervalLayout{
		// ((CDateIntervalLayout) component).setWidth("300px");
		// }
		else if (component instanceof CDecimalNumberIntervalLayout) {
			((CDecimalNumberIntervalLayout) component).setWidth("300px");
		} else if (component instanceof CStringIntervalLayout) {
			((CStringIntervalLayout) component).setWidth("300px");
		} else if (component instanceof CDiscreetNumberIntervalLayout) {
			((CDiscreetNumberIntervalLayout) component).setWidth("300px");
		} else if (component instanceof ActiveSelect) {
			((ActiveSelect) component).setWidth("150px");
		} else if (component instanceof CCheckBox) {
			((CCheckBox) component).setWidth("-1px");
		} else {
			((HasSize) component).setWidth("-1px");
		}
	}

}
