package software.simple.solutions.framework.core.web;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import com.github.appreciated.app.layout.AppLayout;
import com.github.appreciated.app.layout.behaviour.AppLayoutComponent;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.NavigatorAppLayoutBuilder;
import com.github.appreciated.app.layout.builder.Section;
import com.github.appreciated.app.layout.builder.design.AppLayoutDesign;
import com.github.appreciated.app.layout.builder.elements.NavigatorNavigationElement;
import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.github.appreciated.app.layout.component.button.AppBarNotificationButton;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class AppLayoutView extends VerticalLayout {

	DefaultNotificationHolder notifications = new DefaultNotificationHolder();
	DefaultBadgeHolder badge = new DefaultBadgeHolder();
	private SessionHolder sessionHolder;

	public AppLayoutView() {
		setMargin(false);
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		try {
			setDrawerVariant(Behaviour.LEFT_RESPONSIVE_HYBRID);
		} catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setDrawerVariant(Behaviour variant) throws FrameworkException {
		removeAllComponents();
		IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
		Configuration configuration = configurationService.getByCode(ConfigurationProperty.APPLICATION_NAME);
		Label title = new Label();
		title.setContentMode(ContentMode.HTML);
		if (configuration != null) {
			title.setValue("<strong>" + configuration.getValue() + "</strong>");
			title.setSizeUndefined();
		}

		Image profileImage = new Image();
		profileImage.setWidth("100%");
		profileImage.setSource(new ThemeResource("img/profile-pic-300px.jpg"));
		profileImage.addStyleName("appbar-profile-image");

		NavigatorAppLayoutBuilder navigatorAppLayoutBuilder = AppLayout.getDefaultBuilder(variant).withTitle(title)
				.addToAppBar(new AppBarNotificationButton(notifications, true)).addToAppBar(profileImage);
		navigatorAppLayoutBuilder.withDesign(AppLayoutDesign.MATERIAL);
		Image image = new Image();
		image.setWidth("100%");
		image.setSource(new ThemeResource("img/header-logo.jpg"));
		navigatorAppLayoutBuilder.add(image, Section.HEADER);

		VerticalLayout loggedInAsLayout = new VerticalLayout();
		loggedInAsLayout.addStyleName(ValoTheme.LAYOUT_WELL);
		loggedInAsLayout.setMargin(new MarginInfo(false, false, false, true));
		Label loggedInAsUserLbl = new Label(
				PropertyResolver.getPropertyValueByLocale(SystemProperty.PROFILE_LOGGED_IN_AS,
						new Object[] { sessionHolder.getApplicationUser().getUsername() }));
		loggedInAsUserLbl.addStyleName(ValoTheme.LABEL_H2);
		loggedInAsUserLbl.addStyleName(ValoTheme.LABEL_COLORED);
		loggedInAsLayout.addComponent(loggedInAsUserLbl);

		navigatorAppLayoutBuilder.add(loggedInAsLayout, Section.HEADER);
		navigatorAppLayoutBuilder.withNavigationElementClickListener((element, event) -> {
			if (element instanceof NavigatorNavigationElement) {
				NavigatorNavigationElement nElement = (NavigatorNavigationElement) element;
				System.out.println("Element clicked " + nElement.getViewName());
			}
		});

		MenuServiceFacade menuServiceFacade = MenuServiceFacade.get(UI.getCurrent());
		List<Menu> menus = menuServiceFacade.findAuthorizedMenusByType(sessionHolder.getSelectedRole().getId(),
				Arrays.asList(MenuType.WEB_SIDE_MENU));

		LinkedHashMap<String, String> menuItems = new LinkedHashMap<>();
		if (menus != null) {
			for (Menu menu : menus) {
				menuItems.put(menu.getId().toString(), menu.getName());
				if (menu.getView() != null) {
					try {
						Integer codePoint = menu.getIcon();
						FontAwesome fontAwesome = null;
						if (codePoint != null) {
							fontAwesome = FontAwesome.fromCodepoint(codePoint);
						}
						Class<? extends View> viewClass = (Class<? extends View>) Class
								.forName(menu.getView().getViewClassName());
						navigatorAppLayoutBuilder.add(menu.getName(), fontAwesome, viewClass);
						// navigator.addView(menu.getId().toString(),
						// viewClass);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}

		AppLayoutComponent drawer = navigatorAppLayoutBuilder.build();
		drawer.addStyleName("left");
		addComponent(drawer);
	}

}
