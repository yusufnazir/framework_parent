package software.simple.solutions.framework.core.web;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;

public class ValoMenuLayout extends HorizontalLayout {

	public static final String ID = "dashboard-menu";
	public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
	public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
	private static final String STYLE_VISIBLE = "valo-menu-visible";
	private Label notificationsBadge;
	private Label reportsBadge;
	private MenuItem settingsItem;
	private SessionHolder sessionHolder;
	// private TabSheet tabSheet;
	private CssLayout menuItemsLayout;
	private LinkedHashMap<String, String> menuItems = new LinkedHashMap<>();
	CssLayout menu = new CssLayout();
	
	CssLayout menuArea = new CssLayout();
	CssLayout contentArea = new CssLayout();

	
	VerticalLayout verticalLayout = new VerticalLayout();
	CssLayout headerLayout = new CssLayout();
	CssLayout contentLayout = new CssLayout();

	private Navigator navigator;

	public ValoMenuLayout() throws FrameworkException {
		
		setSizeFull();
		setSpacing(false);

		sessionHolder = (SessionHolder) UI.getCurrent().getData();

		navigator = new Navigator(UI.getCurrent(), contentArea);
		MenuServiceFacade menuServiceFacade = MenuServiceFacade.get(UI.getCurrent());
		List<Menu> menus = menuServiceFacade.findAuthorizedMenusByType(sessionHolder.getSelectedRole().getId(),
				Arrays.asList(MenuType.WEB_SIDE_MENU));

		if (menus != null) {
			for (Menu menu : menus) {
				menuItems.put(menu.getId().toString(), menu.getName());
				if (menu.getView() != null) {
					try {
						Class<? extends View> viewClass = (Class<? extends View>) Class
								.forName(menu.getView().getViewClassName());
						navigator.addView(menu.getId().toString(), viewClass);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}

		// this.tabSheet = tabSheet;
		menuItemsLayout = new CssLayout();
		menuItemsLayout.addStyleName("valo-menuitems");

		// setPrimaryStyleName("valo-menu");
		setId(ID);
//		setSizeUndefined();

		menuArea.setPrimaryStyleName(ValoTheme.MENU_ROOT);

		contentArea.setPrimaryStyleName("valo-content");
		contentArea.addStyleName("v-scrollable");
		contentArea.setSizeFull();

//		headerLayout.setWidth("100%");
//		headerLayout.setMargin(false);

		contentLayout.setSizeFull();

//		verticalLayout.setSizeFull();
//		verticalLayout.setSpacing(true);
//		verticalLayout.setMargin(false);
//		verticalLayout.addComponents(headerLayout, contentLayout);
//		verticalLayout.setExpandRatio(contentLayout, 1);
		contentArea.addComponents(headerLayout, contentLayout);

		Image image = new Image();
		image.setSource(new ThemeResource("img/header-logo.png"));
		image.setHeight("20%");
		image.setWidth("100%");
		headerLayout.addComponent(image);
		headerLayout.setWidth("100%");

		addComponents(menuArea, contentArea);
		setExpandRatio(contentArea, 1);
//		addComponents(menuArea, verticalLayout);
//		setExpandRatio(verticalLayout, 1);

		addMenu(buildMenu());
	}

	public ComponentContainer getContentContainer() {
		return contentLayout;
	}

	public void addMenu(Component menu) {
		menu.addStyleName(ValoTheme.MENU_PART);
		menuArea.addComponent(menu);
	}

	CssLayout buildMenu() throws FrameworkException {
		// Add items
		// menuItems.put("common", "Common UI Elements");
		// menuItems.put("labels", "Labels");
		// menuItems.put("buttons-and-links", "Buttons & Links");
		// menuItems.put("textfields", "Text Fields");
		// menuItems.put("datefields", "Date Fields");
		// menuItems.put("comboboxes", "Combo Boxes");
		// menuItems.put("selects", "Selects");
		// menuItems.put("checkboxes", "Check Boxes & Option Groups");
		// menuItems.put("sliders", "Sliders & Progress Bars");
		// menuItems.put("colorpickers", "Color Pickers");
		// menuItems.put("menubars", "Menu Bars");
		// menuItems.put("trees", "Trees");
		// menuItems.put("tables", "Tables");
		// menuItems.put("dragging", "Drag and Drop");
		// menuItems.put("panels", "Panels");
		// menuItems.put("splitpanels", "Split Panels");
		// menuItems.put("tabs", "Tabs");
		// menuItems.put("accordions", "Accordions");
		// menuItems.put("popupviews", "Popup Views");
		// menuItems.put("calendar", "Calendar");
		// menuItems.put("forms", "Forms");

		HorizontalLayout top = new HorizontalLayout();
		top.setWidth("100%");
		top.setSpacing(false);
		top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		top.addStyleName(ValoTheme.MENU_TITLE);
		menu.addComponent(top);

		Button showMenu = new Button("Menu", event -> {
			if (menu.getStyleName().contains("valo-menu-visible")) {
				menu.removeStyleName("valo-menu-visible");
			} else {
				menu.addStyleName("valo-menu-visible");
			}
		});
		showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
		showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
		showMenu.addStyleName("valo-menu-toggle");
		showMenu.setIcon(FontAwesome.LIST);
		menu.addComponent(showMenu);

		IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
		Configuration configuration = configurationService.getByCode(ConfigurationProperty.APPLICATION_NAME);
		if (configuration != null) {
			Label title = new Label("<h3><strong>" + configuration.getValue() + "</strong></h3>", ContentMode.HTML);
			title.setSizeUndefined();
			top.addComponent(title);
			top.setExpandRatio(title, 1);
		}

		MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");
		MenuItem settingsItem = settings.addItem(sessionHolder.getApplicationUser().getUsername(),
				new ThemeResource("img/profile-pic-300px.jpg"), null);
		// settingsItem.addItem("Edit Profile", null);
		// settingsItem.addItem("Preferences", null);
		// settingsItem.addSeparator();
		// settingsItem.addItem("Sign Out", null);
		menu.addComponent(settings);

		menuItemsLayout.setPrimaryStyleName("valo-menuitems");
		menu.addComponent(menuItemsLayout);

		Label label = null;
		int count = -1;
		for (final Entry<String, String> item : menuItems.entrySet()) {
			// if (item.getKey().equals("labels")) {
			// label = new Label("Components", ContentMode.HTML);
			// label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
			// label.addStyleName(ValoTheme.LABEL_H4);
			// label.setSizeUndefined();
			// menuItemsLayout.addComponent(label);
			// }
			// if (item.getKey().equals("panels")) {
			// label.setValue(label.getValue() + " <span
			// class=\"valo-menu-badge\">" + count + "</span>");
			// count = 0;
			// label = new Label("Containers", ContentMode.HTML);
			// label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
			// label.addStyleName(ValoTheme.LABEL_H4);
			// label.setSizeUndefined();
			// menuItemsLayout.addComponent(label);
			// }
			// if (item.getKey().equals("calendar")) {
			// label.setValue(label.getValue() + " <span
			// class=\"valo-menu-badge\">" + count + "</span>");
			// count = 0;
			// label = new Label("Other", ContentMode.HTML);
			// label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
			// label.addStyleName(ValoTheme.LABEL_H4);
			// label.setSizeUndefined();
			// menuItemsLayout.addComponent(label);
			// }
			Button b = new Button(item.getValue(), event -> navigator.navigateTo(item.getKey()));
			// if (count == 2) {
			// b.setCaption(b.getCaption() + " <span
			// class=\"valo-menu-badge\">123</span>");
			// }
			b.setCaptionAsHtml(true);
			b.setWidth("200px");
			b.setPrimaryStyleName(ValoTheme.MENU_ITEM);
			b.setIcon(FontAwesome.HOME);
			menuItemsLayout.addComponent(b);
			count++;
		}
		// label.setValue(label.getValue() + " <span class=\"valo-menu-badge\">"
		// + count + "</span>");

		return menu;
	}

}
