package software.simple.solutions.framework.core.web;

import java.util.List;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.BaseView;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.BrowserResizeEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.CloseOpenWindowsEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEvent.PostViewChangeEvent;
import software.simple.solutions.framework.core.events.SimpleSolutionsEventBus;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.web.view.ErrorView;

public class SimpleSolutionsNavigator extends Navigator {

	private static final long serialVersionUID = 8459601136506413574L;
	private ViewProvider errorViewProvider;

	public SimpleSolutionsNavigator(final ComponentContainer container) throws FrameworkException {
		super(UI.getCurrent(), container);

		initViewChangeListener();
		initViewProviders();

	}

	private void initViewChangeListener() {
		addViewChangeListener(new ViewChangeListener() {

			private static final long serialVersionUID = -465868412911835800L;

			@Override
			public boolean beforeViewChange(final ViewChangeEvent event) {
				// Since there's no conditions in switching between the views
				// we can always return true.
				return true;
			}

			@Override
			public void afterViewChange(final ViewChangeEvent event) {
				BaseView newView = (BaseView) event.getNewView();
				// Appropriate events get fired after the view is changed.
				SimpleSolutionsEventBus.post(new PostViewChangeEvent(newView));
				SimpleSolutionsEventBus.post(new BrowserResizeEvent());
				SimpleSolutionsEventBus.post(new CloseOpenWindowsEvent());
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initViewProviders() throws FrameworkException {

		// A dedicated view provider is added for each separate view type
		SessionHolder sessionHolder = (SessionHolder) UI.getCurrent().getData();
		List<SimpleSolutionsMenuItem> authorizedViews = sessionHolder.getAuthorizedViews();
		for (final SimpleSolutionsMenuItem viewType : authorizedViews) {
			ViewProvider viewProvider = new ClassBasedViewProvider(viewType.getMenuName(),
					(Class<? extends View>) viewType.getViewClass());
			// {
			//
			// // This field caches an already initialized view instance if the
			// // view should be cached (stateful views).
			// private View cachedInstance;
			//
			// @Override
			// public View getView(final String viewName) {
			// View result = null;
			// if (viewType.getViewName().equals(viewName)) {
			// // if (viewType.isStateful()) {
			// // // Stateful views get lazily instantiated
			// // if (cachedInstance == null) {
			// // cachedInstance =
			// // super.getView(viewType.getViewName());
			// // }
			// // result = cachedInstance;
			// // } else {
			// // Non-stateful views get instantiated every time
			// // they're navigated to
			// result = super.getView(viewType.getViewName());
			// // }
			// }
			// return result;
			// }
			// };

			addProvider(viewProvider);
		}

		errorViewProvider = new ClassBasedViewProvider("Error", ErrorView.class);
		setErrorProvider(errorViewProvider);
	}
}
