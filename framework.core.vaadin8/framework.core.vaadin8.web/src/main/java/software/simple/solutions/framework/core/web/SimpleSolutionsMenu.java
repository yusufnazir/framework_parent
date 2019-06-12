package software.simple.solutions.framework.core.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.PostViewChangeEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.ProfileUpdatedEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.TabSheetViewEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.UserLoggedOutEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEventBus;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@SuppressWarnings({ "serial" })
public final class SimpleSolutionsMenu extends CustomComponent {

	private static final Logger logger = LogManager.getLogger(SimpleSolutionsMenu.class);

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

	public SimpleSolutionsMenu(TabSheet tabSheet) throws FrameworkException {
		// this.tabSheet = tabSheet;
		menuItemsLayout = new CssLayout();
		menuItemsLayout.addStyleName("valo-menuitems");

		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		setPrimaryStyleName("valo-menu");
		setId(ID);
		setSizeUndefined();

		// There's only one DashboardMenu per UI so this doesn't need to be
		// unregistered from the UI-scoped DashboardEventBus.
		SimpleSolutionsEventBus.register(this);

		setCompositionRoot(buildContent());
	}

	private Component buildContent() throws FrameworkException {
		final CssLayout menuContent = new CssLayout();
		menuContent.addStyleName("sidebar");
		menuContent.addStyleName(ValoTheme.MENU_PART);
		menuContent.addStyleName("no-vertical-drag-hints");
		menuContent.addStyleName("no-horizontal-drag-hints");
		menuContent.setWidth(null);
		menuContent.setHeight("100%");

		menuContent.addComponent(buildTitle());
		menuContent.addComponent(buildUserMenu());
		menuContent.addComponent(buildToggleButton());

		buildMainMenuItems();
		menuContent.addComponent(menuItemsLayout);

		return menuContent;
	}

	private Component buildTitle() {
		Label logo = new Label("QuickTickets <strong>Dashboard</strong>", ContentMode.HTML);
		logo.setSizeUndefined();
		HorizontalLayout logoWrapper = new HorizontalLayout(logo);
		logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
		logoWrapper.addStyleName("valo-menu-title");
		logoWrapper.setSpacing(false);
		return logoWrapper;
	}

	private ApplicationUser getCurrentUser() {
		return sessionHolder.getApplicationUser();
	}

	private Component buildUserMenu() {
		final MenuBar settings = new MenuBar();
		settings.addStyleName("user-menu");
		final ApplicationUser user = getCurrentUser();
		settingsItem = settings.addItem("", new ThemeResource("img/profile-pic-300px.jpg"), null);
		updateUserName(null);
		settingsItem.addItem(
				PropertyResolver.getPropertyValueByLocale("system.settings.profile.edit.profile", getLocale()),
				new Command() {
					@Override
					public void menuSelected(final MenuItem selectedItem) {
						ProfilePreferencesWindow.open(user, false);
					}
				});
		settingsItem.addItem(
				PropertyResolver.getPropertyValueByLocale("system.settings.profile.preferences", getLocale()),
				new Command() {
					@Override
					public void menuSelected(final MenuItem selectedItem) {
						ProfilePreferencesWindow.open(user, true);
					}
				});
		settingsItem.addSeparator();
		settingsItem.addItem(PropertyResolver.getPropertyValueByLocale("system.settings.profile.sign.out", getLocale()),
				new Command() {
					@Override
					public void menuSelected(final MenuItem selectedItem) {
						SimpleSolutionsEventBus.post(new UserLoggedOutEvent());
					}
				});
		return settings;
	}

	private Component buildToggleButton() {
		Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
			@Override
			public void buttonClick(final ClickEvent event) {
				if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
					getCompositionRoot().removeStyleName(STYLE_VISIBLE);
				} else {
					getCompositionRoot().addStyleName(STYLE_VISIBLE);
				}
			}
		});
		valoMenuToggleButton.setIcon(FontAwesome.LIST);
		valoMenuToggleButton.addStyleName("valo-menu-toggle");
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
		return valoMenuToggleButton;
	}

	private Component buildBadgeWrapper(final Component menuItemButton, final Component badgeLabel) {
		CssLayout dashboardWrapper = new CssLayout(menuItemButton);
		dashboardWrapper.addStyleName("badgewrapper");
		dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
		badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
		badgeLabel.setWidthUndefined();
		badgeLabel.setVisible(false);
		dashboardWrapper.addComponent(badgeLabel);
		return dashboardWrapper;
	}

	@Override
	public void attach() {
		super.attach();
		// updateNotificationsCount(null);
	}

	@Subscribe
	public void postViewChange(final PostViewChangeEvent event) {
		// After a successful view change the menu can be hidden in mobile view.
		getCompositionRoot().removeStyleName(STYLE_VISIBLE);
	}

	// @Subscribe
	// public void updateNotificationsCount(
	// final NotificationsCountUpdatedEvent event) {
	// int unreadNotificationsCount = DashboardUI.getDataProvider()
	// .getUnreadNotificationsCount();
	// notificationsBadge.setValue(String.valueOf(unreadNotificationsCount));
	// notificationsBadge.setVisible(unreadNotificationsCount > 0);
	// }

	// @Subscribe
	// public void updateReportsCount(final ReportsCountUpdatedEvent event) {
	// reportsBadge.setValue(String.valueOf(event.getCount()));
	// reportsBadge.setVisible(event.getCount() > 0);
	// }

	@Subscribe
	public void updateUserName(final ProfileUpdatedEvent event) {
		ApplicationUser user = getCurrentUser();
		settingsItem.setText(user.getUsername());
	}

	public final class ValoMenuItemButton extends Button {

		private static final String STYLE_SELECTED = "selected";

		private final SimpleSolutionsMenuItem viewItem;

		public ValoMenuItemButton(final SimpleSolutionsMenuItem viewItem) {
			this.viewItem = viewItem;
			setPrimaryStyleName("valo-menu-item");
			setIcon(viewItem.getIcon());
			setCaption(viewItem.getMenuName());
			SimpleSolutionsEventBus.register(this);
			addClickListener(new ClickListener() {
				@Override
				public void buttonClick(final ClickEvent event) {
					try {
						manageItemClick(viewItem);
					} catch (FrameworkException e) {
						logger.error(e.getMessage(), e);
						new MessageWindowHandler(e);
					}
				}
			});

		}

		@Subscribe
		public void postViewChange(final PostViewChangeEvent event) {
			removeStyleName(STYLE_SELECTED);
			if (event.getView() == viewItem) {
				addStyleName(STYLE_SELECTED);
			}
		}

		private void manageItemClick(SimpleSolutionsMenuItem simpleSolutionsMenuItem) throws FrameworkException {
			if (viewItem.isParent()) {
				if (viewItem.isReturnToMain()) {
					buildMainMenuItems();
				} else {
					buildMenuItems(simpleSolutionsMenuItem);
				}
			} else {
				updateTabSheetContent(viewItem);
			}
		}
	}

	private void buildMainMenuItems() throws FrameworkException {
		sessionHolder.setSimpleSolutionsMenuItem(null);
	}

	private void buildMenuItems(SimpleSolutionsMenuItem simpleSolutionsMenuItem) throws FrameworkException {
		sessionHolder.setSimpleSolutionsMenuItem(simpleSolutionsMenuItem);
		UI.getCurrent().getNavigator().navigateTo(simpleSolutionsMenuItem.getViewName());
	}

	private void updateTabSheetContent(SimpleSolutionsMenuItem simpleSolutionsMenuItem) throws FrameworkException {
		sessionHolder.setSimpleSolutionsMenuItem(simpleSolutionsMenuItem);
		UI.getCurrent().getNavigator().navigateTo(simpleSolutionsMenuItem.getViewName());
	}

	// @Subscribe
	// public void buildMenuItems(final MenuStructureEvent event) throws
	// FrameworkException {
	// SimpleSolutionsMenuItem simpleSolutionsMenuItem =
	// sessionHolder.getSimpleSolutionsMenuItem();
	// Menu parentMenu = simpleSolutionsMenuItem.getMenu();
	// menuItemsLayout.removeAllComponents();
	// AuthorizedViewListHelper authorizedViewListHelper = new
	// AuthorizedViewListHelper();
	// List<SimpleSolutionsMenuItem> views =
	// authorizedViewListHelper.createMenuItems(parentMenu);
	// sessionHolder.setAuthorizedViews(views);

	// if (views != null) {
	// for (final SimpleSolutionsMenuItem view : views) {
	// if (view.isDivider()) {
	// Label label = new Label("", ContentMode.HTML);
	// label.setPrimaryStyleName(ValoTheme.MENU_SUBTITLE);
	// label.addStyleName(ValoTheme.LABEL_H4);
	// menuItemsLayout.addComponent(label);
	// } else {
	// Component menuItemComponent = new ValoMenuItemButton(view);
	//
	// menuItemsLayout.addComponent(menuItemComponent);
	// }
	// }
	// }
	// }

	@Subscribe
	public void updateTabSheetContent(final TabSheetViewEvent event) throws FrameworkException {
		Long menuId = event.getMenuId();
		Menu menu = MenuServiceFacade.get(UI.getCurrent()).getById(Menu.class, menuId);
		SimpleSolutionsMenuItem menuItem = new SimpleSolutionsMenuItem(menu);
		menuItem.setSearchedEntity(event.getSearchedEntity());
		updateTabSheetContent(menuItem);
	}

}
