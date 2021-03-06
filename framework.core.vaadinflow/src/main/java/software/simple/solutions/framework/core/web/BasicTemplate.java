package software.simple.solutions.framework.core.web;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome.Solid.Icon;
import com.github.appreciated.card.Card;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.constants.Columns;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.IMappedSuperClass;
import software.simple.solutions.framework.core.entities.MappedSuperClass;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PagingInfo;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;
import software.simple.solutions.framework.core.service.facade.ViewServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.util.SortingHelper;
import software.simple.solutions.framework.core.valueobjects.SuperVO;
import software.simple.solutions.framework.core.web.components.NotificationBuilder;
import software.simple.solutions.framework.core.web.components.Table;
import software.simple.solutions.framework.core.web.routing.Routes;

public abstract class BasicTemplate<T> extends AbstractBaseView implements GridTable, Build, BeforeEnterObserver {

	private static final long serialVersionUID = -4732858914162901000L;

	private static final Logger logger = LogManager.getLogger(BasicTemplate.class);

	public Grid<T> contentGrid;
	private ListDataProvider<T> dataProvider;
	private ActionBar actionBar;
	private PagingBar pagingBar;
	private HorizontalLayout topBarLayout;
	private VerticalLayout filterAndResultLayout;
	private FilterView filterView;
	private Card filterPanel;
	private FormView formView;
	private FormView readonlyFormView;
	private VerticalLayout formLayout;
	private VerticalLayout preFilterLayout;

	private Class<? extends ISuperService> serviceClass;
	private Class<? extends FilterView> filterClass;
	private Class<? extends FormView> formClass;
	private Class<? extends FormView> readonlyFormClass;
	private Class<?> entityClass;
	private SuperServiceFacade<?> superService;

	private boolean advancedSearch = true;
	private Set<Object> toDeleteEntities;
	private Set<Object> toDeleteItemIds;
	protected ConcurrentMap<Object, Boolean> sortingMap;
	protected SortingHelper sortingHelper;
	private List<GridItem> gridHeaderItems;
	private Set<String> hiddenColumnIds;
	private int gridRowHeight = 40;

	private boolean isNew = false;
	private boolean formMode = false;
	private boolean skipActiveColumn = false;
	private PagingSearchEvent pagingSearchEvent;
	private Object entity;
	private PagingSetting pagingSetting;
	private PagingResult<Object> pagingResult;
	private String updateObserverReferenceKey;
	private String editRoute;
	private Location location;
	private final BehaviorSubject<Object> lookUpSelectedObserver;

	public BasicTemplate() {
		sortingMap = new ConcurrentHashMap<Object, Boolean>();
		sortingHelper = new SortingHelper();
		toDeleteEntities = new HashSet<Object>();
		toDeleteItemIds = new HashSet<Object>();
		pagingSetting = new PagingSetting();
		gridHeaderItems = new ArrayList<GridItem>();
		hiddenColumnIds = new HashSet<String>();
		dataProvider = DataProvider.ofItems();
		lookUpSelectedObserver = BehaviorSubject.create();
	}

	public void initialize() {
		try {
			executeBuild();
		} catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			updateErrorContent(e);
			// new MessageWindowHandler(e);
		}

		location = event.getLocation();
		initialize();
		QueryParameters queryParameters = event.getLocation().getQueryParameters();
		List<String> idList = queryParameters.getParameters().get("id");
		if (idList != null && !idList.isEmpty()) {
			String id = idList.get(0);

			try {
				Object entity = superService.get(entityClass, NumberUtil.getLong(id));
				if (entity != null) {
					setSelectedEntity(entity);
					switchToForm(entity, false);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				updateErrorContent(e);
				// new MessageWindowHandler(e);
			}
		}
	}

	public void executePreBuild() throws FrameworkException {
		return;
	}

	@SuppressWarnings("unchecked")
	public void executeBuild() throws FrameworkException {
		removeAll();
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

		executePreBuild();

		setUpTemplateLayout();

		setUpService();
		setUpFilterView();

		setUpActionBar();
		setUpPagingBar();

		setUpActionButtons();

		setUpTable();

		setUpActionBarListeners();

		executePostBuild();

		// addUpdateObserverReferenceKey(getEntityReferenceKey());

		getUpdateObserver().subscribe(new Consumer<Object>() {

			@Override
			public void accept(Object t) throws Exception {
				if (t instanceof MappedSuperClass) {
					Optional<T> optional = dataProvider.getItems().stream()
							.filter(p -> ((MappedSuperClass) p).getId().compareTo(((MappedSuperClass) t).getId()) == 0)
							.findFirst();
					ArrayList<T> list = new ArrayList<T>(dataProvider.getItems());
					if (optional.isPresent()) {
						list.remove(optional.get());
					}
					list.add((T) t);
					dataProvider = DataProvider.ofCollection(list);
					dataProvider.refreshAll();
				}
			}
		});
	}

	public void executePostBuild() throws FrameworkException {
		return;
	}

	private void setUpTemplateLayout() {
		setSpacing(false);
		setMargin(false);
		setPadding(false);

		topBarLayout = new HorizontalLayout();
		topBarLayout.setWidth("100%");
		topBarLayout.setHeight("-1px");
		add(topBarLayout);

		preFilterLayout = new VerticalLayout();
		preFilterLayout.setVisible(false);
		add(preFilterLayout);

		// viewContentPanel = new SplitLayout();
		// viewContentPanel.setOrientation(Orientation.HORIZONTAL);
		// viewContentPanel.addThemeVariants(SplitLayoutVariant.LUMO_MINIMAL);
		// viewContentPanel.setSplitterPosition(100);
		// viewContentPanel.setSizeFull();

		// if (orientation == Orientation.HORIZONTAL) {
		// filterAndResultLayout = new HorizontalLayout();
		// } else {
		filterAndResultLayout = new VerticalLayout();
		// }
		filterAndResultLayout.setSpacing(true);
		filterAndResultLayout.setMargin(false);
		filterAndResultLayout.setPadding(false);
		filterAndResultLayout.setWidth("100%");
		filterAndResultLayout.setHeight("100%");
		add(filterAndResultLayout);

		setSizeFull();
		filterAndResultLayout.setSizeFull();
		// setExpandRatio(filterAndResultLayout, 1);
		filterAndResultLayout.setSizeFull();

		// add(viewContentPanel);
		// viewContentPanel.setVisible(false);
		// setExpandRatio(viewContentPanel, 1);

		formLayout = new VerticalLayout();
		formLayout.setSpacing(false);
		formLayout.setMargin(false);
		formLayout.setWidth("100%");
		add(formLayout);
	}

	private void setUpActionBar() throws FrameworkException {
		actionBar = new ActionBar();

		List<String> privileges = new ArrayList<String>();

		IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
		Configuration consolidateRoleConfiguration = configurationService
				.getByCode(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE);
		boolean consolidateRoles = false;
		if (consolidateRoleConfiguration != null && consolidateRoleConfiguration.getBoolean()) {
			consolidateRoles = true;
		}

		if (consolidateRoles) {
			privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent()).getPrivilegesByViewIdAndUserId(
					getViewDetail().getMenu().getView().getId(), getSessionHolder().getApplicationUser().getId());
		} else {
			privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent()).getPrivilegesByViewIdAndRoleId(
					getViewDetail().getMenu().getView().getId(), getSessionHolder().getSelectedRole().getId());
		}

		// ActionState actionState =
		// ViewActionStateUtil.createActionState(getViewDetail().getPrivileges(),
		// getViewDetail().getView().getId(),
		// getSessionHolder().getSelectedRole().getId());
		ActionState actionState = new ActionState(privileges);
		getViewDetail().setActionState(actionState);
		actionBar.setActionState(actionState);
		actionBar.setSearchDisabled();
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.add(actionBar);
		// horizontalLayout.setComponentAlignment(actionBar,
		// Alignment.MIDDLE_LEFT);

		topBarLayout.add(horizontalLayout);
		topBarLayout.expand(horizontalLayout);
		// topBarLayout.setComponentAlignment(horizontalLayout,
		// Alignment.MIDDLE_LEFT);

		// errorPopupBtn = new PopupButton();
		// errorPopupBtn.setIcon(CxodeIcons.FAIL);
		// errorPopupBtn.addStyleName(Style.RESIZED_ICON_80);
		// errorPopupBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		// errorPopupBtn.addStyleName(ValoTheme.BUTTON_DANGER);
		// errorPopupBtn.setVisible(false);

		// HorizontalLayout popupLayout = new HorizontalLayout();
		// errorPopupBtn.setContent(popupLayout); // Set popup content
		// topBarLayout.add(errorPopupBtn);
		/*
		 * example
		 */
		// Button modifyButton = new Button("Modify");
		// modifyButton.setIcon(VaadinIcons.ABACUS);
		// popupLayout.add(modifyButton);
		// Button addButton = new Button("Add");
		// addButton.setIcon(VaadinIcons.ABSOLUTE_POSITION);
		// popupLayout.add(addButton);
		// Button deleteButton = new Button("Delete");
		// deleteButton.setIcon(VaadinIcons.ACADEMY_CAP);
		// popupLayout.add(deleteButton);
		// popupButton.setPopupVisible(true);
	}

	public void updateErrorContent(Exception e) {
		DetailsWindow.build(e);
	}

	// public void resetErrorContent() {
	// errorPopupBtn.setVisible(false);
	// }

	private void setUpPagingBar() {
		pagingBar = new PagingBar();
		topBarLayout.add(pagingBar);
		topBarLayout.setJustifyContentMode(JustifyContentMode.END);

		pagingSearchEvent = new PagingSearchEvent() {

			@Override
			public void handleSearch(int currentPage, int maxResult) {

				try {
					clearToDeleteIds();
					handleViewSearch();
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					// updateErrorContent(e);
					// new MessageWindowHandler(e);
				}
			}

			@Override
			public Long count() throws FrameworkException {
				return handleCount(false);
			}
		};

		pagingBar.addPagingSearchEvent(pagingSearchEvent);
	}

	public void setUpActionButtons() {
		actionBar.setNewDisabled();
		actionBar.setDeleteDisabled();
		actionBar.setSaveDisabled();
		actionBar.setPrintDisabled();
		actionBar.setAuditDisabled();
		actionBar.setHelpDisabled();
		actionBar.setBackDisabled();
		actionBar.setInfoDisabled();
		actionBar.setAuditDisabled();
		actionBar.hideCreatingItem();

		actionBar.authorizeNew();
		actionBar.authorizeSearch();
		actionBar.authorizeErase();

		pagingBar.setVisible(true);

		Role role = getSessionHolder().getSelectedRole();
		if (role != null && role.getId().compareTo(1L) == 0) {
			actionBar.authorizeInfo();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	private Grid<T> setUpGrid() {
		gridHeaderItems.clear();
		Grid<T> contentGrid = new Grid<T>();
		contentGrid.setWidth("100%");
		contentGrid.setSelectionMode(SelectionMode.NONE);

		switch (getPopUpMode()) {
		case TAB:
		case POPUP: {
			Column<T> lookUpColum = contentGrid.addComponentColumn(new ValueProvider<T, Button>() {

				@Override
				public Button apply(T source) {

					Button popUpBtn = new Button();
					popUpBtn.setIcon(FontAwesome.Solid.CHECK_CIRCLE.create());
					// popUpBtn.setDescription(
					// PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SELECT_LOOKUP,
					// UI.getCurrent().getLocale()));
					popUpBtn.addClickListener(new PopUpModeEvent(source));
					return popUpBtn;
				}
			});
			lookUpColum.setFlexGrow(0).setWidth("40px");
			lookUpColum.setId(Columns.POPUP_ITEM_SELECTED);
			Icon icon = FontAwesome.Solid.CHECK_CIRCLE.create();
			icon.setSize("20px");
			lookUpColum.setHeader(icon);
		}
			break;
		case NONE: {
			Column<T> rowSelectedColumn = contentGrid.addComponentColumn(new ValueProvider<T, Checkbox>() {

				@Override
				public Checkbox apply(T source) {
					Checkbox selectedChk = new Checkbox();
					// selectedChk.addValueChangeListener(new
					// RowSelectCheckClickListener(source));
					return selectedChk;
				}
			});
			rowSelectedColumn.setFlexGrow(0).setWidth("40px");
			// rowSelectedColumn.setAutoWidth(true);
			rowSelectedColumn.setId(Columns.ROW_SELECTED);
			Icon icon = FontAwesome.Solid.CHECK_CIRCLE.create();
			icon.setSize("20px");
			rowSelectedColumn.setHeader(icon);
		}
			break;
		default:
			break;
		}

		Column<T> formEditorColumn = contentGrid.addComponentColumn(new ValueProvider<T, Button>() {

			@Override
			public Button apply(T source) {
				Button formBtn = new Button();
				formBtn.setIcon(FontAwesome.Solid.EDIT.create());
				formBtn.addClickListener(new BasicTemplate.FormSetup(source));
				return formBtn;
			}
		});
		formEditorColumn.setFlexGrow(0).setWidth("40px");
		formEditorColumn.setId(Columns.FORM_EDITOR);
		Icon icon = FontAwesome.Solid.EDIT.create();
		icon.setSize("20px");
		formEditorColumn.setHeader(icon);

		setUpCustomColumns();

		for (GridItem gridItem : gridHeaderItems) {
			Column column = null;
			if (gridItem.isComponent()) {
				column = contentGrid.addComponentColumn(gridItem.getValueProvider());
			} else {
				column = contentGrid.addColumn(gridItem.getValueProvider());
			}
			column.setHeader(
					PropertyResolver.getPropertyValueByLocale(gridItem.getCaptionKey(), UI.getCurrent().getLocale()));
			column.setId(gridItem.getCaptionKey());
		}

		for (String hiddenColumnId : hiddenColumnIds) {
			Column<T> column = contentGrid.getColumnByKey(hiddenColumnId);
			if (column != null) {
				// column.setHidden(true);
			}
		}

		if (!skipActiveColumn) {
			Column<T> activeColumn = contentGrid.addComponentColumn(new ValueProvider<T, Icon>() {

				@Override
				public Icon apply(T source) {
					Icon activeIcon = FontAwesome.Solid.CIRCLE.create();
					if (((IMappedSuperClass) source).getActive() != null && ((IMappedSuperClass) source).getActive()) {
						activeIcon.setColor("green");
					} else {
						activeIcon.setColor("red");
					}
					return activeIcon;
				}
			});
			activeColumn.setFlexGrow(0).setWidth("40px");
			activeColumn.setId(SystemProperty.SYSTEM_ENTITY_ACTIVE);
			Icon activeIcon = FontAwesome.Solid.TOGGLE_ON.create();
			activeIcon.setSize("20px");
			activeColumn.setHeader(activeIcon);
		}

		// contentGrid.setBodyRowHeight(getGridRowHeight());

		return contentGrid;
	}

	private void setUpTable() {

		contentGrid = setUpGrid();
		filterAndResultLayout.add(contentGrid);
		// filterAndResultLayout.setExpandRatio(contentGrid, 1f);

	}

	private void createFormView() throws FrameworkException {
		if (formClass == null) {
			return;
		}
		try {
			formView = formClass.newInstance();
		} catch (SecurityException | IllegalArgumentException | InstantiationException | IllegalAccessException e) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}
		formView.getPushEntityToFormSubject().subscribe(new Consumer<Object>() {

			@Override
			public void accept(Object source) throws Exception {
				Menu menu = getViewDetail().getMenu();
				View view = menu.getView();
				Class<? extends Component> forName = null;
				Route route = null;
				if (view != null) {
					String viewClassName = view.getViewClassName();
					try {
						forName = (Class<? extends Component>) Class.forName(viewClassName);
						route = forName.getAnnotation(Route.class);
						if (route != null) {
							Map<String, String> qpm = new HashMap<String, String>();
							qpm.put("id", ((MappedSuperClass) source).getId().toString());
							QueryParameters simple = QueryParameters.simple(qpm);
							if (location != null) {
								location = new Location(location.getPath(), simple);
								UI.getCurrent().getPage().getHistory().pushState(null, location);
								String value = route.value();
								UI.getCurrent().navigate(value, simple);
							}
						}
					} catch (ClassNotFoundException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		});
		formView.getByPassValidationSubject().subscribe(new Consumer<Object>() {

			@Override
			public void accept(Object source) throws Exception {
				doForSaveByPassValidation();
			}
		});

		formView.setReferenceKeys(getReferenceKeys());
		formView.setParentEntity(getParentEntity());
		if (getParentReferenceKey() != null
				&& !getParentReferenceKey().equalsIgnoreCase(ReferenceKey.PROPERTY_PER_LOCALE)) {
			formView.addToReferenceKey(getParentReferenceKey(), getSelectedEntity());
			formView.addToReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_KEY, getParentReferenceKey());
			if (getSelectedEntity() != null) {
				formView.addToReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_ID,
						((MappedSuperClass) getSelectedEntity()).getId().toString());
			}
		}
		formView.setSelectedEntity(getSelectedEntity());
		formView.setViewDetail(getViewDetail());
		formView.executeBuild();
		formLayout.removeAll();
		formLayout.add(formView);
	}

	private void createReadonlyFormView() throws FrameworkException {
		if (readonlyFormClass == null) {
			return;
		}
		try {
			readonlyFormView = readonlyFormClass.getConstructor(new Class[] { this.getClass() })
					.newInstance(new Object[] { this });
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InstantiationException
				| IllegalAccessException | InvocationTargetException e) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.COULD_NOT_CREATE_VIEW,
					UI.getCurrent().getLocale(), e);
		}

		readonlyFormView.setParentEntity(getParentEntity());
		if (getParentReferenceKey() != null
				&& !getParentReferenceKey().equalsIgnoreCase(ReferenceKey.PROPERTY_PER_LOCALE)) {
			readonlyFormView.addToReferenceKey(getParentReferenceKey(), getSelectedEntity());
			formView.addToReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_KEY, getParentReferenceKey());
			if (getSelectedEntity() != null) {
				formView.addToReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_ID,
						((MappedSuperClass) getSelectedEntity()).getId().toString());
			}
		}
		readonlyFormView.setSelectedEntity(getSelectedEntity());
		readonlyFormView.setViewDetail(getViewDetail());
		readonlyFormView.executeBuild();
		formLayout.add(readonlyFormView);
	}

	private void setUpFormData(Object entity) throws FrameworkException {
		this.entity = formView.setFormValues(entity);
	}

	private void setUpReadonlyFormData(Object entity) throws FrameworkException {
		if (readonlyFormView != null) {
			readonlyFormView.setFormValues(entity);
		} else {
			createFormView();
			setUpFormData(entity);
		}
	}

	private void setFormVisibility() {
		formLayout.setVisible(true);
		// viewContentPanel.setVisible(true);
		filterAndResultLayout.setVisible(false);
		preFilterLayout.setVisible(false);
	}

	protected void setServiceClass(Class<? extends ISuperService> serviceClass) {
		this.serviceClass = serviceClass;
	}

	protected void setFilterClass(Class<? extends FilterView> filterClass) {
		this.filterClass = filterClass;
	}

	protected void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	private void setUpService() {
		if (serviceClass != null) {
			try {
				Constructor<?>[] constructors = serviceClass.getConstructors();
				Constructor<? extends ISuperService> constructor = serviceClass.getConstructor(UI.class,
						serviceClass.getInterfaces()[0].getClass());
				superService = (SuperServiceFacade<?>) constructor.newInstance(UI.getCurrent(),
						serviceClass.getInterfaces()[0]);
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	private void setUpFilterView() throws FrameworkException {
		if (filterClass == null) {
			return;
		}
		try {
			filterView = filterClass.getConstructor(new Class[] { this.getClass() }).newInstance(new Object[] { this });
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InstantiationException
				| IllegalAccessException | InvocationTargetException e) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.COULD_NOT_CREATE_FILTER,
					UI.getCurrent().getLocale(), e);
		}
		filterView.setViewDetail(getViewDetail());
		filterView.setParentEntity(getParentEntity());
		filterView.setReferenceKeys(getReferenceKeys());
		filterView.executeBuild();
		filterPanel = new Card();
		filterPanel.setVisible(advancedSearch);
		filterPanel.setHeight("-1px");
		filterPanel.setWidth("100%");
		filterPanel.add(filterView);
		filterPanel.getElement().getStyle().set("overflow", "auto");
		filterPanel.getElement().getStyle().set("box-shadow",
				"var(--lumo-box-shadow-s, var(--material-shadow-elevation-2dp))");
		// filterPanel.setCaption(PropertyResolver.getPropertyValueByLocale(SystemProperty.SEARCH_FILTER,
		// UI.getCurrent().getLocale()));
		filterAndResultLayout.add(filterPanel);
	}

	@SuppressWarnings("unchecked")
	private void handleViewSearch() throws FrameworkException {
		pagingSetting.setMaxResult(pagingBar.getMaxResult());
		pagingSetting.setStartPosition(pagingBar.getStartPosition());
		pagingResult = superService.findBySearch(createCriteria(), pagingSetting);
		prePopulateResultsTable(pagingResult.getResult());

		dataProvider = DataProvider.ofCollection((Collection<T>) pagingResult.getResult());
		contentGrid.setDataProvider(dataProvider);
		dataProvider.refreshAll();
	}

	private Long handleCount(boolean showCount) throws FrameworkException {
		Long count = pagingResult.getCount();
		pagingBar.setTotalRows(count);

		if (showCount) {
			// NotificationWindow.notificationNormalWindow(SystemProperty.TOTAL_RECORDS_FOUND,
			// new Object[] { count });
		}

		return count;
	}

	public void handleResetSearch() throws FrameworkException {
		pagingBar.reset();
		handleViewSearch();
		handleCount(true);
	}

	public void handleBackFromForm() {
		Boolean viewContentUpdated = getViewContentUpdated();
		doForBack();

		if (location != null && location.getQueryParameters() != null) {
			Map<String, List<String>> parameters = location.getQueryParameters().getParameters();
			QueryParameters queryParameters = new QueryParameters(parameters);
			location = new Location(location.getPath());
			UI.getCurrent().getPage().getHistory().replaceState(null, location);
		}
	}

	private void doForBack() {
		formLayout.removeAll();
		formLayout.setVisible(false);
		filterAndResultLayout.setVisible(true);
		if (preFilterLayout.getComponentCount() > 0) {
			preFilterLayout.setVisible(true);
		}
		setUpActionButtons();
		formMode = false;
		setSelectedEntity(null);
		resetSubMenuItems();
		getEntitySelectedObserver().onNext(new EntitySelect());
	}

	private void setUpActionBarListeners() {
		actionBar.setActionSearch(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -4849048407300214220L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				try {
					// resetErrorContent();
					handleResetSearch();
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					// updateErrorContent(e);
					// new MessageWindowHandler(e);
				}
			}
		});

		actionBar.setActionToggleAdvancedSearch(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 6918893280700843492L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				// VerticalLayout layout = new VerticalLayout();
				// layout.setMargin(false);
				// layout.setSpacing(true);
				// CButton searchBtn = new CButton();
				// searchBtn.setIcon(FontAwesome.Solid.SEARCH.create());
				// searchBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
				// add(searchBtn);
				// searchBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SEARCH,
				// UI.getCurrent().getLocale()));
				// layout.add(searchBtn, filterView);
				// QuickPopup qp = new
				// QuickPopup(actionBar.getToggleAdvancedSearchBtn().getElement(),
				// layout);
				// qp.show();
				advancedSearch = !advancedSearch;
				filterPanel.setVisible(advancedSearch);
			}
		});

		actionBar.setActionBack(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -3771741387274418165L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				// resetErrorContent();
				handleBackFromForm();
			}
		});

		actionBar.setActionErase(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 7495574048115888107L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (filterView != null) {
					filterView.erase();
				}
			}
		});

		actionBar.setActionNew(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -8846603542113287812L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				// resetErrorContent();
				initNewForm();
			}
		});

		actionBar.setActionEdit(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -2098654946031634783L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				try {
					switchToForm(getSelectedEntity(), true);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					updateErrorContent(e);
				}
			}
		});

		// actionBar.setActionCancel(new
		// ComponentEventListener<ClickEvent<Button>>() {
		//
		// private static final long serialVersionUID = 3499030569856099283L;
		//
		// @Override
		// public void onComponentEvent(ClickEvent<Button> event) {
		// try {
		// switchToForm(getSelectedEntity(), false);
		// } catch (FrameworkException e) {
		// logger.error(e.getMessage(), e);
		// updateErrorContent(e);
		// }
		// }
		// });

		actionBar.setActionSave(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -7323502285236604385L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				handleForSaveClicked();
			}
		});

		actionBar.setActionDelete(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				// resetErrorContent();
				// ConfirmWindow confirmWindow = new
				// ConfirmWindow(SystemProperty.DELETE_HEADER,
				// SystemProperty.DELETE_CONFIRMATION_REQUEST,
				// SystemProperty.CONFIRM, SystemProperty.CANCEL);
				// confirmWindow.execute(new ConfirmationHandler() {
				//
				// @SuppressWarnings({ "unchecked", "rawtypes" })
				// @Override
				// public void handlePositive() {
				// if (toDeleteEntities != null && !toDeleteEntities.isEmpty())
				// {
				// try {
				// try {
				// int deleted = superService.delete(new
				// ArrayList(toDeleteEntities));
				// handleReactiveUpdate(toDeleteEntities);
				// } catch (DataIntegrityViolationException e) {
				// throw new
				// FrameworkException(SystemMessageProperty.DATA_FOREIGN_KEY_CONSTRAINT,
				// e);
				// }
				// } catch (FrameworkException e) {
				// // logger.error(e.getMessage(), e);
				// updateErrorContent(e);
				// }
				// } else {
				// NotificationWindow.notificationNormalWindow(SystemProperty.NO_RECORD_SELECTED);
				// }
				//
				// if (formMode) {
				// setViewContentUpdated(false);
				// handleBackFromForm();
				// }
				// try {
				// handleResetSearch();
				// } catch (FrameworkException e) {
				// logger.error(e.getMessage(), e);
				// updateErrorContent(e);
				// }
				// }
				//
				// @Override
				// public void handleNegative() {
				// // TODO Auto-generated method stub
				//
				// }
				// });
			}
		});

		actionBar.setActionInfo(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -2864840063433847243L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				Dialog dialog = new Dialog();
				ViewDetail viewDetail = getViewDetail();
				Table table = new Table(2, 2);
				dialog.add(table);
				table.setWidth("100px");
				table.add(new Text("viewClass:"), 0, 0);
				table.add(new Text(viewDetail.getMenu().getView().getViewClassName()), 1, 0);
				table.add(new Text("menu id:"), 0, 1);
				table.add(new Text(viewDetail.getMenu().getId().toString()), 1, 1);
				dialog.open();

			}
		});

		// actionBar.setActionRestore(new Command() {
		//
		// private static final long serialVersionUID = 5484577968239189288L;
		//
		// @Override
		// public void menuSelected(MenuItem selectedItem) {
		// resetErrorContent();
		// ConfirmWindow confirmWindow = new
		// ConfirmWindow(SystemProperty.RESTORE_HEADER,
		// SystemProperty.RESTORE_CONFIRMATION_REQUEST, SystemProperty.CONFIRM,
		// SystemProperty.CANCEL);
		// confirmWindow.execute(new ConfirmationHandler() {
		//
		// @Override
		// public void handlePositive() {
		// try {
		// try {
		// entity = superService.restore(entityClass, ((IMappedSuperClass)
		// entity).getId());
		// updateTabs();
		// setSelectedEntity(entity);
		// switchToForm(entity, false);
		// NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
		// } catch (InvalidDataAccessApiUsageException e) {
		// throw new FrameworkException(SystemMessageProperty.FAILED_TO_PERSIST,
		// e);
		// }
		// actionBar.authorizeSave();
		// } catch (FrameworkException e) {
		// actionBar.authorizeSave();
		// logger.error(e.getMessage(), e);
		// updateErrorContent(e);
		// // new MessageWindowHandler(e);
		// }
		// }
		//
		// @Override
		// public void handleNegative() {
		// }
		// });
		// }
		// });
		//
		// actionBar.setActionExportToExcel(new Command() {
		//
		// private static final long serialVersionUID = -4274380478004375818L;
		//
		// @SuppressWarnings("unchecked")
		// @Override
		// public void menuSelected(MenuItem selectedItem) {
		// resetErrorContent();
		// try {
		// Grid<T> grid = setUpGrid();
		// Column<T, ?> formEditorColum = grid.getColumn(Columns.FORM_EDITOR);
		// if (formEditorColum != null) {
		// formEditorColum.setHidden(true);
		// }
		//
		// Configuration configuration =
		// ConfigurationServiceFacade.get(UI.getCurrent())
		// .getByCode(Configuration.class,
		// ConfigurationProperty.APPLICATION_EXPORT_ROW_COUNT);
		// Long maxRecords = 10000L;
		// if (configuration != null &&
		// StringUtils.isNotBlank(configuration.getValue())) {
		// maxRecords = NumberUtil.getLong(configuration.getValue());
		// }
		//
		// PagingSetting pagingSetting = new PagingSetting(0,
		// maxRecords.intValue());
		// PagingResult<?> pagingResult =
		// superService.findBySearch(createCriteria(), pagingSetting);
		//
		// grid.setItems((Collection<T>) pagingResult.getResult());
		// List<Column<T, ?>> columns = grid.getColumns();
		// List<String> visibleColumns = new ArrayList<String>();
		// List<String> columHeaders = new ArrayList<String>();
		// HeaderRow headerRow = grid.getDefaultHeaderRow();
		// for (Column<T, ?> column : columns) {
		// // boolean hidden = column.isHidden();
		// String id = column.getId();
		// visibleColumns.add(id);
		// String header = headerRow.getCell(id).getText();
		// columHeaders.add(header);
		// }
		// visibleColumns.remove(Columns.ROW_SELECTED);
		// visibleColumns.remove(Columns.FORM_EDITOR);
		// visibleColumns.remove(Columns.POPUP_ITEM_SELECTED);
		// visibleColumns.remove(SystemProperty.SYSTEM_ENTITY_ACTIVE);
		//
		// ExportExcelComponentConfiguration<T> componentConfig = new
		// ExportExcelComponentConfigurationBuilder<T>()
		// .withGrid(grid)
		// .withVisibleProperties(visibleColumns.toArray(new
		// String[visibleColumns.size()]))
		// .withHeaderConfigs(Arrays.asList(new
		// ComponentHeaderConfigurationBuilder()
		// .withAutoFilter(true).withColumnKeys(
		// columHeaders.toArray(new String[columHeaders.size()]))
		// .build()))
		// .build();
		// ExportExcelSheetConfiguration<T> sheetConfig = new
		// ExportExcelSheetConfigurationBuilder<T>()
		// .withReportTitle("Export").withSheetName("Sheet 1")
		// .withComponentConfigs(Arrays.asList(componentConfig))
		// .withIsHeaderSectionRequired(Boolean.TRUE).withDateFormat("dd-MMM-yyyy").build();
		// ExportExcelConfiguration<T> config = new
		// ExportExcelConfigurationBuilder<T>()
		// .withSheetConfigs(Arrays.asList(sheetConfig)).build();
		// new ExportToExcel<>(ExportType.XLSX, config).export();
		//
		// } catch (FrameworkException e) {
		// logger.error(e.getMessage(), e);
		// }
		// }
		// });
		//
		// actionBar.setActionAudit(new Command() {
		//
		// private static final long serialVersionUID = -6460639017503880781L;
		//
		// @Override
		// public void menuSelected(MenuItem selectedItem) {
		// resetErrorContent();
		// try {
		// Window window = new Window();
		// window.setWidth("90%");
		// window.setHeight("90%");
		// window.center();
		// window.setModal(true);
		// window.setCaption(PropertyResolver.getPropertyValueByLocale(AuditProperty.AUDIT));
		// AuditView auditView = new AuditView();
		// auditView.setEntityClass(entityClass);
		// auditView.setEntityId(((IMappedSuperClass)
		// BasicTemplate.this.entity).getId());
		// auditView.executeBuild();
		// auditView.executeSearch();
		// window.setContent(auditView);
		// UI.getCurrent().addWindow(window);
		// } catch (FrameworkException e) {
		// updateErrorContent(e);
		// // new MessageWindowHandler(e);
		// }
		// }
		// });
	}

	@Override
	public boolean validate() throws FrameworkException {
		if (formView != null) {
			Boolean valid = formView.validate();
			if (!valid) {
				return false;
			}
		}
		return true;
	}

	public Object formUpdate() throws FrameworkException {
		if (formView != null && formMode) {
			SuperVO formValues = (SuperVO) formView.getFormValues();
			formValues.setUpdatedBy(getSessionHolder().getApplicationUser().getId());
			formValues.setUpdatedDate(LocalDateTime.now());
			formValues.setCurrentRoleId(getSessionHolder().getSelectedRole().getId());
			formValues.setCurrentUserId(getSessionHolder().getApplicationUser().getId());
			formValues.setNew(isNew);
			formValues.setLocale(UI.getCurrent().getLocale());

			try {
				setUpService();
				entity = superService.updateSingle(formValues);
				handleReactiveUpdate(entity);
				// updateExistingResultSet();
				setSelectedEntity(entity);
				switchToForm(entity, true);
				return entity;
			} catch (InvalidDataAccessApiUsageException | NullPointerException e) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FAILED_TO_PERSIST,
						UI.getCurrent().getLocale(), e);
			}
		}
		return null;
	}

	private void handleReactiveUpdate(Object item) {
		getUpdateObserver().onNext(item);
		if (updateObserverReferenceKey != null) {
			BehaviorSubject<Object> observer = getReferenceKey(updateObserverReferenceKey);
			if (observer != null) {
				observer.onNext(item);
			}
		}
	}

	public void postUpdate(software.simple.solutions.framework.core.web.Action action) {
		return;
	}

	protected void initNewForm() {
		try {
			// hideSecondComponent();
			// secondContentPanel.setContent(null);
			setSelectedEntity(null);

			setUpFormActions();
			createFormView();
			formView.handleNewForm();
			actionBar.setDeleteDisabled();
			setFormVisibility();
			isNew = true;
			formMode = true;
			resetAllSelectCheckBoxes();
			clearToDeleteIds();
			resetSubMenuItems();
			getEntitySelectedObserver().onNext(new EntitySelect(null));
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			// updateErrorContent(e);
			// new MessageWindowHandler(e);
		}
	}

	private void resetSubMenuItems() {
		topBarLayout.setVisible(true);
		actionBar.setVisible(true);
	}

	protected void activateAdvanceSearchLayout() {
		if (filterPanel != null) {
			filterPanel.setVisible(advancedSearch);
		}
	}

	protected PagingInfo getPagingInfo() throws FrameworkException {
		PagingInfo searchMode = new PagingInfo();
		searchMode.setMaxResult(pagingBar.getMaxResult());
		searchMode.setStartPosition(pagingBar.getStartPosition());
		return searchMode;
	}

	protected SuperVO createCriteria() throws FrameworkException {
		PagingInfo pagingInfo = getPagingInfo();
		SuperVO vo = (SuperVO) filterView.getCriteria();
		vo.setEntityClass(entityClass);
		if (vo.getSortingHelper() != null) {
			sortingHelper = vo.getSortingHelper();
		}
		vo.setSortingHelper(sortingHelper);
		vo.setPagingInfo(pagingInfo);
		vo.setCurrentUserId(getSessionHolder().getApplicationUser().getId());
		vo.setCurrentRoleId(getSessionHolder().getSelectedRole().getId());
		// if (getParentEntity() != null) {
		// vo.setId(((IMappedSuperClass) getParentEntity()).getId());
		// }
		return vo;
	}

	public void prePopulateResultsTable(List<?> results) throws FrameworkException {
		return;
	}

	public void populateRow(Object itemId, Object result) throws FrameworkException {
		return;
	}

	private class PopUpModeEvent implements ComponentEventListener<ClickEvent<Button>> {

		private static final long serialVersionUID = -5165198948151324581L;
		private Object source;

		public PopUpModeEvent(Object source) {
			this.source = source;
		}

		@Override
		public void onComponentEvent(ClickEvent<Button> event) {
			switch (getPopUpMode()) {
			case POPUP:
				setPopUpEntity(source);
				getPopUpWindow().setData(true);
				getPopUpWindow().close();
				break;
			case TAB:
				lookUpSelectedObserver.onNext(source);
				break;
			default:
				break;
			}
		}
	}

	public class FormSetup implements ComponentEventListener<ComponentEvent<Button>> {

		private static final long serialVersionUID = -3618276831491958217L;

		private Object selectedEntity;

		public FormSetup(Object selectedEntity) {
			this.selectedEntity = selectedEntity;
		}

		@Override
		public void onComponentEvent(ComponentEvent<Button> event) {
			setUpForm(selectedEntity);
		}
	}

	public void setUpForm(Object selectedEntity) {
		setSelectedEntity(selectedEntity);
		try {
			selectedEntity = superService.get(entityClass, ((MappedSuperClass) selectedEntity).getId());
			setSelectedEntity(selectedEntity);
			switchToForm(selectedEntity, false);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			updateErrorContent(e);
			// new MessageWindowHandler(e);
		}
	}

	public void switchToForm(Object entity, boolean editable) throws FrameworkException {
		if (readonlyFormClass == null) {
			editable = true;
		}
		if (editable) {
			createFormView();
			setUpFormData(entity);
		} else {
			createReadonlyFormView();
			setUpReadonlyFormData(entity);
		}
		setFormVisibility();
		isNew = false;
		formMode = true;
		resetAllSelectCheckBoxes();
		clearToDeleteIds();
		addToDelete(entity);
		setUpFormActions();
		if (editable) {
			actionBar.authorizeSave();
			// if (readonlyFormClass == null) {
			// actionBar.setCancelDisabled();
			// }
		} else {
			actionBar.authorizeEdit();
		}

		Map<String, String> qpm = new HashMap<String, String>();
		qpm.put("id", ((MappedSuperClass) entity).getId().toString());
		QueryParameters simple = QueryParameters.simple(qpm);
		if (location != null) {
			location = new Location(location.getPath(), simple);
			UI.getCurrent().getPage().getHistory().pushState(null, location);
		}

		getEntitySelectedObserver().onNext(new EntitySelect(entity));
	}

	private void resetAllSelectCheckBoxes() {
		// if (toDeleteItemIds != null && !toDeleteItemIds.isEmpty()) {
		// for (Object itemId : toDeleteItemIds) {
		// CheckBox checkBox = getContainerPropertyComponent(itemId,
		// Columns.ROW_SELECTED);
		// if (checkBox != null) {
		// checkBox.setValue(false);
		// }
		// }
		// }
	}

	private void setUpFormActions() {
		actionBar.setSearchDisabled();
		actionBar.authorizeBack();
		if (getSelectedEntity() == null) {
			actionBar.authorizeCreatingItem();
			actionBar.authorizeSaveNew();
		}
		if (getSelectedEntity() != null) {
			actionBar.hideCreatingItem();
			actionBar.authorizeDelete();
			actionBar.authorizeEdit();
		}

		Role role = getSessionHolder().getSelectedRole();
		if (role != null && role.getId().compareTo(1L) == 0) {
			actionBar.authorizeInfo();
			actionBar.authorizeAudit();
		}

		pagingBar.setVisible(false);
	}

	// protected class RowSelectCheckClickListener implements
	// ValueChangeListener<Boolean> {
	//
	// private static final long serialVersionUID = -6792371147929564759L;
	//
	// private Object entity;
	//
	// public RowSelectCheckClickListener(Object entity) {
	// this.entity = entity;
	// }
	//
	// @Override
	// public void valueChange(ValueChangeEvent<Boolean> event) {
	// if ((boolean) event.getValue()) {
	// addToDelete(entity);
	// } else {
	// removeFromDelete(entity);
	// }
	// manageDeleteBtn();
	// }
	//
	// }

	public void addToDelete(Object entity) {
		if (toDeleteEntities == null) {
			toDeleteEntities = new HashSet<Object>();
		}
		toDeleteEntities.add(entity);

		if (entity != null) {
			if (toDeleteItemIds == null) {
				toDeleteItemIds = new HashSet<Object>();
			}
			toDeleteItemIds.add(entity);
		}
	}

	public void removeFromDelete(Object entity) {
		if (toDeleteEntities != null) {
			toDeleteEntities.remove(entity);
		}

		if (entity != null) {
			if (toDeleteItemIds != null) {
				toDeleteItemIds.remove(entity);
			}
		}
	}

	// public <V> GridItem<T, V> addContainerProperty(ValueProvider<T, V>
	// valueProvider, String captionKey)
	// throws UnsupportedOperationException {
	// GridItem<T, V> gridItem = new GridItem<T, V>(valueProvider, captionKey);
	// gridHeaderItems.add(gridItem);
	// return gridItem;
	// }

	// public <V> GridItem<T, V> addContainerProperty(ValueProvider<T, V>
	// valueProvider, String captionKey)
	// throws UnsupportedOperationException {
	// GridItem<T, V> gridItem = new GridItem<T, V>(valueProvider, captionKey);
	// gridItem.setComponent(true);
	// gridHeaderItems.add(gridItem);
	// return gridItem;
	// }

	public void setUpCollapsedColumns() {
		return;
	}

	protected List<Object> setUpCustomVisibleColumns() {
		return null;
	}

	public void setFormClass(Class<? extends FormView> formClass) {
		this.formClass = formClass;
	}

	public void setReadonlyFormClass(Class<? extends FormView> formClass) {
		this.readonlyFormClass = formClass;
	}

	public void executePostRenderActions() throws FrameworkException {
		if (getForwardToSearchEntity() != null) {
			switchToForm(getForwardToSearchEntity(), false);
		} else {
			executeSearch();
		}
	}

	@Override
	public void executeSearch() {
		try {
			handleResetSearch();
		} catch (FrameworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void executeTab() {
		executeSearch();
	}

	protected void clearToDeleteIds() {
		if (toDeleteEntities != null) {
			toDeleteEntities.clear();
		}

		if (toDeleteItemIds != null) {
			toDeleteItemIds.clear();
		}

		manageDeleteBtn();
	}

	protected void manageDeleteBtn() {
		if (toDeleteEntities != null && !toDeleteEntities.isEmpty()) {
			actionBar.authorizeDelete();
		} else {
			actionBar.setDeleteDisabled();
		}
	}

	public void showAdvancedSearch() {
		actionBar.handleToggleAdvancedSearchClick();
	}

	// public Orientation getOrientation() {
	// return orientation;
	// }

	// public void setOrientation(Orientation orientation) {
	// this.orientation = orientation;
	// }

	public boolean isSkipActiveColumn() {
		return skipActiveColumn;
	}

	public void setSkipActiveColumn(boolean skipActiveColumn) {
		this.skipActiveColumn = skipActiveColumn;
	}

	public int getGridRowHeight() {
		return gridRowHeight;
	}

	public void setGridRowHeight(int gridRowHeight) {
		this.gridRowHeight = gridRowHeight;
	}

	// public void setGridHeightMode(HeightMode heightMode) {
	// contentGrid.setHeightMode(heightMode);
	// }

	public VerticalLayout getPreFilterLayout() {
		return preFilterLayout;
	}

	public void setPreFilterLayout(VerticalLayout preFilterLayout) {
		this.preFilterLayout = preFilterLayout;
	}

	public void addHiddenColumnId(String id) {
		hiddenColumnIds.add(id);
	}

	public void removeHiddenColumnId(String id) {
		hiddenColumnIds.remove(id);
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public VerticalLayout getFormLayout() {
		return formLayout;
	}

	public void setFormLayout(VerticalLayout formLayout) {
		this.formLayout = formLayout;
	}

	public void setUpdateObserverReferenceKey(String updateObserverReferenceKey) {
		this.updateObserverReferenceKey = updateObserverReferenceKey;
	}

	public String getUpdateObserverReferenceKey() {
		return updateObserverReferenceKey;
	}

	public <V> GridItem<T, V> addContainerProperty(ValueProvider<T, V> valueProvider, String captionKey)
			throws UnsupportedOperationException {
		GridItem<T, V> gridItem = new GridItem<T, V>(valueProvider, captionKey);
		gridHeaderItems.add(gridItem);
		return gridItem;
	}

	public <V> GridItem<T, V> addComponentContainerProperty(ValueProvider<T, V> valueProvider, String captionKey)
			throws UnsupportedOperationException {
		GridItem<T, V> gridItem = new GridItem<T, V>(valueProvider, captionKey);
		gridItem.setComponent(true);
		gridHeaderItems.add(gridItem);
		return gridItem;
	}

	public String getEditRoute() {
		return editRoute;
	}

	public void setEditRoute(String editRoute) {
		this.editRoute = editRoute;
	}

	public BehaviorSubject<Object> getLookUpSelectedObserver() {
		return lookUpSelectedObserver;
	}

	public void handleForSaveClicked() {
		// resetErrorContent();
		try {
			Boolean valid = validate();
			if (valid) {
				doForSaveByPassValidation();
			} else {
				// Notification.show(formView.getValidationMessage(),
				// Type.ERROR_MESSAGE);
			}
			actionBar.authorizeSave();
		} catch (FrameworkException e) {
			actionBar.authorizeSave();
			logger.error(e.getMessage(), e);
			updateErrorContent(e);
			// new MessageWindowHandler(e);
		}
	}

	public void doForSaveByPassValidation() throws FrameworkException {
		formUpdate();
		switch (getPopUpMode()) {
		case POPUP: {
			setPopUpEntity(entity);
			// getPopUpWindow().setData(true);
			// getPopUpWindow().close();
		}
			break;
		case TAB:
			break;
		default:
			break;
		}
		NotificationBuilder.buildSuccess(PropertyResolver.getPropertyValueByLocale(SystemProperty.UPDATE_SUCCESSFULL,
				UI.getCurrent().getLocale()));
	}

}
