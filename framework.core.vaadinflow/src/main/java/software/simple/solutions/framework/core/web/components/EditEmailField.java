package software.simple.solutions.framework.core.web.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.annotations.CxodeFunctionaInterface;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;

public class EditEmailField<E> extends CustomField<String> {

	private static final long serialVersionUID = -6953072556831688132L;

	private static final Logger logger = LogManager.getLogger(EditEmailField.class);

	private CButton editBtn;
	private CButton commitChangeBtn;
	private SessionHolder sessionHolder;
	private CEmailField textField;
	private String previousValue;

	private CButton cancelChangeBtn;

	public EditEmailField() {
		super();
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		getElement().getStyle().set("--lumo-text-field-size", "0");
		getElement().getStyle().set("display", "inline");
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(false);
		add(horizontalLayout);

		textField = new CEmailField();
		textField.setReadOnly(true);
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		textField.getStyle().set("--lumo-text-field-size", "1.4rem");
		horizontalLayout.add(textField);
		textField.setWidth("100%");

		editBtn = new CButton();
		editBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		editBtn.setIcon(new Icon(VaadinIcon.EDIT));
		editBtn.getStyle().set("border", "1px solid");
		horizontalLayout.add(editBtn);
		editBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 7585289128374363839L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				textField.setReadOnly(false);
				editBtn.setVisible(false);
				commitChangeBtn.setVisible(true);
				cancelChangeBtn.setVisible(true);
				previousValue = textField.getValue();
			}
		});

		commitChangeBtn = new CButton();
		commitChangeBtn.setVisible(false);
		commitChangeBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_SUCCESS);
		commitChangeBtn.setIcon(new Icon(VaadinIcon.CHECK));
		commitChangeBtn.getStyle().set("border", "1px solid");
		horizontalLayout.add(commitChangeBtn);
		commitChangeBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 7585289128374363839L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				textField.setReadOnly(true);
				editBtn.setVisible(true);
				commitChangeBtn.setVisible(false);
				ConfirmationDialog confirmationDialog = new ConfirmationDialog();
				confirmationDialog.buildConfirmation(new ComponentEventListener<ClickEvent<Button>>() {

					private static final long serialVersionUID = -708162705554006817L;

					@Override
					public void onComponentEvent(ClickEvent<Button> event) {
						textField.setReadOnly(true);
						editBtn.setVisible(true);
						commitChangeBtn.setVisible(false);
						cancelChangeBtn.setVisible(false);
						updateValue();
					}
				});
			}
		});

		cancelChangeBtn = new CButton();
		cancelChangeBtn.setVisible(false);
		cancelChangeBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		cancelChangeBtn.setIcon(new Icon(VaadinIcon.CLOSE));
		cancelChangeBtn.getStyle().set("border", "1px solid");
		horizontalLayout.add(cancelChangeBtn);
		cancelChangeBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 7585289128374363839L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				textField.setReadOnly(true);
				editBtn.setVisible(true);
				commitChangeBtn.setVisible(false);
				cancelChangeBtn.setVisible(false);
				textField.setValue(previousValue);
			}
		});
	}

	public <T> T updateValue(CxodeFunctionaInterface function) {
		return function.execute();
	}

	public void setCaptionByKey(String key) {
		setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public void clear() {
		setValue(null);
	}

	@Override
	public void setEnabled(boolean enabled) {
		editBtn.setEnabled(enabled);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		textField.setReadOnly(readOnly);
	}

	@Override
	protected String generateModelValue() {
		return textField.getValue();
	}

	@Override
	protected void setPresentationValue(String value) {
		textField.setValue(null);
		editBtn.setVisible(true);
		commitChangeBtn.setVisible(false);
	}

}
