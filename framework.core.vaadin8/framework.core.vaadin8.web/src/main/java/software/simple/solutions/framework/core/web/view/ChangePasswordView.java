package software.simple.solutions.framework.core.web.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CLabel;
import software.simple.solutions.framework.core.components.CPasswordField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.UserLoginRequestedEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEventBus;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.ApplicationUserServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ChangePasswordView extends VerticalLayout {

	private static final long serialVersionUID = 1969692676006992700L;
	private static final Logger logger = LogManager.getLogger(ChangePasswordView.class);

	private SessionHolder sessionHolder;
	private boolean mandatory = true;

	public ChangePasswordView() throws FrameworkException {
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		setSizeFull();
		setMargin(true);
		setSpacing(false);

		Component loginForm = buildLoginForm();
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}

	private Component buildLoginForm() throws FrameworkException {
		final VerticalLayout changePasswordPanel = new VerticalLayout();
		changePasswordPanel.setSizeUndefined();
		changePasswordPanel.setMargin(true);
		Responsive.makeResponsive(changePasswordPanel);
		changePasswordPanel.addStyleName(Style.LOGIN_PANEL);

		changePasswordPanel.addComponent(buildLabels());
		changePasswordPanel.addComponent(buildFields());

		return changePasswordPanel;
	}

	private Component buildFields() {
		VerticalLayout fields = new VerticalLayout();
		fields.addStyleName(Style.FIELDS);
		fields.addStyleName(ValoTheme.LAYOUT_CARD);

		final CPasswordField oldPasswordFld = new CPasswordField();
		oldPasswordFld.setCaptionByKey(ApplicationUserProperty.CURRENT_PASSWORD);
		oldPasswordFld.setIcon(FontAwesome.USER);
		oldPasswordFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final CPasswordField newPasswordFld = new CPasswordField();
		newPasswordFld.setCaptionByKey(ApplicationUserProperty.PASSWORD);
		newPasswordFld.setIcon(FontAwesome.LOCK);
		newPasswordFld.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final CPasswordField confirmNewPassword = new CPasswordField();
		confirmNewPassword.setCaptionByKey(ApplicationUserProperty.PASSWORD_CONFIRM);
		confirmNewPassword.setIcon(FontAwesome.LOCK);
		confirmNewPassword.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		final CButton submitChangeBtn = new CButton();
		submitChangeBtn.setCaptionByKey(SystemProperty.LOGIN_BUTTON_LOGIN);
		submitChangeBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submitChangeBtn.setClickShortcut(KeyCode.ENTER);
		submitChangeBtn.focus();

		fields.addComponents(oldPasswordFld, newPasswordFld, confirmNewPassword, submitChangeBtn);
		fields.setComponentAlignment(submitChangeBtn, Alignment.BOTTOM_LEFT);

		submitChangeBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = -232691329181636057L;

			@Override
			public void buttonClick(final ClickEvent event) {
				try {
					ApplicationUser applicationUser = sessionHolder.getApplicationUser();
					SecurityValidation securityValidation = ApplicationUserServiceFacade.get(UI.getCurrent())
							.validateChangePassword(applicationUser.getId(), oldPasswordFld.getValue(),
									newPasswordFld.getValue(), confirmNewPassword.getValue());
					if (securityValidation.isSuccess()) {
						ApplicationUserServiceFacade.get(UI.getCurrent()).changePassword(applicationUser.getId(),
								newPasswordFld.getValue());
						if (mandatory) {
							SimpleSolutionsEventBus.post(new UserLoginRequestedEvent(applicationUser.getUsername(),
									newPasswordFld.getValue()));
						} else {
							NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
						}
						oldPasswordFld.setValue(null);
						newPasswordFld.setValue(null);
						confirmNewPassword.setValue(null);
					} else {
						NotificationWindow.notificationErrorWindow(
								PropertyResolver.getPropertyValueByLocale(securityValidation.getMessageKey()),
								UI.getCurrent().getLocale());
					}
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
					logger.error(e.getMessage(), e);
				}
			}
		});
		return fields;
	}

	private Component buildLabels() throws FrameworkException {
		HorizontalLayout labels = new HorizontalLayout();
		labels.setWidth("100%");
		// labels.addStyleName(Style.LABELS);

		CLabel changePasswordLbl = new CLabel();
		changePasswordLbl.setValueByKey(ApplicationUserProperty.CHANGE_PASSWORD);
		changePasswordLbl.setSizeUndefined();
		changePasswordLbl.addStyleName(ValoTheme.LABEL_H4);
		changePasswordLbl.addStyleName(ValoTheme.LABEL_COLORED);
		labels.addComponent(changePasswordLbl);
		labels.setExpandRatio(changePasswordLbl, 1f);

		return labels;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

}
