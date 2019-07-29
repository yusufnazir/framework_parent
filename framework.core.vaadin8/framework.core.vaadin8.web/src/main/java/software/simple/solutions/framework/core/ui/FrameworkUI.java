package software.simple.solutions.framework.core.ui;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.Page;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.CxodeTheme;
import software.simple.solutions.framework.core.constants.LayoutType;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.UserLoginRequestedEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEventBus;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.web.AppLayoutView;
import software.simple.solutions.framework.core.web.TopMenuLayoutView;
import software.simple.solutions.framework.core.web.view.ChangePasswordView;
import software.simple.solutions.framework.core.web.view.ErrorView;
import software.simple.solutions.framework.core.web.view.LoginView;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme(value = CxodeTheme.CXODE)
public class FrameworkUI extends UI implements RequestHandler {

	private static final long serialVersionUID = 845098824732445156L;

	private static final Logger logger = LogManager.getLogger(FrameworkUI.class);

	private final SimpleSolutionsEventBus dashboardEventbus = new SimpleSolutionsEventBus();

	private SessionHolder sessionHolder;

	// @Autowired
	// private SpringViewProvider viewProvider;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		getSession().addRequestHandler(this);
		sessionHolder = new SessionHolder();
		sessionHolder.setLogoutPageLocation(vaadinRequest.getContextPath() + "/app");
		sessionHolder.setLocale(UI.getCurrent().getLocale());
		UI.getCurrent().setData(sessionHolder);
		UI.getCurrent().setErrorHandler(new ErrorHandler() {

			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				logger.error(event.getThrowable());
				new MessageWindowHandler(event.getThrowable());
			}
		});

		Responsive.makeResponsive(this);

		SimpleSolutionsEventBus.register(this);
		// Responsive.makeResponsive(this);
		// addStyleName(ValoTheme.UI_WITH_MENU);

		try {
			IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
			Configuration configuration = configurationService.getByCode(ConfigurationProperty.APPLICATION_NAME);
			if (configuration != null) {
				Page.getCurrent().setTitle(configuration.getValue());
			}

			addStyleName("backgroundimage");
			updateContent();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static SimpleSolutionsEventBus getDashboardEventbus() {
		return ((FrameworkUI) getCurrent()).dashboardEventbus;
	}

	protected void updateContent() throws FrameworkException {
		ApplicationUser applicationUser = sessionHolder.getApplicationUser();
		if (applicationUser != null) {

			Boolean resetPassword = applicationUser.getResetPassword();
			if (applicationUser.getUseLdap() != null && !applicationUser.getUseLdap() && resetPassword) {
				setContent(new ChangePasswordView());
				addStyleName("loginview");
			} else {
				// Authenticated user
				Long layoutType = LayoutType.TOP_MENU;
				IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
				Configuration configuration = configurationService.getByCode(ConfigurationProperty.APPLICATION_LAYOUT);
				if (configuration != null) {
					layoutType = configuration.getLong();
				}
				if (layoutType == null || layoutType.compareTo(0l) == 0) {
					layoutType = LayoutType.TOP_MENU;
				}

				if (layoutType.compareTo(LayoutType.TOP_MENU) == 0) {
					setContent(new TopMenuLayoutView());
				} else if (layoutType.compareTo(LayoutType.APP_LAYOUT) == 0) {
					AppLayoutView valoMenuLayout = new AppLayoutView();
					valoMenuLayout.setSizeFull();
					setContent(valoMenuLayout);
					removeStyleName("loginview");
				}
			}
		} else {
			getNavigator().setErrorView(ErrorView.class);
			LoginView loginView = new LoginView();
			this.setContent(loginView);
			// addStyleName("loginview");
		}
	}

	@Subscribe
	public void userLoginRequested(final UserLoginRequestedEvent event) throws FrameworkException {
		updateContent();
	}

	@Override
	public boolean handleRequest(VaadinSession session, VaadinRequest request, VaadinResponse response)
			throws IOException {
		String pathInfo = request.getPathInfo();
		System.out.println("pathInfo:" + pathInfo);
		Map<String, String[]> parameterMap = request.getParameterMap();
		System.out.println("parameterMap:" + parameterMap);
		return false;
	}

	@Override
	public void detach() {
		super.detach();

		// Clean up
		getSession().removeRequestHandler(this);
	}

}
