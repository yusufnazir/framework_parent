package software.simple.solutions.framework.core.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
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

import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.ConfirmWindow;
import software.simple.solutions.framework.core.components.ConfirmWindow.ConfirmationHandler;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
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
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.service.ILanguageService;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.service.IRoleService;
import software.simple.solutions.framework.core.service.IViewService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.MenuIndexComparator;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SimpleMenuIndexComparator;
import software.simple.solutions.framework.core.valueobjects.LanguageVO;
import software.simple.solutions.framework.core.web.view.ChangePasswordView;

public class TopMenuLayoutView extends VerticalLayout {

	private static final long serialVersionUID = -6481063751855663777L;

	private static final Logger logger = LogManager.getLogger(TopMenuLayoutView.class);

	private TabSheet tabSheet;
	private MenuBar menuBar;
	private MenuBar userInfoMenuBar;
	private SessionHolder sessionHolder;

	private List<Menu> menus;

	private IMenuService menuService;
	private IViewService viewService;
	private IRoleService roleService;
	private ILanguageService languageService;

	private MenuItem userMenuItem;

	private HorizontalLayout headerLayout;
	private UI ui;

	public TopMenuLayoutView() throws FrameworkException {
		super();
		ui = UI.getCurrent();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		menuService = ContextProvider.getBean(IMenuService.class);
		viewService = ContextProvider.getBean(IViewService.class);
		roleService = ContextProvider.getBean(IRoleService.class);
		languageService = ContextProvider.getBean(ILanguageService.class);
		SimpleSolutionsEventBus.register(this);

		setMargin(true);
		setSpacing(true);
		buildLayout();

		setUpMenus();
	}

	private void setUpMenus() throws FrameworkException {

		createRoleMenuItems();

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
						menuBar.setVisible(true);
						tabSheet.removeAllComponents();
					}
				});

		List<UserRole> userRoles = roleService.findRolesByUserId(sessionHolder.getApplicationUser().getId());
		/*
		 * Add the roles as childeren of the parent, which is the caption of the
		 * role menu.
		 */
		if (userRoles != null) {
			for (final UserRole userRole : userRoles) {
				/*
				 * Add the roles
				 */
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
							 * update the parent description to show selected
							 * role
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
				if (userRoles.get(0).equals(userRole)) {
					roleItem.getCommand().menuSelected(roleItem);
				}
			}
		}
	}

	private void createMenu(Role role) throws FrameworkException {
		menuBar.setVisible(true);
		sessionHolder.setSelectedRole(role);
		menuBar.removeItems();
		menus = menuService.findAuthorizedMenus(role.getId());
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
			MenuBar.MenuItem item = menuBar.addItem(menuName, command);
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
				handleMenuSelected(viewId, menu);
			}
		};
	}

	@Subscribe
	public void handleLookupMenuSelected(final LookupMenuSelectedEvent lookupMenuSelectedEvent) {
		Tab tab = handleMenuSelected(lookupMenuSelectedEvent.getMenu().getView().getId(),
				lookupMenuSelectedEvent.getMenu());
		tab.setIcon(FontAwesome.ADJUST);
	}

	public Tab handleMenuSelected(Long viewId, Menu menu) {
		try {
			if (!tabSheet.isVisible()) {
				tabSheet.setVisible(true);
			}
			View view = viewService.getById(View.class, viewId);
			AbstractBaseView abstractBaseView = (AbstractBaseView) ViewUtil.initView(view.getViewClassName());
			if (abstractBaseView == null) {
				Notification notification = new Notification(PropertyResolver.getPropertyValueByLocale(
						"core.menu.no.view.found", UI.getCurrent().getLocale()), Type.ERROR_MESSAGE);
				notification.setDescription(PropertyResolver.getPropertyValueByLocale("core.menu.no.view.found.message",
						UI.getCurrent().getLocale()));
				notification.show(UI.getCurrent().getPage());
				return null;
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
		PagingResult<Language> languageResult = languageService.findBySearch(vo, new PagingSetting());

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
								createMenuStructure();
							}
						});
			}
		}
		return userMenuItem;
	}

	private MenuItem createChangePasswordItem() {
		return userMenuItem.addItem(PropertyResolver.getPropertyValueByLocale(ApplicationUserProperty.CHANGE_PASSWORD),
				FontAwesome.KEY, new Command() {
					private static final long serialVersionUID = -3515364978684559560L;

					@Override
					public void menuSelected(MenuItem selectedItem) {
						try {
							ChangePasswordView changePasswordView = new ChangePasswordView();
							changePasswordView.setMandatory(false);
							tabSheet.addComponent(changePasswordView);
							tabSheet.getTab(changePasswordView).setCaption(
									PropertyResolver.getPropertyValueByLocale(ApplicationUserProperty.CHANGE_PASSWORD));
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
						getUI().getSession().close();
						getUI().getPage().setLocation("");
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

		Image logoImageHolder = createLogoImageHolder();
		headerLayout.addComponent(logoImageHolder);

		menuBar = createMenuStructure();
		headerLayout.addComponent(menuBar);
		// headerLayout.setExpandRatio(menuBar, 1);

		// menuBarRole = createRoleMenuStructure();
		// headerLayout.addComponent(menuBarRole);
		// headerLayout.setExpandRatio(menuBarRole, 1);

		userInfoMenuBar = createUserMenuStructure();
		headerLayout.addComponent(userInfoMenuBar);
		headerLayout.setExpandRatio(userInfoMenuBar, 1);
		headerLayout.setComponentAlignment(userInfoMenuBar, Alignment.MIDDLE_RIGHT);

		return headerLayout;
	}

	private Image createLogoImageHolder() {
		Image image = new Image();
		image.setWidth("200px");
		image.setHeight("40px");
		image.setSource(new ThemeResource("../cxode/img/your-logo-here.png"));
		image.addStyleName(Style.MAIN_VIEW_APPLICATION_LOGO);

		new Thread(new Runnable() {

			@Override
			public void run() {
				Resource resource = new StreamResource(new StreamSource() {

					private static final long serialVersionUID = 7941811283553249939L;

					@Override
					public InputStream getStream() {
						try {
							IConfigurationService configurationService = ContextProvider
									.getBean(IConfigurationService.class);
							Configuration configuration = configurationService
									.getByCode(ConfigurationProperty.APPLICATION_LOGO);
							if (configuration != null) {
								IFileService fileService = ContextProvider.getBean(IFileService.class);
								EntityFile entityFile = fileService.findFileByEntityAndType(
										configuration.getId().toString(), Configuration.class.getName(),
										ConfigurationProperty.APPLICATION_LOGO);
								if (entityFile.getFileObject() != null) {
									return new ByteArrayInputStream(entityFile.getFileObject());
								}
							}
						} catch (FrameworkException e) {
							logger.error(e.getMessage(), e);
						}
						return null;
					}
				}, UUID.randomUUID().toString());
				ui.access(new Runnable() {

					@Override
					public void run() {
						image.setSource(resource);
					}
				});
			}
		}).start();

		return image;
	}

	private MenuBar createMenuStructure() {
		menuBar = new MenuBar();
		menuBar.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		menuBar.addStyleName(ValoTheme.MENUBAR_SMALL);
		return menuBar;
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

							@SuppressWarnings({ "unchecked", "rawtypes" })
							@Override
							public void handlePositive() throws FrameworkException {
								closeTab(tabsheet, tabContent);
							}

							@Override
							public void handleNegative() {
								return;
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
