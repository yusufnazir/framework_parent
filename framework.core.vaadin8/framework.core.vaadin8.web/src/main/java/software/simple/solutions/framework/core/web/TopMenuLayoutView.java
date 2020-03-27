package software.simple.solutions.framework.core.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;

import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.functions.Consumer;
import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.ConfirmWindow;
import software.simple.solutions.framework.core.components.ConfirmWindow.ConfirmationHandler;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.config.SystemObserver;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.UserRole;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.LookupMenuSelectedEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEventBus;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.pojo.SimpleMenuItem;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.FileServiceFacade;
import software.simple.solutions.framework.core.service.facade.LanguageServiceFacade;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.ViewServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.MenuIndexComparator;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SimpleMenuIndexComparator;
import software.simple.solutions.framework.core.valueobjects.LanguageVO;
import software.simple.solutions.framework.core.web.view.ChangePasswordView;

public class TopMenuLayoutView extends VerticalLayout {

	private static final long serialVersionUID = -6481063751855663777L;

	private static final Logger logger = LogManager.getLogger(TopMenuLayoutView.class);

	private TabSheet tabSheet;
	private MenuBar menuBar_;
	private MenuBar userInfoMenuBar;
	private SessionHolder sessionHolder;
	private ConcurrentMap<String, Object> referenceKeys;
	private List<Menu> menus;
	private MenuItem userMenuItem;
	private HorizontalLayout headerLayout;
	private Image applicationLogoImage;
	private Navigator navigator;
	private UI ui;
	private boolean authorizationByRole = true;
	private boolean useNavigator = false;

	public TopMenuLayoutView() throws FrameworkException {
		super();
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();

		if (useNavigator) {
			createNavigatorListeners();
		}

		referenceKeys = new ConcurrentHashMap<String, Object>();
		sessionHolder.setReferenceKeys(referenceKeys);
		SimpleSolutionsEventBus.register(this);

		setMargin(true);
		setSpacing(true);
		buildLayout();

		setUpMenus();

		SystemObserver systemObserver = ContextProvider.getBean(SystemObserver.class);
		systemObserver.getApplicationLogoChangeObserver().subscribe(new Consumer<Boolean>() {

			@Override
			public void accept(Boolean t) throws Exception {
				updateApplicationLogo();
			}
		});

		if (useNavigator) {
			createDefaultNavigation();
		}

	}

	private void createDefaultNavigation() {
		String state = navigator.getState();
		if (StringUtils.isNotBlank(state)) {
			try {
				navigator.navigateTo(state);
			} catch (IllegalArgumentException e) {
				Notification notification = new Notification(
						PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_MENU_NOT_AUTHORIZED,
								UI.getCurrent().getLocale()),
						Type.TRAY_NOTIFICATION);
				notification.setDescription(PropertyResolver.getPropertyValueByLocale(
						SystemProperty.SYSTEM_MENU_NOT_AUTHORIZED_MESSAGE, UI.getCurrent().getLocale()));
				notification.show(UI.getCurrent().getPage());
				VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
				HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest).getHttpServletRequest();
				String requestUrl = httpServletRequest.getServletContext().getContextPath();
				Page.getCurrent().replaceState(requestUrl + "/app");
			}
		}
	}

	private void createNavigatorListeners() {
		navigator = new Navigator(ui, new ViewDisplay() {

			private static final long serialVersionUID = -799205460550821576L;

			@Override
			public void showView(com.vaadin.navigator.View view) {
				String state = navigator.getState();
				// tabSheet.addTab(view.getViewComponent());
				System.out.println("state: " + state);
			}
		});
		navigator.addView("", new com.vaadin.navigator.View() {

			private static final long serialVersionUID = 8127200007422905310L;
		});
		navigator.addViewChangeListener(new ViewChangeListener() {

			private static final long serialVersionUID = -3678171551078201020L;

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				Long menuId = NumberUtil.getLong(event.getViewName());
				if (menuId != null) {
					IMenuService menuService = ContextProvider.getBean(IMenuService.class);
					try {
						boolean userHasAccess = false;
						if (authorizationByRole) {
							userHasAccess = menuService.doesUserHaveAccess(sessionHolder.getApplicationUser().getId(),
									sessionHolder.getSelectedRole().getId(), menuId);
						} else {
							userHasAccess = menuService.doesUserHaveAccess(sessionHolder.getApplicationUser().getId(),
									null, menuId);
						}

						if (userHasAccess) {
							Map<String, String> parameterMap = event.getParameterMap();
							String uuid = parameterMap.get("uuid");
							boolean uuidExists = sessionHolder.uuidExists(uuid);
							if (uuidExists) {
								Component generatedMenu = sessionHolder.getGeneratedMenu(uuid);
								tabSheet.setSelectedTab(generatedMenu);
							} else {
								handleNavigation(menuId, uuid);
							}
							return true;
						}
					} catch (FrameworkException e) {
						logger.error(e.getMessage(), e);
					}
				}
				return true;
			}
		});
	}

	private void setUpMenus() throws FrameworkException {

		IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
		Configuration consolidateRoleConfiguration = configurationService
				.getByCode(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE);
		authorizationByRole = true;
		if (consolidateRoleConfiguration != null && consolidateRoleConfiguration.getBoolean()) {
			authorizationByRole = false;
		}
		if (authorizationByRole) {
			createRoleMenuItems();
		} else {
			createMenu(null);
		}

		/*
		 * Add Languages options
		 */
		createLanguageMenuItems();

		/*
		 * Add changepassword options
		 */
		createChangePasswordItem();

		/*
		 * Add logout option
		 */
		createLogoutMenuItem();

	}

	private void createRoleMenuItems() throws FrameworkException {
		final MenuItem parentRole = userMenuItem.addItem(
				PropertyResolver.getPropertyValueByLocale("core.menu.role", UI.getCurrent().getLocale()),
				FontAwesome.USER_SECRET, new Command() {

					private static final long serialVersionUID = -493308176706441856L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						menuBar_.setVisible(true);
						tabSheet.removeAllComponents();
					}
				});

		List<UserRole> userRoles = RoleServiceFacade.get(UI.getCurrent())
				.findRolesByUserId(sessionHolder.getApplicationUser().getId());
		/*
		 * Add the roles as childeren of the parent, which is the caption of the
		 * role menu.
		 */
		if (userRoles != null) {
			for (final UserRole userRole : userRoles) {
				/*
				 * Add the roles
				 */
				if (userRole.getRole() != null) {
					MenuItem roleItem = parentRole.addItem(userRole.getRole().getName(), new Command() {

						private static final long serialVersionUID = -493308176706441856L;

						@Override
						public void menuSelected(MenuItem selectedItem) {
							try {
								createMenu(userRole.getRole());
								parentRole.getChildren().stream().forEach(p -> p.setChecked(false));
								selectedItem.setCheckable(true);
								selectedItem.setChecked(true);
								sessionHolder.setSelectedRole(userRole.getRole());
								tabSheet.removeAllComponents();

								/*
								 * update the parent description to show
								 * selected role
								 */
								userMenuItem.setText(sessionHolder.getApplicationUser().getUsername() + " ("
										+ userRole.getRole().getName() + ")");
							} catch (FrameworkException e) {
								logger.error("Something went wrong [" + e.getMessage() + "]");
								new MessageWindowHandler(e);
							}
						}
					});
					/*
					 * Create menulayout for the first role
					 */
					Optional<UserRole> findFirst = userRoles.stream().filter(p -> p.getRole() != null).findFirst();
					if (findFirst.isPresent() && findFirst.get().equals(userRole)) {
						roleItem.getCommand().menuSelected(roleItem);
					}
				}
			}
		}
	}

	private void createMenu(Role role) throws FrameworkException {
		menuBar_.setVisible(true);
		menuBar_.removeItems();
		if (role != null) {
			sessionHolder.setSelectedRole(role);
			menus = MenuServiceFacade.get(UI.getCurrent()).findAuthorizedMenus(role.getId());
		} else {
			menus = MenuServiceFacade.get(UI.getCurrent())
					.findAuthorizedMenusByUser(sessionHolder.getApplicationUser().getId());
		}
		List<SimpleMenuItem> parents = new ArrayList<SimpleMenuItem>();
		Collections.sort(menus, new MenuIndexComparator());

		for (Menu menu : menus) {
			if (menu.getParentMenu() == null) {
				SimpleMenuItem menuItem = new SimpleMenuItem();
				menuItem.setId(menu.getId());
				menuItem.setMenu(menu);
				menuItem.setIndex(menu.getIndex());
				menuItem.setMenuItems(getChildrens(menu));
				parents.add(menuItem);
			}
		}

		Collections.sort(parents, new SimpleMenuIndexComparator());
		for (SimpleMenuItem menuItem : parents) {
			Command command = getCommand(
					menuItem.getMenu().getView() == null ? null : menuItem.getMenu().getView().getId(),
					menuItem.getMenu());
			String menuName = menuItem.getMenu().getKey() == null ? menuItem.getMenu().getName()
					: PropertyResolver.getPropertyValueByLocale(menuItem.getMenu().getKey(),
							UI.getCurrent().getLocale());
			MenuBar.MenuItem item = menuBar_.addItem(menuName, command);
			setMenuItems(menuItem, item);
		}
	}

	private void setMenuItems(SimpleMenuItem menuItem, MenuBar.MenuItem item) {
		if (menuItem.getMenus() != null && menuItem.getMenus().size() > 0) {
			for (SimpleMenuItem child : menuItem.getMenus()) {

				Command command = getCommand(
						child.getMenu().getView() == null ? null : child.getMenu().getView().getId(), child.getMenu());

				String menuName = child.getMenu().getKey() == null ? child.getMenu().getName()
						: PropertyResolver.getPropertyValueByLocale(child.getMenu().getKey(),
								UI.getCurrent().getLocale());

				if (useNavigator) {
					if (child.getMenu().getView() != null) {
						try {
							String viewClassName = child.getMenu().getView().getViewClassName();
							Class<? extends com.vaadin.navigator.View> forName = (Class<? extends com.vaadin.navigator.View>) Class
									.forName(viewClassName);
							navigator.addView(child.getMenu().getId().toString(), forName);
						} catch (ClassNotFoundException e) {
							logger.error(e.getMessage(), e);
						}
					}
				}

				MenuItem parent = item.addItem(menuName, command);
				setMenuItems(child, parent);
			}
		}
	}

	private List<SimpleMenuItem> getChildrens(Menu parent) {
		List<SimpleMenuItem> childrens = new ArrayList<SimpleMenuItem>();
		for (Menu menu : menus) {
			SimpleMenuItem menuItem = new SimpleMenuItem();
			if (menu.getParentMenu() != null && menu.getParentMenu().getId().compareTo(parent.getId()) == 0) {
				menuItem.setId(menu.getId());
				menuItem.setMenu(menu);
				menuItem.setIndex(menu.getIndex());
				menuItem.setMenuItems(getChildrens(menu));
				childrens.add(menuItem);
			}
		}
		Collections.sort(childrens, new SimpleMenuIndexComparator());
		return childrens;
	}

	private Command getCommand(final Long viewId, final Menu menu) {
		if (viewId == null) {
			return null;
		}
		return new MenuBar.Command() {

			private static final long serialVersionUID = 4714225361223264974L;

			public void menuSelected(MenuItem selectedItem) {
				if (useNavigator) {
					String uuid = UUID.randomUUID().toString();
					navigator.navigateTo(menu.getId().toString() + "/uuid=" + uuid);
				} else {
					handleMenuSelected(viewId, menu);
				}
			}
		};
	}

	@Subscribe
	public void handleLookupMenuSelected(final LookupMenuSelectedEvent lookupMenuSelectedEvent) {
		Tab tab = handleMenuSelected(lookupMenuSelectedEvent.getMenu().getView().getId(),
				lookupMenuSelectedEvent.getMenu());
		tab.setIcon(FontAwesome.ADJUST);
	}

	// public Tab handleNavigateTo(Long viewId, Menu menu) {
	// try {
	// if (!tabSheet.isVisible()) {
	// tabSheet.setVisible(true);
	// }
	// View view = ViewServiceFacade.get(UI.getCurrent()).getById(View.class,
	// viewId);
	// AbstractBaseView abstractBaseView = (AbstractBaseView)
	// ViewUtil.initView(view.getViewClassName(),
	// sessionHolder.getSelectedRole().getId());
	// if (abstractBaseView == null) {
	// Notification notification = new
	// Notification(PropertyResolver.getPropertyValueByLocale(
	// "core.menu.no.view.found", UI.getCurrent().getLocale()),
	// Type.ERROR_MESSAGE);
	// notification.setDescription(PropertyResolver.getPropertyValueByLocale("core.menu.no.view.found.message",
	// UI.getCurrent().getLocale()));
	// notification.show(UI.getCurrent().getPage());
	// return null;
	// }
	// List<String> privileges =
	// RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
	// .getPrivilegesByViewIdAndRoleId(viewId,
	// sessionHolder.getSelectedRole().getId());
	// abstractBaseView.getViewDetail().setPrivileges(privileges);
	//
	// abstractBaseView.getViewDetail().setMenu(menu);
	// abstractBaseView.getViewDetail().setView(view);
	// abstractBaseView.executeBuild();
	// abstractBaseView.executeSearch();
	// tabSheet.addComponent(abstractBaseView);
	// tabSheet.getTab(abstractBaseView).setCaption(PropertyResolver.getPropertyValueByLocale(
	// abstractBaseView.getViewDetail().getMenu().getKey(),
	// UI.getCurrent().getLocale()));
	// tabSheet.getTab(abstractBaseView).setClosable(true);
	// tabSheet.setSelectedTab(abstractBaseView);
	//
	// Tab tab = tabSheet.getTab(abstractBaseView);
	// return tab;
	// } catch (FrameworkException e) {
	// new MessageWindowHandler(e);
	// }
	// return null;
	// }

	public Tab handleNavigation(Long menuId, String uuid) {
		if (menuId == null) {
			return null;
		}
		try {
			if (!tabSheet.isVisible()) {
				tabSheet.setVisible(true);
			}
			IMenuService menuService = ContextProvider.getBean(IMenuService.class);
			Menu menu = menuService.get(Menu.class, menuId);
			Long viewId = menu.getView().getId();

			View view = ViewServiceFacade.get(UI.getCurrent()).getById(View.class, viewId);
			AbstractBaseView abstractBaseView = (AbstractBaseView) ViewUtil.initView(view.getViewClassName());
			if (abstractBaseView == null) {
				Notification notification = new Notification(PropertyResolver.getPropertyValueByLocale(
						"core.menu.no.view.found", UI.getCurrent().getLocale()), Type.ERROR_MESSAGE);
				notification.setDescription(PropertyResolver.getPropertyValueByLocale("core.menu.no.view.found.message",
						UI.getCurrent().getLocale()));
				notification.show(UI.getCurrent().getPage());
				return null;
			}

			IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
			Configuration consolidateRoleConfiguration = configurationService
					.getByCode(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE);
			boolean consolidateRoles = false;
			if (consolidateRoleConfiguration != null && consolidateRoleConfiguration.getBoolean()) {
				consolidateRoles = true;
			}

			if (consolidateRoles) {
				List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
						.getPrivilegesByViewIdAndUserId(viewId, sessionHolder.getApplicationUser().getId());
				abstractBaseView.getViewDetail().setPrivileges(privileges);
			} else {
				List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
						.getPrivilegesByViewIdAndRoleId(viewId, sessionHolder.getSelectedRole().getId());
				abstractBaseView.getViewDetail().setPrivileges(privileges);
			}

			abstractBaseView.getViewDetail().setMenu(menu);
			abstractBaseView.getViewDetail().setView(view);
			abstractBaseView.setUuid(uuid);
			abstractBaseView.executeBuild();
			abstractBaseView.executeSearch();
			tabSheet.addComponent(abstractBaseView);
			Tab tab = tabSheet.getTab(abstractBaseView);
			tab.setCaption(PropertyResolver.getPropertyValueByLocale(
					abstractBaseView.getViewDetail().getMenu().getKey(), UI.getCurrent().getLocale()));
			tabSheet.getTab(abstractBaseView).setClosable(true);
			tabSheet.setSelectedTab(abstractBaseView);

			sessionHolder.addGeneratedMenu(uuid, abstractBaseView);
			return tab;
		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}
		return null;
	}

	public Tab handleMenuSelected(Long viewId, Menu menu) {
		try {
			if (!tabSheet.isVisible()) {
				tabSheet.setVisible(true);
			}
			View view = ViewServiceFacade.get(UI.getCurrent()).getById(View.class, viewId);
			AbstractBaseView abstractBaseView = (AbstractBaseView) ViewUtil.initView(view.getViewClassName());
			if (abstractBaseView == null) {
				Notification notification = new Notification(PropertyResolver.getPropertyValueByLocale(
						"core.menu.no.view.found", UI.getCurrent().getLocale()), Type.ERROR_MESSAGE);
				notification.setDescription(PropertyResolver.getPropertyValueByLocale("core.menu.no.view.found.message",
						UI.getCurrent().getLocale()));
				notification.show(UI.getCurrent().getPage());
				return null;
			}

			IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
			Configuration consolidateRoleConfiguration = configurationService
					.getByCode(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE);
			boolean consolidateRoles = false;
			if (consolidateRoleConfiguration != null && consolidateRoleConfiguration.getBoolean()) {
				consolidateRoles = true;
			}

			if (consolidateRoles) {
				List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
						.getPrivilegesByViewIdAndUserId(viewId, sessionHolder.getApplicationUser().getId());
				abstractBaseView.getViewDetail().setPrivileges(privileges);
			} else {
				List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
						.getPrivilegesByViewIdAndRoleId(viewId, sessionHolder.getSelectedRole().getId());
				abstractBaseView.getViewDetail().setPrivileges(privileges);
			}

			abstractBaseView.getViewDetail().setMenu(menu);
			abstractBaseView.getViewDetail().setView(view);
			abstractBaseView.executeBuild();
			abstractBaseView.executeSearch();
			tabSheet.addComponent(abstractBaseView);
			tabSheet.getTab(abstractBaseView).setCaption(PropertyResolver.getPropertyValueByLocale(
					abstractBaseView.getViewDetail().getMenu().getKey(), UI.getCurrent().getLocale()));
			tabSheet.getTab(abstractBaseView).setClosable(true);
			tabSheet.setSelectedTab(abstractBaseView);

			Tab tab = tabSheet.getTab(abstractBaseView);
			return tab;
		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}
		return null;
	}

	private MenuItem createLanguageMenuItems() throws FrameworkException {
		MenuItem languageItem = userMenuItem.addItem(
				PropertyResolver.getPropertyValueByLocale("core.menu.languages", UI.getCurrent().getLocale()),
				FontAwesome.FLAG, null);

		LanguageVO vo = new LanguageVO();
		vo.setEntityClass(Language.class);
		vo.setActive(true);
		PagingResult<Language> languageResult = LanguageServiceFacade.get(UI.getCurrent()).findBySearch(vo,
				new PagingSetting());

		if (languageResult != null && languageResult.getResult() != null) {
			for (final Language language : languageResult.getResult()) {

				Resource resource = null;
				// if (language.getFlag16() != null &&
				// language.getFlag16().getFileObject() != null) {
				//
				// /*
				// * Byte[] to streamsource
				// */
				// StreamSource streamSource = new StreamSource() {
				//
				// private static final long serialVersionUID =
				// 1156320555650907794L;
				// InputStream is = new
				// ByteArrayInputStream(language.getFlag16().getFileObject());
				//
				// @Override
				// public InputStream getStream() {
				// return is;
				// }
				// };
				//
				// resource = new StreamResource(streamSource,
				// language.getProperty() == null ? language.getName()
				// :
				// MessageHandler.getMessage(language.getProperty().getCode()));
				// }

				languageItem.addItem(language.getKey() == null ? language.getCode()
						: PropertyResolver.getPropertyValueByLocale(language.getKey(), UI.getCurrent().getLocale()),
						new Command() {

							private static final long serialVersionUID = -5927663312101535392L;

							@Override
							public void menuSelected(MenuItem selectedItem) {
								Locale locale = new Locale(language.getCode());
								UI.getCurrent().setLocale(locale);
								sessionHolder.setLocale(locale);
								createMenuStructure();
							}
						});
			}
		}
		return userMenuItem;
	}

	private MenuItem createChangePasswordItem() {
		return userMenuItem.addItem(PropertyResolver.getPropertyValueByLocale(ApplicationUserProperty.CHANGE_PASSWORD,
				UI.getCurrent().getLocale()), FontAwesome.KEY, new Command() {
					private static final long serialVersionUID = -3515364978684559560L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						try {
							ChangePasswordView changePasswordView = new ChangePasswordView();
							changePasswordView.setMandatory(false);
							tabSheet.addComponent(changePasswordView);
							tabSheet.getTab(changePasswordView).setCaption(PropertyResolver.getPropertyValueByLocale(
									ApplicationUserProperty.CHANGE_PASSWORD, UI.getCurrent().getLocale()));
							tabSheet.getTab(changePasswordView).setClosable(true);
							tabSheet.setSelectedTab(changePasswordView);
							if (!tabSheet.isVisible()) {
								tabSheet.setVisible(true);
							}
						} catch (FrameworkException e) {
							new MessageWindowHandler(e);
						}
					}
				});
	}

	private MenuItem createLogoutMenuItem() {
		userMenuItem.addItem(PropertyResolver.getPropertyValueByLocale("core.menu.logout", UI.getCurrent().getLocale()),
				FontAwesome.CLOSE, new Command() {
					private static final long serialVersionUID = 4586595958059959129L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
						HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest)
								.getHttpServletRequest();
						String requestUrl = httpServletRequest.getServletContext().getContextPath();
						SecurityContextHolder.clearContext();
						getUI().getSession().close();
						getUI().getPage().setLocation(requestUrl + "/app");
					}
				});

		return userMenuItem;
	}

	public VerticalLayout buildLayout() throws FrameworkException {
		headerLayout = createHeaderLayout();

		final VerticalLayout footerLayout = new VerticalLayout(new Label("FOOTER"));
		// CONTENT
		final VerticalLayout contentLayout = new VerticalLayout();
		for (int i = 0; i < 80; i++) {
			contentLayout.addComponent(new Button("test " + i));
		}
		// final Panel contentPanel = new Panel(contentLayout);
		// contentPanel.setSizeFull();

		buildTabSheet();

		// Panel panel = new Panel();
		// panel.setSizeFull();
		// panel.setContent(tabSheet);
		// tabSheet.setWidth("100%");
		// addComponents(headerLayout, panel);
		// setExpandRatio(panel, 1);

		setSizeFull();
		addComponents(headerLayout, tabSheet);
		setExpandRatio(tabSheet, 1);

		return this;
	}

	private HorizontalLayout createHeaderLayout() throws FrameworkException {
		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.setWidth("100%");
		headerLayout.setMargin(false);
		// headerLayout.setHeight("100px");

		Image logoImageHolder = createLogoImageHolder();
		headerLayout.addComponent(logoImageHolder);

		menuBar_ = createMenuStructure();
		headerLayout.addComponent(menuBar_);
		// headerLayout.setExpandRatio(menuBar, 1);

		// menuBarRole = createRoleMenuStructure();
		// headerLayout.addComponent(menuBarRole);
		// headerLayout.setExpandRatio(menuBarRole, 1);

		userInfoMenuBar = createUserMenuStructure();
		headerLayout.addComponent(userInfoMenuBar);
		headerLayout.setExpandRatio(userInfoMenuBar, 1);
		headerLayout.setComponentAlignment(userInfoMenuBar, Alignment.TOP_RIGHT);

		return headerLayout;
	}

	private Image createLogoImageHolder() throws FrameworkException {
		applicationLogoImage = new Image();
		applicationLogoImage.setWidth("200px");
		applicationLogoImage.setHeight("40px");
		updateApplicationLogo();

		return applicationLogoImage;
	}

	private void updateApplicationLogo() throws FrameworkException {
		Configuration applicationLogoHeightConfiguration = ConfigurationServiceFacade.get(UI.getCurrent())
				.getByCode(ConfigurationProperty.APPLICATION_LOGO_HEIGHT);
		if (applicationLogoHeightConfiguration != null) {
			Long height = applicationLogoHeightConfiguration.getLong();
			if (height != null && height.compareTo(0L) > 0) {
				applicationLogoImage.setHeight(height + "px");
			}
		}
		Configuration applicationLogoWidthConfiguration = ConfigurationServiceFacade.get(UI.getCurrent())
				.getByCode(ConfigurationProperty.APPLICATION_LOGO_WIDTH);
		if (applicationLogoWidthConfiguration != null) {
			Long width = applicationLogoWidthConfiguration.getLong();
			if (width != null && width.compareTo(0L) > 0) {
				applicationLogoImage.setWidth(width + "px");
			}
		}

		applicationLogoImage.setSource(new ThemeResource("../cxode/img/your-logo-here.png"));
		applicationLogoImage.addStyleName(Style.MAIN_VIEW_APPLICATION_LOGO);

		new Thread(new Runnable() {

			@Override
			public void run() {
				Resource resource = new StreamResource(new StreamSource() {

					private static final long serialVersionUID = 7941811283553249939L;

					@Override
					public InputStream getStream() {
						try {
							Configuration configuration = ConfigurationServiceFacade.get(ui)
									.getByCode(ConfigurationProperty.APPLICATION_LOGO);
							if (configuration != null) {
								EntityFile entityFile = FileServiceFacade.get(ui).findFileByEntityAndType(
										configuration.getId().toString(), Configuration.class.getName(),
										ConfigurationProperty.APPLICATION_LOGO);
								if (entityFile != null && entityFile.getFileObject() != null) {
									return new ByteArrayInputStream(entityFile.getFileObject());
								}
							}
						} catch (FrameworkException e) {
							logger.error(e.getMessage(), e);
						}
						try {
							File file = new ClassPathResource("VAADIN/themes/cxode/img/your-logo-here.png").getFile();
							return new FileInputStream(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						return null;
					}
				}, UUID.randomUUID().toString());
				ui.access(new Runnable() {

					@Override
					public void run() {
						applicationLogoImage.setSource(resource);
					}
				});
			}
		}).start();
	}

	private MenuBar createMenuStructure() {
		menuBar_ = new MenuBar();
		Environment environment = ContextProvider.getBean(Environment.class);
		String menubarStyle = environment.getProperty("application.style.main.menu.bar");
		if (StringUtils.isNotBlank(menubarStyle)) {
			menuBar_.setPrimaryStyleName(menubarStyle);
		}
		menuBar_.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		menuBar_.addStyleName(ValoTheme.MENUBAR_SMALL);
		return menuBar_;
	}

	// private MenuBar createRoleMenuStructure() {
	// menuBarRole = new MenuBar();
	// menuBarRole.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
	// menuBarRole.addStyleName(ValoTheme.MENUBAR_SMALL);
	// return menuBarRole;
	// }

	private MenuBar createUserMenuStructure() {
		userInfoMenuBar = new MenuBar();
		userInfoMenuBar.addStyleName(ValoTheme.MENUBAR_SMALL);
		userInfoMenuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		// userMenuBarRole.addItem("User menu here");

		userMenuItem = userInfoMenuBar.addItem(WordUtils.capitalize("username"),
				new ThemeResource("../cxode/img/profile-pic-300px.jpg"), null);
		userMenuItem.setStyleName(Style.MAIN_VIEW_USER_MENU);

		return userInfoMenuBar;
	}

	private void buildTabSheet() throws FrameworkException {
		tabSheet = new TabSheet();
		tabSheet.setVisible(false);
		tabSheet.setSizeFull();
		// tabSheet.setResponsive(true);
		tabSheet.addStyleName(ValoTheme.TABSHEET_COMPACT_TABBAR);
		// tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		// addComponent(new SimpleSolutionsMenu(tabSheet));
		// addComponent(tabSheet);
		// setExpandRatio(tabSheet, 1.0f);

		tabSheet.setCloseHandler(new CloseHandler() {

			private static final long serialVersionUID = -2721595516583386738L;

			public void closeTab(TabSheet tabsheet, Component tabContent) {
				Notification notification = new Notification(PropertyResolver.getPropertyValueByLocale(
						"system.tab.closed", UI.getCurrent().getLocale(), new Object[] { tabContent.getCaption() }));
				notification.show(Page.getCurrent());
				tabsheet.removeComponent(tabContent);
				if (tabContent instanceof AbstractBaseView) {
					AbstractBaseView abstractBaseView = (AbstractBaseView) tabContent;
					sessionHolder.removeGeneratedMenu(abstractBaseView.getUuid());
				}
				if (tabsheet.getComponentCount() > 0) {
					tabsheet.setVisible(true);
				} else {
					tabsheet.setVisible(false);
				}
			}

			@Override
			public void onTabClose(TabSheet tabsheet, Component tabContent) {
				if (tabContent instanceof AbstractBaseView) {
					Boolean viewContentUpdated = ((AbstractBaseView) tabContent).getViewContentUpdated();
					if (viewContentUpdated) {
						ConfirmWindow confirmWindow = new ConfirmWindow(SystemProperty.UNSAVED_CHANGES_HEADER,
								SystemProperty.UNSAVED_CHANGES_CONFIRMATION_REQUEST, SystemProperty.CONFIRM,
								SystemProperty.CANCEL);
						confirmWindow.execute(new ConfirmationHandler() {

							@Override
							public void handlePositive() {
								closeTab(tabsheet, tabContent);
							}

							@Override
							public void handleNegative() {
								// Does nothing
							}
						});
					} else {
						closeTab(tabsheet, tabContent);
					}
				}
			}
		});
	}

}
