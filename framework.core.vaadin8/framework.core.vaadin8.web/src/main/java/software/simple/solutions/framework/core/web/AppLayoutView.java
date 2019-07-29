package software.simple.solutions.framework.core.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.appreciated.app.layout.AppLayout;
import com.github.appreciated.app.layout.behaviour.AppLayoutComponent;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayoutConfiguration.NavigatorProducer;
import com.github.appreciated.app.layout.builder.NavigatorAppLayoutBuilder;
import com.github.appreciated.app.layout.builder.Section;
import com.github.appreciated.app.layout.builder.design.AppLayoutDesign;
import com.github.appreciated.app.layout.builder.elements.NavigatorNavigationElement;
import com.github.appreciated.app.layout.builder.elements.builders.SubmenuBuilder;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.github.appreciated.app.layout.component.button.AppBarNotificationButton;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
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
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.ConfirmWindow;
import software.simple.solutions.framework.core.components.ConfirmWindow.ConfirmationHandler;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.FileServiceFacade;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.view.ChangePasswordView;
import software.simple.solutions.framework.core.web.view.PersonView;

public class AppLayoutView extends VerticalLayout {

	private static final long serialVersionUID = -7957430888366287509L;

	private static final Logger logger = LogManager.getLogger(TopMenuLayoutView.class);

	private DefaultNotificationHolder notifications = new DefaultNotificationHolder();
	private Image applicationLogoImage;
	private SessionHolder sessionHolder;
	private List<Menu> menus;
	private UI ui;
	private TabSheet tabSheet;
	private Menu homeMenu;
	private boolean profilePopUpOpen = false;

	private CssLayout profilePopUpLayout;

	public AppLayoutView() {
		setMargin(false);
		ui = UI.getCurrent();
		tabSheet = new TabSheet();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		try {
			setUpTabSheet();
			setDrawerVariant(Behaviour.LEFT_OVERLAY);
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void setUpTabSheet() {
		tabSheet.setVisible(false);
		tabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {

			private static final long serialVersionUID = -7530334462975633736L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				if (tabSheet.getComponentCount() > 0) {
					tabSheet.setVisible(true);
				} else {
					tabSheet.setVisible(false);
				}
				if (tabSheet.getComponentCount() == 1) {
					tabSheet.setTabsVisible(false);
				} else {
					tabSheet.setTabsVisible(true);
				}
			}
		});
		tabSheet.setCloseHandler(new CloseHandler() {

			private static final long serialVersionUID = -156199322439548182L;

			public void closeTab(TabSheet tabsheet, Component tabContent) {
				Notification notification = new Notification(PropertyResolver.getPropertyValueByLocale(
						"system.tab.closed", UI.getCurrent().getLocale(), new Object[] { tabContent.getCaption() }));
				notification.show(Page.getCurrent());
				tabsheet.removeComponent(tabContent);
				
				if (tabSheet.getComponentCount() > 0) {
					tabSheet.setVisible(true);
				} else {
					tabSheet.setVisible(false);
				}
				if (tabSheet.getComponentCount() == 1) {
					tabSheet.setTabsVisible(false);
				} else {
					tabSheet.setTabsVisible(true);
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
								return;
							}
						});
					} else {
						closeTab(tabsheet, tabContent);
					}
				} else {
					closeTab(tabsheet, tabContent);
				}
			}
		});
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

		CssLayout profileLayout = createProfileLayout();
		NavigatorAppLayoutBuilder navigatorAppLayoutBuilder = AppLayout.getDefaultBuilder(variant).withTitle(title)
				.addToAppBar(new AppBarNotificationButton(notifications, true)).addToAppBar(profileLayout)
				.withNavigator(new NavigatorProducer() {

					@Override
					public Navigator apply(Panel t) {
						t.setContent(tabSheet);
						tabSheet.setSizeFull();
						ViewDisplay viewDisplay = new ViewDisplay() {

							private static final long serialVersionUID = -4909425631082680611L;

							@Override
							public void showView(View view) {
//								System.out.println(view);
//								tabSheet.addComponent(view.getViewComponent());
							}
						};
						return new Navigator(ui, viewDisplay);
					}
				});
		navigatorAppLayoutBuilder.withDesign(AppLayoutDesign.MATERIAL);

		applicationLogoImage = new Image();
		updateApplicationLogo();
		navigatorAppLayoutBuilder.add(applicationLogoImage, Section.HEADER);

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
				Navigator navigator = UI.getCurrent().getNavigator();
				View currentView = navigator.getCurrentView();
				try {
					String state = navigator.getState();
					Long menuId = NumberUtil.getLong(state);

					MenuServiceFacade menuServiceFacade = MenuServiceFacade.get(UI.getCurrent());
					Menu menu = menuServiceFacade.getById(Menu.class, menuId);
					software.simple.solutions.framework.core.entities.View view = menu.getView();

					AbstractBaseView abstractBaseView = (AbstractBaseView) currentView;
					if (abstractBaseView == null) {
						Notification notification = new Notification(PropertyResolver.getPropertyValueByLocale(
								"core.menu.no.view.found", UI.getCurrent().getLocale()), Type.ERROR_MESSAGE);
						notification.setDescription(PropertyResolver.getPropertyValueByLocale(
								"core.menu.no.view.found.message", UI.getCurrent().getLocale()));
						notification.show(UI.getCurrent().getPage());
						return;
					}
					List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
							.getPrivilegesByViewIdAndRoleId(view.getId(), sessionHolder.getSelectedRole().getId());
					abstractBaseView.getViewDetail().setPrivileges(privileges);

					abstractBaseView.getViewDetail().setMenu(menu);
					abstractBaseView.getViewDetail().setView(view);
					abstractBaseView.executeBuild();
					abstractBaseView.executeSearch();
					abstractBaseView.setMargin(true);

					tabSheet.addTab(abstractBaseView);
					String menuName = menu.getKey() == null ? menu.getName()
							: PropertyResolver.getPropertyValueByLocale(menu.getKey(), UI.getCurrent().getLocale());
					tabSheet.getTab(abstractBaseView).setCaption(menuName);
					tabSheet.getTab(abstractBaseView).setClosable(true);
					tabSheet.setSelectedTab(abstractBaseView);
					tabSheet.setVisible(true);

				} catch (SecurityException | IllegalArgumentException | FrameworkException e) {
					logger.error(e.getMessage(), e);
				}
			}
		});

		MenuServiceFacade menuServiceFacade = MenuServiceFacade.get(UI.getCurrent());

		Configuration configurationHomeView = configurationService
				.getByCode(ConfigurationProperty.APPLICATION_HOME_VIEW);
		if (configurationHomeView != null) {
			Long homeViewMenuId = configurationHomeView.getLong();
			if (homeViewMenuId != null) {
				homeMenu = menuServiceFacade.getById(Menu.class, homeViewMenuId);
				if (homeMenu != null) {
					Integer codePoint = homeMenu.getIcon();
					FontAwesome fontAwesome = null;
					if (codePoint != null) {
						fontAwesome = FontAwesome.fromCodepoint(codePoint);
					}
					String menuName = homeMenu.getKey() == null ? homeMenu.getName()
							: PropertyResolver.getPropertyValueByLocale(homeMenu.getKey(), UI.getCurrent().getLocale());
					navigatorAppLayoutBuilder.addClickable(menuName, fontAwesome, new Button.ClickListener() {

						private static final long serialVersionUID = -8337443024708870791L;

						@Override
						public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
							// TODO Auto-generated method stub

						}
					});
				}
			}
		}

		menus = menuServiceFacade.findAuthorizedMenusByType(sessionHolder.getSelectedRole().getId(),
				Arrays.asList(MenuType.HEAD_MENU, MenuType.CHILD));

		LinkedHashMap<String, String> menuItems = new LinkedHashMap<>();
		if (menus != null) {
			for (Menu menu : menus) {
				if (menu.getParentMenu() == null) {
					if (menu.getView() == null) {
						menuItems.put(menu.getId().toString(), menu.getName());
						SubmenuBuilder submenuBuilder = SubmenuBuilder.get(menu.getName());
						submenuBuilder = createSubMenus(menu, submenuBuilder);
						navigatorAppLayoutBuilder.add(submenuBuilder.build());
					} else {
						try {
							Integer codePoint = menu.getIcon();
							FontAwesome fontAwesome = null;
							if (codePoint != null) {
								fontAwesome = FontAwesome.fromCodepoint(codePoint);
							}
							Class<? extends View> viewClass = (Class<? extends View>) Class
									.forName(menu.getView().getViewClassName());
							String menuName = menu.getKey() == null ? menu.getName()
									: PropertyResolver.getPropertyValueByLocale(menu.getKey(),
											UI.getCurrent().getLocale());
							NavigatorNavigationElement navigationElement = new NavigatorNavigationElement(menuName,
									menu.getId().toString(), fontAwesome, null, viewClass);
							navigatorAppLayoutBuilder.add(navigationElement);
						} catch (ClassNotFoundException e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
		}

		navigatorAppLayoutBuilder.withDefaultNavigationView(PersonView.class);
		AppLayoutComponent drawer = navigatorAppLayoutBuilder.build();
		drawer.addStyleName("left");
		addComponent(drawer);
		try {
			initializeHomeView();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initializeHomeView() throws FrameworkException, ClassNotFoundException {
		if (homeMenu != null) {
			Class<? extends View> viewClass = (Class<? extends View>) Class
					.forName(homeMenu.getView().getViewClassName());
			AbstractBaseView abstractBaseView = (AbstractBaseView) ViewUtil.initView(viewClass);
			if (abstractBaseView == null) {
				Notification notification = new Notification(PropertyResolver.getPropertyValueByLocale(
						"core.menu.no.view.found", UI.getCurrent().getLocale()), Type.ERROR_MESSAGE);
				notification.setDescription(PropertyResolver.getPropertyValueByLocale("core.menu.no.view.found.message",
						UI.getCurrent().getLocale()));
				notification.show(UI.getCurrent().getPage());
				return;
			}
			List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
					.getPrivilegesByViewIdAndRoleId(homeMenu.getView().getId(),
							sessionHolder.getSelectedRole().getId());
			abstractBaseView.getViewDetail().setPrivileges(privileges);

			abstractBaseView.getViewDetail().setMenu(homeMenu);
			abstractBaseView.getViewDetail().setView(homeMenu.getView());
			abstractBaseView.executeBuild();
			abstractBaseView.executeSearch();
			abstractBaseView.setMargin(true);

			String menuName = homeMenu.getKey() == null ? homeMenu.getName()
					: PropertyResolver.getPropertyValueByLocale(homeMenu.getKey(), UI.getCurrent().getLocale());
			tabSheet.addComponent(abstractBaseView);
			tabSheet.getTab(abstractBaseView).setCaption(menuName);
			tabSheet.getTab(abstractBaseView).setClosable(false);
			tabSheet.setSelectedTab(abstractBaseView);
			tabSheet.setVisible(true);
		}
	}

	private SubmenuBuilder createSubMenus(Menu parent, SubmenuBuilder submenuBuilder) {
		return getChildrens(parent, submenuBuilder);
	}

	private SubmenuBuilder getChildrens(Menu parent, SubmenuBuilder submenuBuilder) {
		for (Menu menu : menus) {
			if (menu.getParentMenu() != null && menu.getParentMenu().getId().compareTo(parent.getId()) == 0) {
				if (menu.getView() == null) {
					SubmenuBuilder builder = SubmenuBuilder.get(menu.getName());
					builder = getChildrens(menu, builder);
					submenuBuilder.add(builder.build());
				} else {
					try {
						Integer codePoint = menu.getIcon();
						FontAwesome fontAwesome = null;
						if (codePoint != null) {
							fontAwesome = FontAwesome.fromCodepoint(codePoint);
						}
						Class<? extends View> viewClass = (Class<? extends View>) Class
								.forName(menu.getView().getViewClassName());
						NavigatorNavigationElement navigationElement = new NavigatorNavigationElement(menu.getName(),
								menu.getId().toString(), fontAwesome, null, viewClass);
						submenuBuilder.add(navigationElement);
					} catch (ClassNotFoundException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		return submenuBuilder;
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

		applicationLogoImage.setWidth("100%");
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

	private void toggleProfilePopUpLayout() {
		if (profilePopUpOpen) {
			profilePopUpLayout.removeStyleName("open");
		} else {
			profilePopUpLayout.addStyleName("open");
		}
		profilePopUpOpen = !profilePopUpOpen;
	}

	private CssLayout createProfileLayout() {
		CssLayout menuLayout = new CssLayout();
		menuLayout.addStyleName("user-menu__inner");

		CssLayout profileImageLayout = new CssLayout();
		profileImageLayout.addStyleName("user-menu__profile");

		menuLayout.addComponent(profileImageLayout);

		Image profileImage = new Image();
		profileImage.setSource(new ThemeResource("../cxode/img/profile-pic-300px.jpg"));
		profileImage.addStyleName("appbar-profile-image");
		profileImageLayout.addComponent(profileImage);

		profilePopUpLayout = new CssLayout();
		profilePopUpLayout.addStyleName("user-menu__pop-out");
		menuLayout.addComponent(profilePopUpLayout);

		profileImage.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1803844919818554795L;

			@Override
			public void click(ClickEvent event) {
				if (event.getButton().compareTo(MouseButton.LEFT) == 0) {
					toggleProfilePopUpLayout();
				}
			}
		});

		CssLayout userLayout = new CssLayout();
		userLayout.addStyleName("user-menu__user");
		profilePopUpLayout.addComponent(userLayout);

		Label userNameLayout = new Label();
		userNameLayout.addStyleName("user-menu__name");
		userNameLayout.setValue("Username");
		userLayout.addComponent(userNameLayout);

		Button editUserProfileBtn = new Button();
		editUserProfileBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		editUserProfileBtn.addStyleName("user-menu__edit-profile");
		editUserProfileBtn.setCaption(
				PropertyResolver.getPropertyValueByLocale("core.menu.edit.profile", UI.getCurrent().getLocale()));
		userLayout.addComponent(editUserProfileBtn);
		editUserProfileBtn.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -2502952536044439873L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				createChangePasswordItem();
			}
		});

		Button logoutBtn = new Button();
		logoutBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		logoutBtn.addStyleName(ValoTheme.BUTTON_LINK);
		logoutBtn.addStyleName("user-menu__link-logout");
		logoutBtn.setIcon(FontAwesome.UNLOCK);
		logoutBtn
				.setCaption(PropertyResolver.getPropertyValueByLocale("core.menu.logout", UI.getCurrent().getLocale()));
		userLayout.addComponent(logoutBtn);
		logoutBtn.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = -2502952536044439873L;

			@Override
			public void buttonClick(com.vaadin.ui.Button.ClickEvent event) {
				VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
				HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest).getHttpServletRequest();
				String requestUrl = httpServletRequest.getServletContext().getContextPath();
				SecurityContextHolder.clearContext();
				getUI().getSession().close();
				getUI().getPage().setLocation(requestUrl + "/app");
			}
		});

		return menuLayout;
	}

	private void createChangePasswordItem() {
		try {
			ChangePasswordView changePasswordView = new ChangePasswordView();
			changePasswordView.setMandatory(false);
			tabSheet.addComponent(changePasswordView);
			tabSheet.getTab(changePasswordView)
					.setCaption(PropertyResolver.getPropertyValueByLocale(ApplicationUserProperty.CHANGE_PASSWORD));
			tabSheet.getTab(changePasswordView).setClosable(true);
			tabSheet.setSelectedTab(changePasswordView);
			if (!tabSheet.isVisible()) {
				tabSheet.setVisible(true);
			}
		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}
	}

}
