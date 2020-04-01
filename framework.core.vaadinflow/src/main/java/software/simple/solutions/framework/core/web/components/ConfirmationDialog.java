package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import de.codecamp.vaadin.components.messagedialog.MessageDialog;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ConfirmationDialog {

	public ConfirmationDialog() {
		super();
	}

	public void buildConfirmation(ComponentEventListener<ClickEvent<Button>> componentEventListener) {
		MessageDialog confirmDialog = new MessageDialog();
		confirmDialog.setWidth("400px");
		Icon icon = VaadinIcon.WARNING.create();
		icon.getStyle().set("color", "var(--lumo-error-color)");
		confirmDialog.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.CONFIRMATION_NEEDED_HEADER,
				UI.getCurrent().getLocale()), icon);
		confirmDialog.setMessage(PropertyResolver.getPropertyValueByLocale(SystemProperty.CONFIRMATION_NEEDED_MESSAGE,
				UI.getCurrent().getLocale()));
		confirmDialog.addButton().text(
				PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_OK, UI.getCurrent().getLocale()))
				.primary().onClick(componentEventListener).closeOnClick();
		confirmDialog.addButtonToLeft().text(PropertyResolver
				.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_CANCEL, UI.getCurrent().getLocale())).tertiary()
				.closeOnClick();
		confirmDialog.open();
	}

}
