package software.simple.solutions.framework.core.web.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.ViewServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.web.AbstractBaseView;
import software.simple.solutions.framework.core.web.CxodeConfigurationModuleContext;
import software.simple.solutions.framework.core.web.CxodeConfigurationModuleContext.CxodeConfigurationItem;
import software.simple.solutions.framework.core.web.DetailsWindow;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.CONFIGURATION, layout = MainView.class)
public class ConfigurationView extends AbstractBaseView implements BeforeEnterObserver {

	private static final long serialVersionUID = 3645781346904098642L;

	private static final Logger logger = LogManager.getLogger(ConfigurationView.class);

	private Tabs tabSheet;
	private VerticalLayout tabContent;
	private Location location;

	public ConfigurationView() {
		super();
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		SessionHolder sessionHolder = getSessionHolder();
		if (sessionHolder == null) {
			sessionHolder = new SessionHolder();
			VaadinSession.getCurrent().setAttribute(Constants.SESSION_HOLDER, sessionHolder);
		}
		if (sessionHolder.getApplicationUser() == null) {
			String path = event.getLocation().getPath();
			sessionHolder.setForwardToPath(path);
			QueryParameters queryParameters = event.getLocation().getQueryParameters();
			if (queryParameters != null) {
				Map<String, List<String>> parameters = queryParameters.getParameters();
				sessionHolder.setQueryParameters(SessionHolder.convertListsToArrays(parameters));
			}
			event.forwardTo(Routes.LOGIN);
			return;
		}

		try {
			View view = ViewServiceFacade.get(UI.getCurrent()).getByClassName(event.getNavigationTarget().getName());
			List<String> privilegesByViewIdAndUserId = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
					.getPrivilegesByViewIdAndUserId(view.getId(), sessionHolder.getApplicationUser().getId());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			DetailsWindow.build(e);
			// new MessageWindowHandler(e);
		}

		location = event.getLocation();
		try {
			executeBuild();
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			DetailsWindow.build(e);
		}
	}

	@Override
	public void executeBuild() throws FrameworkException {
		Long menuId = null;
		if (isSkipRoute()) {
			menuId = getViewDetail().getMenu().getId();
		} else {
			Route route = this.getClass().getAnnotation(Route.class);
			String path = route.value();

			menuId = getSessionHolder().getRouteMenu(path);
			if (menuId == null) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.NO_MENU_FOUND_FOR_ROUTE,
						UI.getCurrent().getLocale(), Arg.build().norm(path));
			}
		}

		MenuServiceFacade menuServiceFacade = MenuServiceFacade.get(UI.getCurrent());
		Menu menu = menuServiceFacade.getById(Menu.class, menuId);
		getViewDetail().setMenu(menu);
		List<Menu> tabMenus = menuServiceFacade.findTabMenus(menu.getId(),
				getSessionHolder().getSelectedRole().getId());
		getViewDetail().setSubMenus(tabMenus);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		add(horizontalLayout);
		tabSheet = new Tabs();
		tabSheet.setOrientation(Orientation.VERTICAL);
		tabSheet.setAutoselect(false);
		horizontalLayout.add(tabSheet);
		horizontalLayout.setFlexGrow(0, tabSheet);
		tabSheet.setSizeUndefined();

		Map<Tab, CxodeConfigurationItem> tabMap = new HashMap<Tab, CxodeConfigurationModuleContext.CxodeConfigurationItem>();
		List<CxodeConfigurationItem> items = CxodeConfigurationModuleContext.getItems();
		for (CxodeConfigurationItem configurationItem : items) {
			// Component newInstance =
			// configurationItem.getConfigurationClass().newInstance();
			Tab tab = new Tab(PropertyResolver.getPropertyValueByLocale(configurationItem.getCaptionKey(),
					UI.getCurrent().getLocale()));
			tabSheet.add(tab);
			tabMap.put(tab, configurationItem);
		}

		tabContent = new VerticalLayout();
		tabContent.setSizeFull();
		horizontalLayout.add(tabContent);
		horizontalLayout.expand(tabContent);

		tabSheet.addSelectedChangeListener(new ComponentEventListener<Tabs.SelectedChangeEvent>() {

			private static final long serialVersionUID = 7545006787010318034L;

			@Override
			public void onComponentEvent(SelectedChangeEvent event) {
				tabContent.removeAll();
				Tab selectedTab = event.getSelectedTab();
				CxodeConfigurationItem cxodeConfigurationItem = tabMap.get(selectedTab);
				try {
					Component newInstance = cxodeConfigurationItem.getConfigurationClass().newInstance();
					tabContent.add(newInstance);
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		Optional<Entry<Tab, CxodeConfigurationItem>> optional = tabMap.entrySet().stream()
				.filter(p -> p.getValue().getOrder() == 1).findFirst();
		if (optional.isPresent()) {
			tabSheet.setSelectedTab(optional.get().getKey());
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
