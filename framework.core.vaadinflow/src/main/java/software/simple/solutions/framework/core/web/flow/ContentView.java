package software.simple.solutions.framework.core.web.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PopUpMode;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.web.AbstractBaseView;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.EntitySelect;
import software.simple.solutions.framework.core.web.SimpleSolutionsMenuItem;
import software.simple.solutions.framework.core.web.ViewUtil;
import software.simple.solutions.framework.core.web.components.CButton;
import software.simple.solutions.framework.core.web.lookup.LookUpHolder;

public class ContentView extends VerticalLayout implements BeforeEnterObserver {

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
				String menuName = abstractBaseView.getViewDetail().getMenu().getName();
				Tab tab = new Tab(menuName);
				createdSubMenusTabs.put(tab, abstractBaseView);
				subTabSheet.add(tab);

				List<Menu> subMenus = abstractBaseView.getViewDetail().getSubMenus();
				if (subMenus != null) {
					for (Menu menu : subMenus) {
						Tab subMenuTab = new Tab(menu.getName());
						subMenuTab.getStyle().set("background", "#f3d512");
						subMenuTab.getStyle().set("border-top-left-radius", "50%");
						subMenuTab.getStyle().set("border-top-right-radius", "5%");
						subMenuTab.getStyle().set("margin-right", "1px");
						subMenuTab.setVisible(false);
						subTabSheet.add(subMenuTab);
						SimpleSolutionsMenuItem simpleSolutionsMenuItem = new SimpleSolutionsMenuItem(menu);
						subMenusTabMap.put(subMenuTab, simpleSolutionsMenuItem);
					}
				}

				((AbstractBaseView) component).getEntitySelectedObserver().subscribe(new Consumer<EntitySelect>() {

					@Override
					public void accept(EntitySelect entitySelect) throws Exception {
						selectedEntity = entitySelect.getEntity();
						if (entitySelect.getEntity() == null) {
							subMenusTabMap.keySet().stream().forEach(p -> p.setVisible(false));
							Set<Tab> keySet = lookUpMenusTabMap.keySet();
							for (Tab tab : keySet) {
								subMenusTabMap.remove(tab);
							}
							lookUpMenusTabMap.clear();
						} else {
							subMenusTabMap.keySet().stream().forEach(p -> p.setVisible(true));
						}
					}
				});
			}
		}
	}

	private void initialize() {
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
		tabBar.add(subTabSheet);
		add(tabBar);

		subTabSheet.addSelectedChangeListener(new ComponentEventListener<Tabs.SelectedChangeEvent>() {

			private static final long serialVersionUID = -7201114375011146454L;

			@Override
			public void onComponentEvent(SelectedChangeEvent event) {
				Tab selectedTab = event.getSelectedTab();
				if (selectedTab != null) {
					contentLayout.getChildren().forEach(p -> p.setVisible(false));
					if (createdSubMenusTabs.containsKey(selectedTab)) {
						AbstractBaseView abstractBaseView = createdSubMenusTabs.get(selectedTab);
						// Optional<Component> optional =
						// contentLayout.getChildren()
						// .filter(p -> ((AbstractBaseView)
						// p).getViewDetail().getMenu().getId()
						// .compareTo(abstractBaseView.getViewDetail().getMenu().getId())
						// == 0)
						// .findFirst();
						Optional<Component> optional = contentLayout.getChildren().filter(p -> ((AbstractBaseView) p)
								.getViewDetail().getUuid().equalsIgnoreCase(abstractBaseView.getViewDetail().getUuid()))
								.findFirst();
						if (optional.isPresent()) {
							optional.get().setVisible(true);
						}
					} else {
						SimpleSolutionsMenuItem simpleSolutionsMenuItem = subMenusTabMap.get(event.getSelectedTab());
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
							e.printStackTrace();
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
			public void accept(Menu t) throws Exception {

			}
		});

		setUpFieldLinkObservable();

		setUpFieldSelectObservable();
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
				Tab tab = new Tab(menu.getName());
				tab.getStyle().set("background", "rgb(231, 233, 255) none repeat scroll 0% 0%");
				tab.getStyle().set("border-top-left-radius", "50%");
				tab.getStyle().set("border-top-right-radius", "5%");
				tab.getStyle().set("margin-right", "1px");
				Icon icon = VaadinIcon.CLOSE_CIRCLE.create();
				icon.addClickListener(new ComponentEventListener<ClickEvent<Icon>>() {

					private static final long serialVersionUID = 2142758407187810547L;

					@Override
					public void onComponentEvent(ClickEvent<Icon> event) {
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
				Tab tab = new Tab(menu.getName());
				tab.getStyle().set("background", "rgb(231, 233, 255) none repeat scroll 0% 0%");
				tab.getStyle().set("border-top-left-radius", "50%");
				tab.getStyle().set("border-top-right-radius", "5%");
				tab.getStyle().set("margin-right", "1px");
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

}
