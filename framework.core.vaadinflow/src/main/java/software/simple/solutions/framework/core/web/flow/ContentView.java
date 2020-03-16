package software.simple.solutions.framework.core.web.flow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.web.ActionBar;
import software.simple.solutions.framework.core.web.PagingBar;
import software.simple.solutions.framework.core.web.PagingSearchEvent;

public class ContentView extends VerticalLayout implements BeforeEnterObserver {

	private static final Logger logger = LogManager.getLogger(ContentView.class);

	private ActionBar actionBar;
	private PagingBar pagingBar;
	private PagingSearchEvent pagingSearchEvent;
	private PagingSetting pagingSetting;
	private HorizontalLayout topBarLayout;

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub

	}

	public ContentView() {
		setSizeFull();
//			pagingSetting = new PagingSetting();

	}

	private void setTopBar() {
		topBarLayout = new HorizontalLayout();
		topBarLayout.setWidth("100%");
		topBarLayout.setHeight("-1px");
		add(topBarLayout);
	}

	private void setUpActionBar() throws FrameworkException {
		actionBar = new ActionBar();
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.add(actionBar);

		topBarLayout.add(horizontalLayout);
		topBarLayout.expand(horizontalLayout);

		// List<String> privileges = new ArrayList<String>();
		//
		// IConfigurationService configurationService =
		// ContextProvider.getBean(IConfigurationService.class);
		// Configuration consolidateRoleConfiguration = configurationService
		// .getByCode(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE);
		// boolean consolidateRoles = false;
		// if (consolidateRoleConfiguration != null &&
		// consolidateRoleConfiguration.getBoolean()) {
		// consolidateRoles = true;
		// }
		//
		// if (consolidateRoles) {
		// privileges =
		// RoleViewPrivilegeServiceFacade.get(UI.getCurrent()).getPrivilegesByViewIdAndUserId(
		// getViewDetail().getMenu().getView().getId(),
		// getSessionHolder().getApplicationUser().getId());
		// } else {
		// privileges =
		// RoleViewPrivilegeServiceFacade.get(UI.getCurrent()).getPrivilegesByViewIdAndRoleId(
		// getViewDetail().getMenu().getView().getId(),
		// getSessionHolder().getSelectedRole().getId());
		// }
		//
		// ActionState actionState = new ActionState(privileges);
		// getViewDetail().setActionState(actionState);
		// actionBar.setActionState(actionState);
	}

	private void setUpPagingBar() {
		pagingBar = new PagingBar();
		topBarLayout.add(pagingBar);
		topBarLayout.setJustifyContentMode(JustifyContentMode.END);

		// pagingSearchEvent = new PagingSearchEvent() {
		//
		// @Override
		// public void handleSearch(int currentPage, int maxResult) {
		//
		// try {
		// clearToDeleteIds();
		// handleViewSearch();
		// } catch (FrameworkException e) {
		// logger.error(e.getMessage(), e);
		// // updateErrorContent(e);
		// // new MessageWindowHandler(e);
		// }
		// }
		//
		// @Override
		// public Long count() throws FrameworkException {
		// return handleCount(false);
		// }
		// };
		//
		// pagingBar.addPagingSearchEvent(pagingSearchEvent);
	}

	public void setContent(HasElement content) {
		add(content.getElement().getComponent().get());
	}

}
