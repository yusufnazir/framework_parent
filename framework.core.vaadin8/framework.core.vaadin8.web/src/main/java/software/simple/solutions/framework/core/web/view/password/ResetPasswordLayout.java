package software.simple.solutions.framework.core.web.view.password;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CPasswordField;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SecurityValidation;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IApplicationUserService;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ResetPasswordLayout {

	private CPasswordField passwordFld;
	private CPasswordField confirmPasswordFld;
	private Long userId;

	public ResetPasswordLayout(Long userId) {
		this.userId = userId;
		Object data = UI.getCurrent().getData();
		Window window = new Window();
		window.setCaption(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_RESET_PASSWORD));
		UI.getCurrent().addWindow(window);
		window.center();
		window.setModal(true);

		CGridLayout resetPasswordLayout = ComponentUtil.createGrid();
		resetPasswordLayout.setMargin(true);
		resetPasswordLayout.setSpacing(true);
		window.setContent(resetPasswordLayout);

		passwordFld = resetPasswordLayout.addField(CPasswordField.class, ApplicationUserProperty.PASSWORD, 0, 0);
		passwordFld.setWidth("250px");

		confirmPasswordFld = resetPasswordLayout.addField(CPasswordField.class,
				ApplicationUserProperty.PASSWORD_CONFIRM, 0, 1);
		confirmPasswordFld.setWidth("250px");

		CButton savePasswordBtn = resetPasswordLayout.addField(CButton.class, 0, 2);
		savePasswordBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		savePasswordBtn.setCaptionByKey(SystemProperty.SYSTEM_BUTTON_SUBMIT);
		resetPasswordLayout.setComponentAlignment(savePasswordBtn, Alignment.BOTTOM_LEFT);

		savePasswordBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 296476166426595927L;

			@Override
			public void buttonClick(ClickEvent event) {
				IApplicationUserService applicationUserService = ContextProvider.getBean(IApplicationUserService.class);
				try {
					SecurityValidation securityValidation = applicationUserService.resetUserPassword(userId,
							passwordFld.getValue(), confirmPasswordFld.getValue());
					if (!securityValidation.isSuccess()) {
						new MessageWindowHandler(securityValidation.getMessageKey(), null);
					} else {
						window.close();
					}
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});

		window.setSizeUndefined();
	}

}
