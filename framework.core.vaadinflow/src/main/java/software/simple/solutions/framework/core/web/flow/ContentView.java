package software.simple.solutions.framework.core.web.flow;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.MenuType;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PopUpMode;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.web.AbstractBaseView;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.DetailsWindow;
import software.simple.solutions.framework.core.web.EntitySelect;
import software.simple.solutions.framework.core.web.SimpleSolutionsMenuItem;
import software.simple.solutions.framework.core.web.ViewUtil;
import software.simple.solutions.framework.core.web.components.CButton;
import software.simple.solutions.framework.core.web.lookup.LookUpHolder;

public class ContentView extends VerticalLayout implements BeforeEnterObserver {

	private final class MenuItemClick implements ComponentEventListener<ClickEvent<MenuItem>> {
		private static final long serialVersionUID = -105533134396024106L;

		private Class<? extends Component> forName;
		private Route route;

		public MenuItemClick(Class<? extends Component> forName, Route route) {
			this.forName = forName;
			this.route = route;
		}

		@Override
		public void onComponentEvent(ClickEvent<MenuItem> event) {
			if (forName != null) {
				resetTabs();
				if (route != null) {
					String value = route.value();
					UI.getCurrent().navigate(value);
				}
			}
		}
	}

	private static final long serialVersionUID = -7294083435850906760L;

	private static final Logger logger = LogManager.getLogger(ContentView.class);

	private HorizontalLayout tabBar;
	private Tabs subTabSheet;
	private VerticalLayout contentLayout;
	private Map<Tab, SimpleSolutionsMenuItem> subMenusTabMap;
	private Map<Tab, SimpleSolutionsMenuItem> lookUpMenusTabMap;
	private Map<Tab, AbstractBaseView> createdSubMenusTabs;
	private SessionHolder sessionHolder;
	private BehaviorSubject<Menu> menuSelectedObserver;
	private AbstractBaseView abstractBaseView;
	private Object selectedEntity;
	private BehaviorSubject<LookUpHolder> lookUpFieldLinkObserver;
	private BehaviorSubject<LookUpHolder> lookUpFieldSelectObserver;
	private Tab parentViewTab;
	private Tab previousSelectedTab;
	private HorizontalLayout mainBar;
	private MenuBar menuBar;
	private List<Menu> menus;

	private MenuBar userMenuBar;

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub

	}

	public ContentView() {
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		menuSelectedObserver = BehaviorSubject.create();
		lookUpFieldLinkObserver = BehaviorSubject.create();
		lookUpFieldSelectObserver = BehaviorSubject.create();
		subMenusTabMap = new HashMap<Tab, SimpleSolutionsMenuItem>();
		createdSubMenusTabs = new HashMap<Tab, AbstractBaseView>();
		lookUpMenusTabMap = new HashMap<Tab, SimpleSolutionsMenuItem>();

		setSizeFull();
		initialize();
	}

	public void setContent(HasElement content) {
		Optional<Component> optional = content.getElement().getComponent();
		if (optional.isPresent()) {
			Component component = optional.get();
			contentLayout.add(component);
			createdSubMenusTabs.clear();
			subTabSheet.removeAll();
			if (component instanceof AbstractBaseView) {
				abstractBaseView = (AbstractBaseView) component;
				parentViewTab = new Tab(PropertyResolver.getPropertyValueByLocale(ReferenceKey.MENU,
						abstractBaseView.getViewDetail().getMenu().getId(), UI.getCurrent().getLocale(),
						abstractBaseView.getViewDetail().getMenu().getName()));
				createdSubMenusTabs.put(parentViewTab, abstractBaseView);
				subTabSheet.add(parentViewTab);

				List<Menu> subMenus = abstractBaseView.getViewDetail().getSubMenus();
				if (subMenus != null) {
					for (Menu menu : subMenus) {
						Tab subMenuTab = new Tab(PropertyResolver.getPropertyValueByLocale(ReferenceKey.MENU,
								menu.getId(), UI.getCurrent().getLocale(), menu.getName()));
						subMenuTab.addClassName("my-custom-sub-tab");
						subMenuTab.setVisible(false);
						subTabSheet.add(subMenuTab);
						SimpleSolutionsMenuItem simpleSolutionsMenuItem = new SimpleSolutionsMenuItem(menu);
						subMenusTabMap.put(subMenuTab, simpleSolutionsMenuItem);
					}
				}

				((AbstractBaseView) component).getEntitySelectedObserver().subscribe(new Consumer<EntitySelect>() {

					@Override
					public void accept(EntitySelect entitySelect) throws Exception {
						handleSelectedEntity(entitySelect);
					}
				});
			}
		}
	}

	private void handleSelectedEntity(EntitySelect entitySelect) {
		selectedEntity = entitySelect.getEntity();
		if (entitySelect.getEntity() == null) {
			subMenusTabMap.keySet().stream().forEach(p -> p.setVisible(false));
			Set<Tab> keySet = lookUpMenusTabMap.keySet();
			for (Tab tab : keySet) {
				subMenusTabMap.remove(tab);
				subTabSheet.remove(tab);
			}
			lookUpMenusTabMap.clear();
			subMenusTabMap.keySet().stream().forEach(p -> createdSubMenusTabs.remove(p));
		} else {
			subMenusTabMap.keySet().stream().forEach(p -> p.setVisible(true));
		}
	}

	public void initialize() {
		mainBar = new HorizontalLayout();
		mainBar.setWidth("100%");
		mainBar.getStyle().set("padding-top", "5px");
		mainBar.getStyle().set("padding-bottom", "5px");
		mainBar.getStyle().set("border-bottom", "2px solid var(--lumo-primary-color)");
		mainBar.getStyle().set("background", "var(--paper-light-green-100)");
		add(mainBar);

		userMenuBar = new MenuBar();
		userMenuBar.setThemeName("user-menubar-theme");
		mainBar.add(userMenuBar);

		menuBar = new MenuBar();
		mainBar.add(menuBar);

		BehaviorSubject<Boolean> loginSuccessfullObserver = BehaviorSubject.create();
		sessionHolder.addReferenceKey(ReferenceKey.LOGIN_SUCCESSFULL, loginSuccessfullObserver);
		loginSuccessfullObserver.subscribe(new Consumer<Boolean>() {

			@Override
			public void accept(Boolean t) throws Exception {
				setUpMenu();
				setUpUserMenu();
			}

			private void setUpMenu() {
				sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
				MenuServiceFacade menuServiceFacade = MenuServiceFacade.get(UI.getCurrent());
				try {
					menus = menuServiceFacade.findAuthorizedMenusByType(sessionHolder.getSelectedRole().getId(),
							Arrays.asList(MenuType.HEAD_MENU, MenuType.CHILD));
					if (menus != null) {
						Collections.sort(menus, Comparator.comparing(Menu::getIndex));
						for (Menu menu : menus) {
							if (menu.getType().compareTo(MenuType.HEAD_MENU) == 0) {
								MenuItem menuItem = menuBar.addItem(PropertyResolver.getPropertyValueByLocale(
										ReferenceKey.MENU, menu.getId().toString(), UI.getCurrent().getLocale(), null,
										menu.getName()));
								getChildrens(menuItem, menu);
							}
						}
					}
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
				}
			}

			private void setUpUserMenu() {
				Image imageField = new Image("img/profile-pic-300px.jpg", "profile-image");
				imageField.getStyle().set("border-radius", "50%");
				imageField.getStyle().set("height", "var(--lumo-size-s)");
				imageField.getStyle().set("border", "2px solid var(--lumo-primary-color)");
				imageField.addClassName("applayout-profile-image");
				MenuItem userProfileMenu = userMenuBar.addItem(imageField);
				userProfileMenu.getElement().getStyle().set("background", "none");

				Button logoutBtn = new Button(PropertyResolver.getPropertyValueByLocale(SystemProperty.BUTTON_LOGOUT,
						UI.getCurrent().getLocale()));
				userProfileMenu.getSubMenu().add(logoutBtn);
				logoutBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

					private static final long serialVersionUID = -5897232278349546260L;

					@Override
					public void onComponentEvent(ClickEvent<Button> event) {
						// UI.getCurrent().getPage().executeJs("window.location.href=''");
						// UI.getCurrent().getPage().reload();
						// VaadinSession.getCurrent().close();

						VaadinSession.getCurrent().getSession().invalidate();
						UI.getCurrent().getPage().executeJs("window.location.href=''");
					}
				});
			}
		});

		tabBar = new HorizontalLayout();
		CButton returnToSearchBtn = new CButton();
		returnToSearchBtn.getStyle().set("border", "1px solid");
		returnToSearchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		returnToSearchBtn.setCaptionByKey(SystemProperty.SYSTEM_BACK_TO_RESULTS);
		returnToSearchBtn.setIcon(VaadinIcon.LIST.create());
		returnToSearchBtn.setVisible(false);
		tabBar.add(returnToSearchBtn);
		returnToSearchBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -687791699840661882L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				// handleBackFromForm();
			}
		});

		subTabSheet = new Tabs();
		subTabSheet.getStyle().set("box-shadow", "none");
		tabBar.add(subTabSheet);
		add(tabBar);

		subTabSheet.addSelectedChangeListener(new ComponentEventListener<Tabs.SelectedChangeEvent>() {

			private static final long serialVersionUID = -7201114375011146454L;

			@Override
			public void onComponentEvent(SelectedChangeEvent event) {
				previousSelectedTab = event.getPreviousTab();
				Tab selectedTab = event.getSelectedTab();
				if (selectedTab != null) {
					contentLayout.getChildren().forEach(p -> p.setVisible(false));
					if (createdSubMenusTabs.containsKey(selectedTab)) {
						AbstractBaseView abstractBaseView = createdSubMenusTabs.get(selectedTab);
						Optional<Component> optional = contentLayout.getChildren().filter(p -> ((AbstractBaseView) p)
								.getViewDetail().getUuid().equalsIgnoreCase(abstractBaseView.getViewDetail().getUuid()))
								.findFirst();
						if (optional.isPresent()) {
							optional.get().setVisible(true);
						}
					} else {
						SimpleSolutionsMenuItem simpleSolutionsMenuItem = subMenusTabMap.get(event.getSelectedTab());
						simpleSolutionsMenuItem.setReferenceKeys(abstractBaseView.getReferenceKeys());
						simpleSolutionsMenuItem.setParentEntity(selectedEntity);
						simpleSolutionsMenuItem.setSubTab(true);
						try {
							AbstractBaseView view = ViewUtil.initView(simpleSolutionsMenuItem, PopUpMode.NONE,
									sessionHolder.getSelectedRole().getId(),
									sessionHolder.getApplicationUser().getId());
							view.executeBuild();
							view.executeSearch();
							contentLayout.add(view);
							createdSubMenusTabs.put(selectedTab, view);
						} catch (FrameworkException e) {
							DetailsWindow.build(e);
							logger.error(e.getMessage(), e);
						}
					}
				}
			}
		});

		contentLayout = new VerticalLayout();
		contentLayout.setSizeFull();
		add(contentLayout);

		sessionHolder.addReferenceKey(ReferenceKey.MENU_SELECTED_OBSERVEABLE, menuSelectedObserver);
		menuSelectedObserver.subscribe(new Consumer<Menu>() {

			@Override
			public void accept(Menu menu) throws Exception {
				View view = menu.getView();
				Class<? extends Component> forName = null;
				Route route = null;
				if (view != null) {
					String viewClassName = view.getViewClassName();
					try {
						forName = (Class<? extends Component>) Class.forName(viewClassName);
						route = forName.getAnnotation(Route.class);
						if (route != null) {
							String value = route.value();
							sessionHolder.addRouteMenu(value, menu.getId());
							resetTabs();
							UI.getCurrent().navigate(value);
						}
					} catch (ClassNotFoundException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		});

		setUpFieldLinkObservable();

		setUpFieldSelectObservable();
	}

	private void getChildrens(MenuItem parentMenuItem, Menu parent) {
		List<Menu> list = menus.stream()
				.filter(p -> (p.getParentMenu() != null && p.getParentMenu().getId().compareTo(parent.getId()) == 0))
				.collect(Collectors.toList());
		if (list != null) {
			for (Menu menu : list) {
				View view = menu.getView();
				Class<? extends Component> forName = null;
				Route route = null;
				if (view != null) {
					String viewClassName = view.getViewClassName();
					try {
						forName = (Class<? extends Component>) Class.forName(viewClassName);
						route = forName.getAnnotation(Route.class);
						if (route != null) {
							String value = route.value();
							sessionHolder.addRouteMenu(value, menu.getId());
						}
					} catch (ClassNotFoundException e) {
						logger.error(e.getMessage(), e);
					}
				}

				MenuItem menuItem = parentMenuItem.getSubMenu().addItem(PropertyResolver.getPropertyValueByLocale(
						ReferenceKey.MENU, menu.getId().toString(), UI.getCurrent().getLocale(), null, menu.getName()));
				getChildrens(menuItem, menu);
				menuItem.addClickListener(new MenuItemClick(forName, route));
			}
		}
	}

	private void setUpFieldSelectObservable() {
		sessionHolder.addReferenceKey(ReferenceKey.LOOKUP_FIELD_SELECT_OBSERVEABLE, lookUpFieldSelectObserver);
		lookUpFieldSelectObserver.subscribe(new Consumer<LookUpHolder>() {

			@Override
			public void accept(LookUpHolder lookUpHolder) throws Exception {
				IMenuService menuService = ContextProvider.getBean(IMenuService.class);
				Menu menu = menuService.getLookUpByViewClass(sessionHolder.getSelectedRole().getId(),
						lookUpHolder.getViewClass().getName());
				SimpleSolutionsMenuItem simpleSolutionsMenuItem = new SimpleSolutionsMenuItem(menu);
				simpleSolutionsMenuItem.setParentEntity(lookUpHolder.getParentEntity());
				AbstractBaseView view = ViewUtil.initView(simpleSolutionsMenuItem, PopUpMode.TAB,
						sessionHolder.getSelectedRole().getId(), sessionHolder.getApplicationUser().getId());
				view.executeBuild();
				view.executeSearch();
				contentLayout.add(view);
				view.setVisible(false);
				Tab tab = new Tab(PropertyResolver.getPropertyValueByLocale(ReferenceKey.MENU, menu.getId(),
						UI.getCurrent().getLocale(), menu.getName()));
				tab.addClassName("my-custom-sub-tab");
				Icon icon = VaadinIcon.CLOSE_CIRCLE.create();
				icon.addClickListener(new ComponentEventListener<ClickEvent<Icon>>() {

					private static final long serialVersionUID = 2142758407187810547L;

					@Override
					public void onComponentEvent(ClickEvent<Icon> event) {
						subTabSheet.setSelectedTab(previousSelectedTab);
						subTabSheet.remove(tab);
						createdSubMenusTabs.remove(tab);
						contentLayout.remove(view);
						lookUpMenusTabMap.remove(tab);
					}
				});
				tab.add(icon);

				createdSubMenusTabs.put(tab, view);
				lookUpMenusTabMap.put(tab, simpleSolutionsMenuItem);
				subTabSheet.add(tab);
				subTabSheet.setSelectedTab(tab);

				setUpFieldSelectedObservable(lookUpHolder, view, tab);

			}

			private void setUpFieldSelectedObservable(LookUpHolder lookUpHolder, AbstractBaseView view, Tab tab) {
				((BasicTemplate<?>) view).getLookUpSelectedObserver().subscribe(new Consumer<Object>() {

					@SuppressWarnings("unchecked")
					@Override
					public void accept(Object selectedEntity) throws Exception {
						subTabSheet.setSelectedTab(previousSelectedTab);
						subTabSheet.remove(tab);
						createdSubMenusTabs.remove(tab);
						lookUpMenusTabMap.remove(tab);
						contentLayout.remove(view);
						lookUpHolder.getLookUpField().setValue(selectedEntity);
					}
				});
			}
		});
	}

	private void setUpFieldLinkObservable() {
		sessionHolder.addReferenceKey(ReferenceKey.LOOKUP_FIELD_LINK_OBSERVEABLE, lookUpFieldLinkObserver);
		lookUpFieldLinkObserver.subscribe(new Consumer<LookUpHolder>() {

			@Override
			public void accept(LookUpHolder lookUpHolder) throws Exception {
				Menu menu = lookUpHolder.getMenu();
				SimpleSolutionsMenuItem simpleSolutionsMenuItem = new SimpleSolutionsMenuItem(menu);
				simpleSolutionsMenuItem.setParentEntity(lookUpHolder.getParentEntity());
				AbstractBaseView view = ViewUtil.initView(simpleSolutionsMenuItem, PopUpMode.NONE,
						sessionHolder.getSelectedRole().getId(), sessionHolder.getApplicationUser().getId());
				view.executeBuild();
				view.executeSearch();
				contentLayout.add(view);
				view.setVisible(false);
				Tab tab = new Tab(PropertyResolver.getPropertyValueByLocale(ReferenceKey.MENU, menu.getId(),
						UI.getCurrent().getLocale(), menu.getName()));
				tab.addClassName("my-custom-sub-tab");
				Icon icon = VaadinIcon.CLOSE_CIRCLE.create();
				icon.addClickListener(new ComponentEventListener<ClickEvent<Icon>>() {

					private static final long serialVersionUID = 6147547710178223723L;

					@Override
					public void onComponentEvent(ClickEvent<Icon> event) {
						subTabSheet.remove(tab);
						createdSubMenusTabs.remove(tab);
						subMenusTabMap.remove(tab);
						lookUpMenusTabMap.remove(tab);
						contentLayout.remove(view);
					}
				});
				tab.add(icon);
				subMenusTabMap.put(tab, simpleSolutionsMenuItem);
				createdSubMenusTabs.put(tab, view);
				lookUpMenusTabMap.put(tab, simpleSolutionsMenuItem);
				subTabSheet.add(tab);
				subTabSheet.setSelectedTab(tab);
			}
		});
	}

	public void resetTabs() {
		handleSelectedEntity(new EntitySelect());
		Set<Tab> keySet = lookUpMenusTabMap.keySet();
		for (Tab tab : keySet) {
			subMenusTabMap.remove(tab);
		}
		lookUpMenusTabMap.clear();
		subMenusTabMap.keySet().stream().forEach(p -> createdSubMenusTabs.remove(p));
		subTabSheet.setSelectedTab(parentViewTab);
	}

	public boolean shouldReset() {
		return (menus == null || menus.isEmpty());
	}

}
