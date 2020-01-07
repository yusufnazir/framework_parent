package software.simple.solutions.framework.core.web;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.appreciated.card.Card;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;

import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.service.facade.SuperServiceFacade;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public abstract class FormBasicTemplate extends AbstractBaseView implements Build {

	private static final long serialVersionUID = -4732858914162901000L;

	private static final Logger logger = LogManager.getLogger(FormBasicTemplate.class);

	private FormView formView;
	private Card formPanel;
	private VerticalLayout formLayout;
	private Tabs subTabSheet;

	private Class<? extends ISuperService> serviceClass;
	private Class<? extends ISuperService> facadeClass;
	private Class<? extends FormView> formClass;
	private SuperServiceFacade<?> superService;

	private boolean isNew = false;
	private Set<AbstractBaseView> selectedTab;

//	Action action_ok = new ShortcutAction("Default key", ShortcutAction.KeyCode.ENTER, null);

	public FormBasicTemplate() {
		selectedTab = new HashSet<AbstractBaseView>();
	}

	public void executeBuild() throws FrameworkException {
		executePreBuild();

		setUpTemplateLayout();

		setUpService();
		// setUpFacade();

		executePostBuild();
	}

	// private void setUpFacade() throws FrameworkException {
	// if (facadeClass != null) {
	// try {
	// superService = (ISuperService) facadeClass.newInstance();
	// } catch (InstantiationException | IllegalAccessException e) {
	// throw new
	// FrameworkException(ExceptionCodes._2_CANNOT_CREATE_FACADE_SERVICE, e);
	// }
	// }
	// }

	private void setUpTemplateLayout() {
		setSpacing(false);
		setMargin(false);
		setSizeFull();

		formLayout = new VerticalLayout();
		formLayout.setSpacing(false);
		formLayout.setMargin(false);
		formLayout.setWidth("100%");
		formLayout.setHeight("100%");
		add(formLayout);
	}

	private void createFormView() throws FrameworkException {
		if (formClass == null) {
			return;
		}
		// formView = FormView.instantiate(formClass);
		try {
			formView = formClass.getConstructor(new Class[] { this.getClass() }).newInstance(new Object[] { this });
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InstantiationException
				| IllegalAccessException | InvocationTargetException e) {
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}
		formView.setParentEntity(getParentEntity());
		formView.executeBuild();
		formPanel = new Card();
//		formPanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
		formPanel.setWidth("100%");
		formPanel.setHeight("-1px");
		formView.setWidth("-1px");
		formPanel.add(formView);
//		formLayout.removeAllComponents();
		formLayout.addComponentAsFirst(formPanel);
//		setExpandRatio(formLayout, 1);
	}

	private void createSubTabs() throws FrameworkException {
		subTabSheet = new Tabs();
		subTabSheet.setVisible(false);
		subTabSheet.setSizeFull();
		formLayout.add(subTabSheet);
//		formLayout.setExpandRatio(subTabSheet, 1);

		AuthorizedViewListHelper authorizedViewListHelper = new AuthorizedViewListHelper();
		List<SimpleSolutionsMenuItem> subMenus = authorizedViewListHelper
				.getTabMenus(getViewDetail().getMenu().getId());

//		subTabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {
//
//			private static final long serialVersionUID = -2864427288443080549L;
//
//			@Override
//			public void selectedTabChange(SelectedTabChangeEvent event) {
//				Component component = event.getTabSheet().getSelectedTab();
//				if (!FormBasicTemplate.this.selectedTab.contains((AbstractBaseView) component)) {
//					try {
//						executeSubMenuBuild((AbstractBaseView) component);
//						selectedTab.add((AbstractBaseView) component);
//					} catch (FrameworkException e) {
//						logger.error(e.getMessage(), e);
//					}
//				}
//			}
//		});

//		if (subMenus != null && !subMenus.isEmpty()) {
//			subTabSheet.setVisible(true);
//			for (int i = 0; i < subMenus.size(); i++) {
//				SimpleSolutionsMenuItem viewItem = subMenus.get(i);
//				AbstractBaseView subView = initSubView(viewItem);
//				subTabSheet.addTab(subView);
//				subTabSheet.getTab(subView).setCaption(viewItem.getMenu().getName());
//				if (i == 0) {
//					subTabSheet.setSelectedTab(subView);
//				}
//			}
//		}
	}

	private AbstractBaseView initSubView(SimpleSolutionsMenuItem viewItem) throws FrameworkException {
		try {
			AbstractBaseView view = ViewUtil.initView(viewItem.getViewClass());
			// AbstractBaseView view = (AbstractBaseView)
			// viewItem.getViewClass().newInstance();

			ViewDetail viewDetail = view.getViewDetail();
			viewDetail.setMenu(viewItem.getMenu());
			viewDetail.setViewId(viewItem.getMenu().getView().getId());

			ActionState actionState = ViewActionStateUtil.createActionState(viewDetail.getPrivileges(),
					viewItem.getMenu().getView().getId(), getSessionHolder().getApplicationUser().getId());
			viewDetail.setActionState(actionState);

			view.setViewDetail(viewDetail);
			view.setSessionHolder(getSessionHolder());
			return view;
		} catch (NullPointerException e) {
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}
	}

	private void executeSubMenuBuild(AbstractBaseView view) throws FrameworkException {
		if (view instanceof BasicTemplate) {
			view.executeBuild();
			((BasicTemplate) view).executeSearch();
		}
	}

	private void setUpFormData(Object entity) throws FrameworkException {
		formView.setFormValues(entity);
	}

	private void setFormVisibility() {
		formLayout.setVisible(true);
	}

	protected void setServiceClass(Class<? extends ISuperService> serviceClass) {
		this.serviceClass = serviceClass;
	}

	protected void setFacadeClass(Class<? extends ISuperService> facadeClass) {
		this.facadeClass = facadeClass;
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

	public Object formUpdate() throws FrameworkException {
		SuperVO formValues = (SuperVO) formView.getFormValues();
		formValues.setUpdatedBy(getSessionHolder().getApplicationUser().getId());
		formValues.setUpdatedDate(LocalDateTime.now());
		formValues.setNew(formValues.getId() == null ? true : false);
		Object updateSingle = null;
		try {
			updateSingle = superService.updateSingle(formValues);
			switchToForm(updateSingle);
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
//			new MessageWindowHandler(e);
		}
		return updateSingle;
	}

	protected void handleNewForm() {
		try {
			createFormView();
			formView.handleNewForm();
			setFormVisibility();
			isNew = true;
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
//			new MessageWindowHandler(e);
		}
	}

	public void switchToForm(Object entity) throws FrameworkException {
		createFormView();
		setUpFormData(entity);
		setFormVisibility();
		isNew = false;

		createSubTabs();
	}

	public void setFormClass(Class<? extends FormView> formClass) {
		this.formClass = formClass;
	}

	public static FormBasicTemplate instantiate(Class<? extends FormBasicTemplate> basicTemplateClass)
			throws FrameworkException {
		try {
			FormBasicTemplate basicTemplate = basicTemplateClass.newInstance();
			return basicTemplate;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}
	}

	@Override
	public void executeTab() {
		try {
			switchToForm(null);
		} catch (FrameworkException e) {
			logger.error(e.getMessage(), e);
//			new MessageWindowHandler(e);
		}
	}

}
