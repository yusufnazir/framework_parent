package software.simple.solutions.framework.core.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.Command;

import de.codecamp.vaadin.components.messagedialog.MessageDialog;
import de.codecamp.vaadin.components.messagedialog.MessageDialog.FluentButton;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.exceptions.ValidationException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class DetailsWindow extends Dialog {

	private static final long serialVersionUID = -8851440301254569930L;

	private TextArea detailsText;

	public DetailsWindow(Exception e) {
		init(e);
	}

	public static DetailsWindow build(Exception e) {
		return new DetailsWindow(e);
	}

	public void init(Exception e) {
		MessageDialog dialog = new MessageDialog();
		dialog.setWidth("400px");
		Icon icon = VaadinIcon.WARNING.create();
		icon.setColor("#FF0000");
		dialog.setTitle(PropertyResolver.getPropertyValueByLocale(SystemMessageProperty.APPLICATION_ERROR,
				UI.getCurrent().getLocale()), icon);

		if (e instanceof ValidationException) {
			ValidationException validationException = (ValidationException) e;
			dialog.setMessage(validationException.getMessage());
		} else if (e instanceof FrameworkException) {
			FrameworkException frameworkException = (FrameworkException) e;
			Locale locale = frameworkException.getLocale();
			if (locale == null) {
				locale = UI.getCurrent().getLocale();
			}
			String message = PropertyResolver.getPropertyValueByLocale(frameworkException.getMessageKey(), locale,
					frameworkException.getArg());
			dialog.setMessage(message);
		} else {
			dialog.setMessage(e.getMessage());
			FluentButton detailsBtn = dialog.addButtonToLeft();
			detailsBtn.text("Details").title("Tooltip").icon(VaadinIcon.ARROW_DOWN).toggleDetails();
			detailsBtn.onClick(new ComponentEventListener<ClickEvent<Button>>() {

				private static final long serialVersionUID = 8195863784445244911L;

				@Override
				public void onComponentEvent(ClickEvent<Button> event) {
					if (dialog.isDetailsVisible()) {
						dialog.setWidth("800px");
					} else {
						dialog.setWidth("400px");
					}
				}
			});

			detailsText = new TextArea();
			detailsText.setWidthFull();
			detailsText.setMaxHeight("15em");
			detailsText.setReadOnly(true);
			dialog.getDetails().add(detailsText);
			createStackTraceArea(e);
		}

		dialog.addButton().text(
				PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_OK, UI.getCurrent().getLocale()))
				.error().closeOnClick();

		dialog.open();
	}

	protected void createStackTraceArea(Exception cause) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			final PrintWriter pw = new PrintWriter(baos);
			cause.printStackTrace(pw);
			pw.flush();
			UI.getCurrent().access(new Command() {

				private static final long serialVersionUID = 8604085057861768093L;

				@Override
				public void execute() {
					detailsText.setValue(baos.toString());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
