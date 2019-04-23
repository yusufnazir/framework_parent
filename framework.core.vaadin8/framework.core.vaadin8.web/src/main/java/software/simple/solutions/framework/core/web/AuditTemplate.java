package software.simple.solutions.framework.core.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.Orientation;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.ActionBar;
import software.simple.solutions.framework.core.components.Build;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.components.NotificationWindow;
import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.IMappedSuperClass;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.paging.PagingBar;
import software.simple.solutions.framework.core.paging.PagingSearchEvent;
import software.simple.solutions.framework.core.pojo.PagingInfo;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.IAuditService;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SortingHelper;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@SuppressWarnings("deprecation")
public abstract class AuditTemplate extends AbstractBaseView implements Build {

	private static final long serialVersionUID = -4732858914162901000L;

	private static final Logger logger = LogManager.getLogger(AuditTemplate.class);

	private ActionBar actionBar;
	private PagingBar pagingBar;
	private HorizontalLayout topBarLayout;
	private AbstractOrderedLayout filterAndResultLayout;
	private FilterView filterView;
	private Panel filterPanel;

	private Class<? extends ISuperService> serviceClass;
	private Class<? extends FilterView> filterClass;
	private Class<?> entityClass;
	private IAuditService auditService;

	private boolean advancedSearch = true;
	private boolean columnsCreated = false;
	protected List<Object> visibleColumns;
	protected Map<Object, Boolean> sortingMap;
	protected SortingHelper sortingHelper;
	private Long entityId;

	private PagingSearchEvent pagingSearchEvent;
	private PagingSetting pagingSetting;
	private PagingResult<Object> pagingResult;
	private Orientation orientation = Orientation.VERTICAL;

	Action action_ok = new ShortcutAction("Default key", ShortcutAction.KeyCode.ENTER, null);

	public AuditTemplate() {
		sortingMap = new HashMap<Object, Boolean>();
		sortingHelper = new SortingHelper();
		pagingSetting = new PagingSetting();
		auditService = ContextProvider.getBean(IAuditService.class);
	}

	public void executePreBuild() throws FrameworkException {
		return;
	}

	public void executeBuild() throws FrameworkException {
		executePreBuild();

		setUpTemplateLayout();

		setUpService();
		setUpFilterView();

		setUpActionBar();
		setUpPagingBar();

		setUpActionButtons();

		setUpActionBarListeners();

		executePostBuild();
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
	}

	private void setUpActionBar() throws FrameworkException {
		actionBar = new ActionBar();

		ActionState actionState = ViewActionStateUtil.createActionState(getViewDetail().getPrivileges(),
				getViewDetail().getViewId(), getSessionHolder().getSelectedRole().getId());
		getViewDetail().setActionState(actionState);
		actionBar.setActionState(actionState);
		topBarLayout.addComponent(actionBar);
		topBarLayout.setComponentAlignment(actionBar, Alignment.MIDDLE_LEFT);
	}

	private void setUpPagingBar() {
		pagingBar = new PagingBar();
		topBarLayout.addComponent(pagingBar);
		topBarLayout.setComponentAlignment(pagingBar, Alignment.MIDDLE_RIGHT);

		pagingSearchEvent = new PagingSearchEvent() {

			@Override
			public void handleSearch(int currentPage, int maxResult) {

				try {
					handleViewSearch();
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
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
		actionBar.setSearchDisabled();
		actionBar.setEraseDisabled();

		if (filterClass != null) {
			actionBar.authorizeSearch();
			actionBar.authorizeErase();
		}

		pagingBar.setVisible(true);

	}

	private void setUpFilterView() throws FrameworkException {
		if (filterClass == null) {
			return;
		}
		filterView = FilterView.instantiate(filterClass);
		filterView.setParentEntity(getParentEntity());
		filterView.executeBuild();
		filterPanel = new Panel();
		filterPanel.setVisible(advancedSearch);
		filterPanel.setHeight("-1px");
		filterPanel.setContent(filterView);
		filterPanel.setCaption(PropertyResolver.getPropertyValueByLocale(SystemProperty.SEARCH_FILTER));
		filterAndResultLayout.addComponentAsFirst(filterPanel);
	}

	private void handleViewSearch() throws FrameworkException {
		pagingSetting.setMaxResult(pagingBar.getMaxResult());
		pagingSetting.setStartPosition(pagingBar.getStartPosition());
		pagingResult = auditService.createAuditQuery(entityClass, entityId, pagingSetting);
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

	private void setUpActionBarListeners() {
		actionBar.setActionSearch(new Command() {

			private static final long serialVersionUID = -4896079796358456263L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				try {
					handleResetSearch();
				} catch (FrameworkException e) {
					logger.error(e.getMessage(), e);
					new MessageWindowHandler(e);
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

		actionBar.setActionErase(new Command() {

			private static final long serialVersionUID = -5291935572344424859L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				if (filterView != null) {
					filterView.erase();
				}
			}
		});

		actionBar.setActionExportToExcel(new Command() {

			private static final long serialVersionUID = -4274380478004375818L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
			}
		});
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
		vo.setSortingHelper(sortingHelper);
		vo.setPagingInfo(pagingInfo);
		vo.setCurrentUserId(getSessionHolder().getApplicationUser().getId());
		vo.setCurrentRoleId(getSessionHolder().getSelectedRole().getId());
		if (getParentEntity() != null) {
			vo.setId(((IMappedSuperClass) getParentEntity()).getId());
		}
		return vo;
	}

	public static AuditTemplate instantiate(Class<? extends AuditTemplate> basicTemplateClass)
			throws FrameworkException {
		try {
			AuditTemplate basicTemplate = basicTemplateClass.newInstance();
			basicTemplate.setToPopUpMode();
			return basicTemplate;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_FILTER, e);
		}
	}

	public void executePostRenderActions() throws FrameworkException {
		if (getForwardToSearchEntity() != null) {
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

	public void showAdvancedSearch() {
		actionBar.handleToggleAdvancedSearchClick();
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
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
			auditService = ContextProvider.getBean(serviceClass);
		}
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public boolean isColumnsCreated() {
		return columnsCreated;
	}

	public void setColumnsCreated(boolean columnsCreated) {
		this.columnsCreated = columnsCreated;
	}

}
