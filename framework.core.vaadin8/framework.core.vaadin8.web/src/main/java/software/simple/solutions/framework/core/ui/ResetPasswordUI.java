package software.simple.solutions.framework.core.ui;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.constants.Navigation;
import software.simple.solutions.framework.core.web.view.password.ResetPasswordSuccessView;
import software.simple.solutions.framework.core.web.view.password.ResetPasswordView;
import software.simple.solutions.framework.core.web.view.password.SecurityErrorView;

@SpringUI(path = "/security/resetpassword/*")
public class ResetPasswordUI extends UI {

	private static final long serialVersionUID = 845098824732445156L;

	private static final Logger logger = LogManager.getLogger(ResetPasswordUI.class);

	public static final String ERROR = "";

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		Map<String, String[]> parameterMap = vaadinRequest.getParameterMap();
		// Create Navigator, make it control the ViewDisplay
		Navigator navigator = new Navigator(this, this);
		navigator.setErrorView(SecurityErrorView.class);
		// Add some Views
		navigator.addView(Navigation.RESET_PASSWORD, new ResetPasswordView());
		navigator.addView(Navigation.RESET_PASSWORD_SUCCESS, new ResetPasswordSuccessView());
		navigator.navigateTo(Navigation.RESET_PASSWORD);
	}

}
