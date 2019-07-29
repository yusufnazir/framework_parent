package software.simple.solutions.framework.core.web;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.vaadin.hene.popupbutton.PopupButton;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.Orientation;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.ActionBar;
import software.simple.solutions.framework.core.components.Build;
import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.ConfirmWindow;
import software.simple.solutions.framework.core.components.ConfirmWindow.ConfirmationHandler;
import software.simple.solutions.framework.core.components.ErrorLayout;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.GridItem;
import software.simple.solutions.framework.core.components.GridTable;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.components.ViewDetail;
import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.constants.Columns;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.IMappedSuperClass;
import software.simple.solutions.framework.core.entities.MappedSuperClass;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.excelexporter.ExportToExcel;
import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelComponentConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.ExportExcelSheetConfiguration;
import software.simple.solutions.framework.core.excelexporter.configuration.builder.ComponentHeaderConfigurationBuilder;
import software.simple.solutions.framework.core.excelexporter.configuration.builder.ExportExcelComponentConfigurationBuilder;
import software.simple.solutions.framework.core.excelexporter.configuration.builder.ExportExcelConfigurationBuilder;
import software.simple.solutions.framework.core.excelexporter.configuration.builder.ExportExcelSheetConfigurationBuilder;
import software.simple.solutions.framework.core.excelexporter.model.ExportType;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.paging.PagingBar;
import software.simple.solutions.framework.core.paging.PagingSearchEvent;
import software.simple.solutions.framework.core.pojo.PagingInfo;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.properties.AuditProperty;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.service.facade.ConfigurationServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortingHelper;
import software.simple.solutions.framework.core.valueobjects.SuperVO;
import software.simple.solutions.framework.core.web.view.AuditView;

public abstract class BasicTemplate<T> extends AbstractBaseView implements GridTable, Build {

	private static final long serialVersionUID = -4732858914162901000L;

	private static final Logger logger = LogManager.getLogger(BasicTemplate.class);

	public Grid<T> contentGrid;
	private ListDataProvider<T> dataProvider;
	private ActionBar actionBar;
	private PagingBar pagingBar;
	private HorizontalLayout topBarLayout;
	private AbstractOrderedLayout filterAndResultLayout;
	private FilterView filterView;
	private Panel filterPanel;
	private FormView formView;
	private FormView readonlyFormView;
	private Panel formPanel;
	private VerticalLayout formLayout;
	private HorizontalSplitPanel viewContentPanel;
	private Panel secondContentPanel;
	private TabSheet subTabSheet;
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
	private Set<AbstractBaseView> selectedTabs;
	private Object entity;
	private PagingSetting pagingSetting;
	private PagingResult<Object> pagingResult;
	private Orientation orientation = Orientation.VERTICAL;
	private String updateObserverReferenceKey;

	Action action_ok = new ShortcutAction("Default key", ShortcutAction.KeyCode.ENTER, null);

	private PopupButton errorPopupBtn;

	public BasicTemplate() {
		sortingMap = new ConcurrentHashMap<Object, Boolean>();
		sortingHelper = new SortingHelper();
		toDeleteEntities = new HashSet<Object>();
		toDeleteItemIds = new HashSet<Object>();
		selectedTabs = new HashSet<AbstractBaseView>();
		pagingSetting = new PagingSetting();
		gridHeaderItems = new ArrayList<GridItem>();
		hiddenColumnIds = new HashSet<String>();
		dataProvider = DataProvider.ofItems();
	}

	public void executePreBuild() throws FrameworkException {
		return;
	}

	@SuppressWarnings("unchecked")
	public void executeBuild() throws FrameworkException {
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

		addUpdateObserverReferenceKey(getEntityReferenceKey());

		getUpdateObserver().subscribe(new Consumer<Object>() {

			@Override
			public void accept(Object t) throws Exception {
				if (t instanceof MappedSuperClass) {
					Optional<T> optional = dataProvider.getItems().stream()
							.filter(p -> ((MappedSuperClass) p).getId().compareTo(((MappedSuperClass) t).getId()) == 0)
							.findFirst();
					if (optional.isPresent()) {
						dataProvider.getItems().remove(optional.get());
					}
					dataProvider.getItems().add((T) t);
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
		topBarLayout = new HorizontalLayout();
		topBarLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
		topBarLayout.addStyleName(Style.TOPBAR);
		topBarLayout.setMargin(new MarginInfo(false, false, false, false));
		topBarLayout.setWidth("100%");
		topBarLayout.setHeight("-1px");
		addComponent(topBarLayout);
		setComponentAlignment(topBarLayout, Alignment.MIDDLE_LEFT);

		preFilterLayout = new VerticalLayout();
		preFilterLayout.setVisible(false);
		addComponent(preFilterLayout);

		viewContentPanel = new HorizontalSplitPanel();
		viewContentPanel.setSizeFull();
		viewContentPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);

		if (orientation == Orientation.HORIZONTAL) {
			filterAndResultLayout = new HorizontalLayout();
		} else {
			filterAndResultLayout = new VerticalLayout();
		}
		filterAndResultLayout.setSpacing(true);
		filterAndResultLayout.setMargin(false);
		filterAndResultLayout.setWidth("100%");
		filterAndResultLayout.setHeight("100%");
		addComponent(filterAndResultLayout);

		setSizeFull();
		filterAndResultLayout.setSizeFull();
		setExpandRatio(filterAndResultLayout, 1);
		filterAndResultLayout.setSizeFull();

		addComponent(viewContentPanel);
		viewContentPanel.setVisible(false);
		setExpandRatio(viewContentPanel, 1);

		formLayout = new VerticalLayout();
		formLayout.setSpacing(false);
		formLayout.setMargin(false);
		formLayout.setWidth("100%");
		viewContentPanel.setFirstComponent(formLayout);

		secondContentPanel = new Panel();
		secondContentPanel.setSizeFull();
		viewContentPanel.setSecondComponent(secondContentPanel);
		if (secondContentPanel.getComponentCount() == 0) {
			hideSecondComponent();
		}
	}

	public void setSeconContent(Component component) {
		secondContentPanel.setContent(component);
		if (secondContentPanel.getComponentCount() != 0) {
			viewContentPanel.setSplitPosition(60);
			viewContentPanel.setLocked(false);
		}
	}

	public void resetSplitPosition() {
		setSplitPosition(60);
	}

	public void setSplitPosition(int position) {
		viewContentPanel.setSplitPosition(position);
	}

	public void hideSecondComponent() {
		viewContentPanel.setSplitPosition(100);
		viewContentPanel.setLocked(true);
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
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.addComponent(actionBar);
		horizontalLayout.setComponentAlignment(actionBar, Alignment.MIDDLE_LEFT);

		topBarLayout.addComponent(horizontalLayout);
		topBarLayout.setComponentAlignment(horizontalLayout, Alignment.MIDDLE_LEFT);

		errorPopupBtn = new PopupButton();
		errorPopupBtn.setIcon(CxodeIcons.FAIL);
		errorPopupBtn.addStyleName(Style.RESIZED_ICON_80);
		errorPopupBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		errorPopupBtn.addStyleName(ValoTheme.BUTTON_DANGER);
		errorPopupBtn.setVisible(false);

		HorizontalLayout popupLayout = new HorizontalLayout();
		errorPopupBtn.setContent(popupLayout); // Set popup content
		topBarLayout.addComponent(errorPopupBtn);
		/*
		 * example
		 */
		// Button modifyButton = new Button("Modify");
		// modifyButton.setIcon(VaadinIcons.ABACUS);
		// popupLayout.addComponent(modifyButton);
		// Button addButton = new Button("Add");
		// addButton.setIcon(VaadinIcons.ABSOLUTE_POSITION);
		// popupLayout.addComponent(addButton);
		// Button deleteButton = new Button("Delete");
		// deleteButton.setIcon(VaadinIcons.ACADEMY_CAP);
		// popupLayout.addComponent(deleteButton);
		// popupButton.setPopupVisible(true);
	}

	public void updateErrorContent(Exception e) {
		errorPopupBtn.setVisible(true);
		errorPopupBtn.setContent(new ErrorLayout(e));
		errorPopupBtn.setPopupVisible(true);
	}

	public void resetErrorContent() {
		errorPopupBtn.setVisible(false);
	}

	private void setUpPagingBar() {
		pagingBar = new PagingBar();
		topBarLayout.addComponent(pagingBar);
		topBarLayout.setComponentAlignment(pagingBar, Alignment.MIDDLE_RIGHT);

		pagingSearchEvent = new PagingSearchEvent() {

			@Override
			public void handleSearch(int currentPage, int maxResult) {

				try {
					clearToDeleteIds();
					handleViewSearch();
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					updateErrorContent(e);
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
		contentGrid.setSizeFull();
		contentGrid.setSelectionMode(SelectionMode.NONE);
		contentGrid.addStyleName("backgroundimage");

		if (isPopUpMode()) {
			Column<T, CButton> lookUpColum = contentGrid.addComponentColumn(new ValueProvider<T, CButton>() {

				@Override
				public CButton apply(T source) {

					CButton popUpBtn = new CButton();
					popUpBtn.setIcon(CxodeIcons.OK);
					popUpBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
					popUpBtn.addStyleName(ValoTheme.BUTTON_SMALL);
					popUpBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
					popUpBtn.addStyleName(Style.RESIZED_ICON);
					popUpBtn.setDescription(
							PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SELECT_LOOKUP));
					popUpBtn.addClickListener(new PopUpModeEvent(source));
					return popUpBtn;
				}
			});
			lookUpColum.setWidth(30);
			lookUpColum.setId(Columns.POPUP_ITEM_SELECTED);

			HeaderRow defaultHeaderRow = contentGrid.getDefaultHeaderRow();
			CButton popUpHeaderBtn = new CButton();
			popUpHeaderBtn.setIcon(CxodeIcons.OK);
			popUpHeaderBtn.addStyleName(Style.RESIZED_ICON_80);
			popUpHeaderBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			popUpHeaderBtn.addStyleName(Style.NO_PADDING);
			defaultHeaderRow.getCell(Columns.POPUP_ITEM_SELECTED).setStyleName(Style.GIRD_HEADER_COLUMN_CENTERED);
			defaultHeaderRow.getCell(Columns.POPUP_ITEM_SELECTED).setComponent(popUpHeaderBtn);
		} else {
			Column<T, CCheckBox> rowSelectedColumn = contentGrid.addComponentColumn(new ValueProvider<T, CCheckBox>() {

				@Override
				public CCheckBox apply(T source) {
					CCheckBox selectedChk = new CCheckBox();
					selectedChk.addValueChangeListener(new RowSelectCheckClickListener(source));
					return selectedChk;
				}
			});
			rowSelectedColumn.setWidth(25);
			rowSelectedColumn.setId(Columns.ROW_SELECTED);

			HeaderRow defaultHeaderRow = contentGrid.getDefaultHeaderRow();
			CButton rowSelectedBtn = new CButton();
			rowSelectedBtn.setIcon(CxodeIcons.CHECK_BOX);
			rowSelectedBtn.addStyleName(Style.RESIZED_ICON_80);
			rowSelectedBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
			rowSelectedBtn.addStyleName(Style.NO_PADDING);
			defaultHeaderRow.getCell(Columns.ROW_SELECTED).setStyleName(Style.GIRD_HEADER_COLUMN_CENTERED);
			defaultHeaderRow.getCell(Columns.ROW_SELECTED).setComponent(rowSelectedBtn);
		}

		Column<T, CButton> formEditorColumn = contentGrid.addComponentColumn(new ValueProvider<T, CButton>() {

			@Override
			public CButton apply(T source) {
				CButton formBtn = new CButton();
				formBtn.setIcon(CxodeIcons.VIEW);
				formBtn.setDescription(
						PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_EDIT));
				formBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
				formBtn.addStyleName(ValoTheme.BUTTON_SMALL);
				formBtn.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
				formBtn.addStyleName(Style.RESIZED_ICON);
				formBtn.addClickListener(new FormSetup(source));
				return formBtn;
			}
		});
		formEditorColumn.setWidth(30);
		formEditorColumn.setId(Columns.FORM_EDITOR);

		HeaderRow defaultHeaderRow = contentGrid.getDefaultHeaderRow();
		CButton rowSelectedBtn = new CButton();
		rowSelectedBtn.setIcon(CxodeIcons.EDIT);
		rowSelectedBtn.addStyleName(Style.RESIZED_ICON_80);
		rowSelectedBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		rowSelectedBtn.addStyleName(Style.NO_PADDING);
		defaultHeaderRow.getCell(Columns.FORM_EDITOR).setStyleName(Style.GIRD_HEADER_COLUMN_CENTERED);
		defaultHeaderRow.getCell(Columns.FORM_EDITOR).setComponent(rowSelectedBtn);

		setUpCustomColumns();

		for (GridItem gridItem : gridHeaderItems) {
			Column column = null;
			if (gridItem.isComponent()) {
				column = contentGrid.addComponentColumn(gridItem.getValueProvider());
			} else {
				column = contentGrid.addColumn(gridItem.getValueProvider());
			}
			column.setCaption(PropertyResolver.getPropertyValueByLocale(gridItem.getCaptionKey()));
			column.setId(gridItem.getCaptionKey());
			if (gridItem.getWidth() > 0) {
				column.setWidth(gridItem.getWidth());
			}
		}

		for (String hiddenColumnId : hiddenColumnIds) {
			Column<T, ?> column = contentGrid.getColumn(hiddenColumnId);
			if (column != null) {
				column.setHidden(true);
			}
		}

		if (!skipActiveColumn) {
			Column<T, CButton> activeColumn = contentGrid.addComponentColumn(new ValueProvider<T, CButton>() {

				@Override
				public CButton apply(T source) {
					CButton activeFld = new CButton();
					activeFld.addStyleName(Style.RESIZED_ICON_80);
					activeFld.addStyleName(ValoTheme.BUTTON_BORDERLESS);
					activeFld.setEnabled(false);
					if (((IMappedSuperClass) source).getActive() != null && ((IMappedSuperClass) source).getActive()) {
						activeFld.setIcon(CxodeIcons.GREEN_DOT);
					} else {
						activeFld.setIcon(CxodeIcons.RED_DOT);
					}
					return activeFld;
				}
			});
			activeColumn.setWidth(40);
			activeColumn.setId(SystemProperty.SYSTEM_ENTITY_ACTIVE);
		}

		contentGrid.setBodyRowHeight(getGridRowHeight());

		return contentGrid;
	}

	private void setUpTable() {

		contentGrid = setUpGrid();
		filterAndResultLayout.addComponent(contentGrid);
		filterAndResultLayout.setExpandRatio(contentGrid, 1f);

	}

	private void createFormView() throws FrameworkException {
		if (formClass == null) {
			return;
		}

		try {
			formView = formClass.getConstructor(new Class[] { this.getClass() }).newInstance(new Object[] { this });
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InstantiationException
				| IllegalAccessException | InvocationTargetException e) {
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}

		formView.setParentEntity(getParentEntity());
		if (getParentReferenceKey() != null) {
			formView.addToReferenceKey(getParentReferenceKey(), getParentEntity());
		}
		formView.setSelectedEntity(getSelectedEntity());
		formView.setViewDetail(getViewDetail());
		formView.executeBuild();
		formPanel = new Panel();
		formPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
		formPanel.setWidth("100%");
		formPanel.setHeight("-1px");
		// formView.setWidth("-1px");
		formPanel.setContent(formView);
		formLayout.removeAllComponents();
		formLayout.addComponentAsFirst(formPanel);
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
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}

		readonlyFormView.setParentEntity(getParentEntity());
		if (getParentReferenceKey() != null) {
			readonlyFormView.addToReferenceKey(getParentReferenceKey(), getSelectedEntity());
		}
		readonlyFormView.setSelectedEntity(getSelectedEntity());
		readonlyFormView.setViewDetail(getViewDetail());
		readonlyFormView.executeBuild();
		formPanel = new Panel();
		formPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
		formPanel.setWidth("100%");
		formPanel.setHeight("-1px");
		// formView.setWidth("-1px");
		formPanel.setContent(readonlyFormView);
		formLayout.removeAllComponents();
		formLayout.addComponentAsFirst(formPanel);
	}

	private void createSubTabs(boolean editable) throws FrameworkException {
		subTabSheet = new TabSheet();
		subTabSheet.setVisible(false);
		subTabSheet.setSizeFull();
		formLayout.addComponent(subTabSheet);
		formLayout.setExpandRatio(subTabSheet, 1);

		AuthorizedViewListHelper authorizedViewListHelper = new AuthorizedViewListHelper();
		List<SimpleSolutionsMenuItem> tabMenus = authorizedViewListHelper
				.getTabMenus(getViewDetail().getMenu().getId());

		if (tabMenus != null && !tabMenus.isEmpty()) {
			subTabSheet.setVisible(true);
			for (int i = 0; i < tabMenus.size(); i++) {
				SimpleSolutionsMenuItem viewItem = tabMenus.get(i);
				if (formView.isSubMenuValid(viewItem)) {
					AbstractBaseView subView = initSubView(viewItem);
					if (editable) {
						subView.setReferenceKeys(formView.getReferenceKeys());
					} else {
						if (readonlyFormClass != null) {
							subView.setReferenceKeys(readonlyFormView.getReferenceKeys());
						} else {
							subView.setReferenceKeys(formView.getReferenceKeys());
						}
					}

					subView.getViewDetail().setView(viewItem.getMenu().getView());
					subTabSheet.addTab(subView);
					subTabSheet.getTab(subView).setCaption(viewItem.getMenu().getName());
					if (i == 0) {
						selectTab(subView);
						subTabSheet.setSelectedTab(subView);
					}
				}
			}
		}

		subTabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {

			private static final long serialVersionUID = -2864427288443080549L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				Component component = event.getTabSheet().getSelectedTab();
				selectTab(component);
			}
		});

	}

	private void selectTab(Component component) {
		if (!BasicTemplate.this.selectedTabs.contains((AbstractBaseView) component)) {
			try {
				executeSubMenuBuild((AbstractBaseView) component);
				selectedTabs.add((AbstractBaseView) component);
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private AbstractBaseView initSubView(SimpleSolutionsMenuItem viewItem) throws FrameworkException {
		try {
			AbstractBaseView view = ViewUtil.initView(viewItem.getViewClass());

			ViewDetail viewDetail = view.getViewDetail();

			IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
			Configuration consolidateRoleConfiguration = configurationService
					.getByCode(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE);
			boolean consolidateRoles = false;
			if (consolidateRoleConfiguration != null && consolidateRoleConfiguration.getBoolean()) {
				consolidateRoles = true;
			}

			if (consolidateRoles) {
				List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
						.getPrivilegesByViewIdAndUserId(viewItem.getMenu().getView().getId(),
								getSessionHolder().getApplicationUser().getId());
				viewDetail.setPrivileges(privileges);
				viewDetail.setActionState(new ActionState(privileges));
			} else {
				List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
						.getPrivilegesByViewIdAndRoleId(viewItem.getMenu().getView().getId(),
								getSessionHolder().getSelectedRole().getId());
				viewDetail.setPrivileges(privileges);
				viewDetail.setActionState(new ActionState(privileges));
			}

			viewDetail.setMenu(viewItem.getMenu());
			viewDetail.setViewId(viewItem.getMenu().getView().getId());

			view.setViewDetail(viewDetail);
			view.setSessionHolder(getSessionHolder());
			return view;
		} catch (NullPointerException e) {
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}
	}

	private void executeSubMenuBuild(AbstractBaseView view) throws FrameworkException {
		view.setParentEntity(getSelectedEntity());
		view.executeBuild();
		view.executeTab();
		if (view instanceof BasicTemplate<?>) {
			((BasicTemplate<?>) view).setGridHeightMode(HeightMode.UNDEFINED);
		}
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
		viewContentPanel.setVisible(true);
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
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_FILTER, e);
		}
		filterView.setViewDetail(getViewDetail());
		filterView.setParentEntity(getParentEntity());
		filterView.setReferenceKeys(getReferenceKeys());
		filterView.executeBuild();
		filterPanel = new Panel();
		filterPanel.setVisible(advancedSearch);
		filterPanel.setHeight("-1px");
		filterPanel.setContent(filterView);
		filterPanel.setCaption(PropertyResolver.getPropertyValueByLocale(SystemProperty.SEARCH_FILTER));
		filterAndResultLayout.addComponentAsFirst(filterPanel);
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
			NotificationWindow.notificationNormalWindow(SystemProperty.TOTAL_RECORDS_FOUND, new Object[] { count });
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
		if (viewContentUpdated) {
			ConfirmWindow confirmWindow = new ConfirmWindow(SystemProperty.UNSAVED_CHANGES_HEADER,
					SystemProperty.UNSAVED_CHANGES_CONFIRMATION_REQUEST, SystemProperty.CONFIRM, SystemProperty.CANCEL);
			confirmWindow.execute(new ConfirmationHandler() {

				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override
				public void handlePositive() {
					doForBack();
				}

				@Override
				public void handleNegative() {
					return;
				}
			});
		} else {
			doForBack();
		}

	}

	private void doForBack() {
		formLayout.removeAllComponents();
		formLayout.setVisible(false);
		viewContentPanel.setVisible(false);
		filterAndResultLayout.setVisible(true);
		if (preFilterLayout.getComponentCount() > 0) {
			preFilterLayout.setVisible(true);
		}
		setUpActionButtons();
		formMode = false;
		setSelectedEntity(null);
	}

	private void setUpActionBarListeners() {
		actionBar.setActionSearch(new Command() {

			private static final long serialVersionUID = -4896079796358456263L;

			@Override
			public void menuSelected(MenuItem selectedMenuItem) {
				try {
					resetErrorContent();
					handleResetSearch();
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					updateErrorContent(e);
					// new MessageWindowHandler(e);
				}
			}
		});

		actionBar.setActionToggleAdvancedSearch(new Command() {

			private static final long serialVersionUID = -6459747530055805125L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				advancedSearch = !advancedSearch;
				filterPanel.setVisible(advancedSearch);
			}
		});

		actionBar.setActionBack(new Command() {

			private static final long serialVersionUID = -8021057525189685245L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				resetErrorContent();
				handleBackFromForm();
			}
		});

		actionBar.setActionErase(new Command() {

			private static final long serialVersionUID = -5291935572344424859L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				if (filterView != null) {
					filterView.erase();
				}
			}
		});

		actionBar.setActionNew(new Command() {

			private static final long serialVersionUID = 6694371324395430924L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				resetErrorContent();
				initNewForm();
				String state = UI.getCurrent().getNavigator().getState();
				Page.getCurrent().pushState(state + "/teststate");
			}
		});

		actionBar.setActionEdit(new Command() {

			private static final long serialVersionUID = -4274380478004375818L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				try {
					switchToForm(getSelectedEntity(), true);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					updateErrorContent(e);
				}
			}
		});

		actionBar.setActionCancel(new Command() {

			private static final long serialVersionUID = -4274380478004375818L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				try {
					switchToForm(getSelectedEntity(), false);
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					updateErrorContent(e);
				}
			}
		});

		actionBar.setActionSave(new Command() {

			private static final long serialVersionUID = 928103148257312770L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				resetErrorContent();
				try {
					Boolean valid = validate();
					if (valid) {
						formUpdate();
						if (isPopUpMode()) {
							setPopUpEntity(entity);
							getPopUpWindow().setData(true);
							getPopUpWindow().close();
						}
						NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
					} else {
						Notification.show(formView.getValidationMessage(), Type.ERROR_MESSAGE);
					}
					actionBar.authorizeSave();
				} catch (FrameworkException e) {
					actionBar.authorizeSave();
					logger.error(e.getMessage(), e);
					updateErrorContent(e);
					// new MessageWindowHandler(e);
				}
			}
		});

		actionBar.setActionDelete(new Command() {

			private static final long serialVersionUID = -2616773155395651641L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				resetErrorContent();
				ConfirmWindow confirmWindow = new ConfirmWindow(SystemProperty.DELETE_HEADER,
						SystemProperty.DELETE_CONFIRMATION_REQUEST, SystemProperty.CONFIRM, SystemProperty.CANCEL);
				confirmWindow.execute(new ConfirmationHandler() {

					@SuppressWarnings({ "unchecked", "rawtypes" })
					@Override
					public void handlePositive() {
						if (toDeleteEntities != null && !toDeleteEntities.isEmpty()) {
							try {
								try {
									int deleted = superService.delete(new ArrayList(toDeleteEntities));
									handleReactiveUpdate(toDeleteEntities);
								} catch (DataIntegrityViolationException e) {
									throw new FrameworkException(SystemMessageProperty.DATA_FOREIGN_KEY_CONSTRAINT, e);
								}
							} catch (FrameworkException e) {
								// logger.error(e.getMessage(), e);
								updateErrorContent(e);
							}
						} else {
							NotificationWindow.notificationNormalWindow(SystemProperty.NO_RECORD_SELECTED);
						}

						if (formMode) {
							setViewContentUpdated(false);
							handleBackFromForm();
						}
						try {
							handleResetSearch();
						} catch (FrameworkException e) {
							logger.error(e.getMessage(), e);
							updateErrorContent(e);
						}
					}

					@Override
					public void handleNegative() {
						// TODO Auto-generated method stub

					}
				});
			}
		});

		actionBar.setActionRestore(new Command() {

			private static final long serialVersionUID = 5484577968239189288L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				resetErrorContent();
				ConfirmWindow confirmWindow = new ConfirmWindow(SystemProperty.RESTORE_HEADER,
						SystemProperty.RESTORE_CONFIRMATION_REQUEST, SystemProperty.CONFIRM, SystemProperty.CANCEL);
				confirmWindow.execute(new ConfirmationHandler() {

					@Override
					public void handlePositive() {
						try {
							try {
								entity = superService.restore(entityClass, ((IMappedSuperClass) entity).getId());
								updateTabs();
								setSelectedEntity(entity);
								switchToForm(entity, false);
								NotificationWindow.notificationNormalWindow(SystemProperty.UPDATE_SUCCESSFULL);
							} catch (InvalidDataAccessApiUsageException e) {
								throw new FrameworkException(SystemMessageProperty.FAILED_TO_PERSIST, e);
							}
							actionBar.authorizeSave();
						} catch (FrameworkException e) {
							actionBar.authorizeSave();
							logger.error(e.getMessage(), e);
							updateErrorContent(e);
							// new MessageWindowHandler(e);
						}
					}

					@Override
					public void handleNegative() {
					}
				});
			}
		});

		actionBar.setActionExportToExcel(new Command() {

			private static final long serialVersionUID = -4274380478004375818L;

			@SuppressWarnings("unchecked")
			@Override
			public void menuSelected(MenuItem selectedItem) {
				resetErrorContent();
				try {
					Grid<T> grid = setUpGrid();
					Column<T, ?> formEditorColum = grid.getColumn(Columns.FORM_EDITOR);
					if (formEditorColum != null) {
						formEditorColum.setHidden(true);
					}

					Configuration configuration = ConfigurationServiceFacade.get(UI.getCurrent())
							.getByCode(Configuration.class, ConfigurationProperty.APPLICATION_EXPORT_ROW_COUNT);
					Long maxRecords = 10000L;
					if (configuration != null && StringUtils.isNotBlank(configuration.getValue())) {
						maxRecords = NumberUtil.getLong(configuration.getValue());
					}

					PagingSetting pagingSetting = new PagingSetting(0, maxRecords.intValue());
					PagingResult<?> pagingResult = superService.findBySearch(createCriteria(), pagingSetting);

					grid.setItems((Collection<T>) pagingResult.getResult());
					List<Column<T, ?>> columns = grid.getColumns();
					List<String> visibleColumns = new ArrayList<String>();
					List<String> columHeaders = new ArrayList<String>();
					HeaderRow headerRow = grid.getDefaultHeaderRow();
					for (Column<T, ?> column : columns) {
						// boolean hidden = column.isHidden();
						String id = column.getId();
						visibleColumns.add(id);
						String header = headerRow.getCell(id).getText();
						columHeaders.add(header);
					}
					visibleColumns.remove(Columns.ROW_SELECTED);
					visibleColumns.remove(Columns.FORM_EDITOR);
					visibleColumns.remove(Columns.POPUP_ITEM_SELECTED);
					visibleColumns.remove(SystemProperty.SYSTEM_ENTITY_ACTIVE);

					ExportExcelComponentConfiguration<T> componentConfig = new ExportExcelComponentConfigurationBuilder<T>()
							.withGrid(grid)
							.withVisibleProperties(visibleColumns.toArray(new String[visibleColumns.size()]))
							.withHeaderConfigs(Arrays.asList(new ComponentHeaderConfigurationBuilder()
									.withAutoFilter(true).withColumnKeys(
											columHeaders.toArray(new String[columHeaders.size()]))
									.build()))
							.build();
					ExportExcelSheetConfiguration<T> sheetConfig = new ExportExcelSheetConfigurationBuilder<T>()
							.withReportTitle("Export").withSheetName("Sheet 1")
							.withComponentConfigs(Arrays.asList(componentConfig))
							.withIsHeaderSectionRequired(Boolean.TRUE).withDateFormat("dd-MMM-yyyy").build();
					ExportExcelConfiguration<T> config = new ExportExcelConfigurationBuilder<T>()
							.withSheetConfigs(Arrays.asList(sheetConfig)).build();
					new ExportToExcel<>(ExportType.XLSX, config).export();

				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
				}
			}
		});

		actionBar.setActionAudit(new Command() {

			private static final long serialVersionUID = -6460639017503880781L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				resetErrorContent();
				try {
					Window window = new Window();
					window.setWidth("90%");
					window.setHeight("90%");
					window.center();
					window.setModal(true);
					window.setCaption(PropertyResolver.getPropertyValueByLocale(AuditProperty.AUDIT));
					AuditView auditView = new AuditView();
					auditView.setEntityClass(entityClass);
					auditView.setEntityId(((IMappedSuperClass) BasicTemplate.this.entity).getId());
					auditView.executeBuild();
					auditView.executeSearch();
					window.setContent(auditView);
					UI.getCurrent().addWindow(window);
				} catch (FrameworkException e) {
					updateErrorContent(e);
					// new MessageWindowHandler(e);
				}
			}
		});
	}

	@Override
	public boolean validate() throws FrameworkException {
		if (formView != null) {
			Boolean valid = formView.validate();
			if (!valid) {
				return false;
			}
			if (selectedTabs != null && !selectedTabs.isEmpty()) {
				for (AbstractBaseView abstractBaseView : selectedTabs) {
					valid = abstractBaseView.validate();
					if (!valid) {
						return false;
					}
				}
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

			try {
				setUpService();
				entity = superService.updateSingle(formValues);
				handleReactiveUpdate(entity);
				// updateExistingResultSet();
				updateTabs();
				setSelectedEntity(entity);
				switchToForm(entity, true);
				return entity;
			} catch (InvalidDataAccessApiUsageException | NullPointerException e) {
				throw new FrameworkException(SystemMessageProperty.FAILED_TO_PERSIST, e);
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

	public void updateTabs() throws FrameworkException {
		if (selectedTabs != null && !selectedTabs.isEmpty()) {
			for (AbstractBaseView abstractBaseView : selectedTabs) {
				abstractBaseView.formUpdate();
			}
		}
	}

	protected void initNewForm() {
		try {
			hideSecondComponent();
			secondContentPanel.setContent(null);
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
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
			updateErrorContent(e);
			// new MessageWindowHandler(e);
		}
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

	private class PopUpModeEvent implements ClickListener {

		private static final long serialVersionUID = -5165198948151324581L;
		private Object source;

		public PopUpModeEvent(Object source) {
			this.source = source;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			setPopUpEntity(source);
			getPopUpWindow().setData(true);
			getPopUpWindow().close();
		}
	}

	private class FormSetup implements ClickListener {

		private static final long serialVersionUID = -3618276831491958217L;

		private Object selectedEntity;

		public FormSetup(Object selectedEntity) {
			this.selectedEntity = selectedEntity;
		}

		@Override
		public void buttonClick(ClickEvent event) {
			setSelectedEntity(selectedEntity);
			try {
				Object entity = superService.get(entityClass, ((MappedSuperClass) selectedEntity).getId());
				setSelectedEntity(entity);
				switchToForm(entity, false);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				updateErrorContent(e);
				// new MessageWindowHandler(e);
			}
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
			if (readonlyFormClass == null) {
				actionBar.setCancelDisabled();
			}
		} else {
			actionBar.authorizeEdit();
		}

		createSubTabs(editable);
	}

	private void resetAllSelectCheckBoxes() {
		// if (toDeleteItemIds != null && !toDeleteItemIds.isEmpty()) {
		// for (Object itemId : toDeleteItemIds) {
		// CCheckBox checkBox = getContainerPropertyComponent(itemId,
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
		if (role != null) {
			if (role.getId().compareTo(1L) == 0) {
				actionBar.authorizeInfo();
				actionBar.authorizeAudit();
			}
		}

		pagingBar.setVisible(false);
	}

	protected class RowSelectCheckClickListener implements ValueChangeListener<Boolean> {

		private static final long serialVersionUID = -6792371147929564759L;

		private Object entity;

		public RowSelectCheckClickListener(Object entity) {
			this.entity = entity;
		}

		@Override
		public void valueChange(ValueChangeEvent<Boolean> event) {
			if ((boolean) event.getValue()) {
				addToDelete(entity);
			} else {
				removeFromDelete(entity);
			}
			manageDeleteBtn();
		}

	}

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

	public static BasicTemplate<?> instantiate(Class<? extends BasicTemplate<?>> basicTemplateClass)
			throws FrameworkException {
		try {
			BasicTemplate<?> basicTemplate = basicTemplateClass.newInstance();
			basicTemplate.setToPopUpMode();
			return basicTemplate;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_FILTER, e);
		}
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
		actionBar.handleSearchClick();
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

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

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

	public void setGridHeightMode(HeightMode heightMode) {
		contentGrid.setHeightMode(heightMode);
	}

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

}
