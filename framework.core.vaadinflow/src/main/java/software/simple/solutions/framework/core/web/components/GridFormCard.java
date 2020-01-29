package software.simple.solutions.framework.core.web.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import software.simple.solutions.framework.core.web.ActionBar1;
import software.simple.solutions.framework.core.web.PagingBar;
import software.simple.solutions.framework.core.web.PagingSearchEvent;

public class GridFormCard extends Panel {

	private static final long serialVersionUID = 287442228069879390L;

	private static final Logger logger = LogManager.getLogger(GridFormCard.class);

	private Grid grid;
	private CFormLayout mainFormLayout;
	private VerticalLayout mainLayout;
	private ActionBar1 actionBar;
	private PagingBar pagingBar;
	private PagingSearchEvent pagingSearchEvent;
	private HorizontalLayout topBarLayout;

	public GridFormCard() {
		init();
	}

	private void init() {
		createTopBarLayout();

		mainLayout = new VerticalLayout();
		add(mainLayout);

		grid = new Grid();
		mainLayout.add(grid);
		mainFormLayout = new CFormLayout();
		mainLayout.add(mainFormLayout);
	}

	private void createTopBarLayout() {
		topBarLayout = new HorizontalLayout();
		topBarLayout.setWidth("100%");
		topBarLayout.setHeight("-1px");
		add(topBarLayout);

		HorizontalLayout horizontalLayout = new HorizontalLayout();
		actionBar = new ActionBar1();
		horizontalLayout.add(actionBar);

		topBarLayout.add(horizontalLayout);
		topBarLayout.expand(horizontalLayout);

		pagingBar = new PagingBar();
		topBarLayout.add(pagingBar);
		topBarLayout.setJustifyContentMode(JustifyContentMode.END);

		// pagingSearchEvent = new PagingSearchEvent() {
		//
		// @Override
		// public void handleSearch(int currentPage, int maxResult) {
		//
		// try {
		// handleViewSearch();
		// } catch (FrameworkException e) {
		// logger.error(e.getMessage(), e);
		// }
		// }
		//
		// @Override
		// public Long count() throws FrameworkException {
		// return handleCount(false);
		// }
		// };

		// pagingBar.addPagingSearchEvent(pagingSearchEvent);
	}

	public Grid getGrid() {
		return grid;
	}

	public CFormLayout getMainFormLayout() {
		return mainFormLayout;
	}

}
