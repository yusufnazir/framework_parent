package software.simple.solutions.framework.core.web.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.Page;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.MenuSelectedEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEventBus;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.SimpleSolutionsMenu;
import software.simple.solutions.framework.core.web.SimpleSolutionsMenuItem;
import software.simple.solutions.framework.core.web.ViewUtil;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the
 * left and creates a simple container for the navigator on the right.
 */
@SuppressWarnings("serial")
public class MainView extends HorizontalLayout {

	private static final Logger logger = LogManager.getLogger(MainView.class);

	private TabSheet tabSheet;

	private SessionHolder sessionHolder;

	public MainView() throws FrameworkException {
		setSizeFull();
		addStyleName("mainview");
		setSpacing(false);

		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		SimpleSolutionsEventBus.register(this);

		tabSheet = new TabSheet();
		tabSheet.setVisible(false);
		tabSheet.setSizeFull();
		tabSheet.setResponsive(true);
		tabSheet.addStyleName(ValoTheme.TABSHEET_COMPACT_TABBAR);
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		addComponent(new SimpleSolutionsMenu(tabSheet));
		addComponent(tabSheet);
		setExpandRatio(tabSheet, 1.0f);

		tabSheet.setCloseHandler(new CloseHandler() {

			@Override
			public void onTabClose(TabSheet tabsheet, Component tabContent) {
				Notification notification = new Notification(PropertyResolver.getPropertyValueByLocale(
						"system.tab.closed", getLocale(), new Object[] { tabContent.getCaption() }));
				notification.show(Page.getCurrent());
				tabsheet.removeComponent(tabContent);
				if (tabsheet.getComponentCount() > 0) {
					tabsheet.setVisible(true);
				} else {
					tabsheet.setVisible(false);
				}
			}
		});
	}

	@Subscribe
	public void updateMainTabSheetContent(final MenuSelectedEvent event) throws FrameworkException {
		SimpleSolutionsMenuItem viewItem = sessionHolder.getSimpleSolutionsMenuItem();
		AbstractBaseView view = ViewUtil.initView(viewItem, sessionHolder.getSelectedRole().getId(),
				sessionHolder.getApplicationUser().getId());
		sessionHolder.setSimpleSolutionsMenuItem(null);
		if (view == null) {
			// TODO: handle null view event.
		}

		Tab tab = tabSheet.addTab(view);
		tab.setCaption(viewItem.getMenuName());
		tab.setClosable(true);
		tabSheet.setSelectedTab(tab);
		tabSheet.setVisible(true);
	}
}
