package software.simple.solutions.framework.core.events;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

import software.simple.solutions.framework.core.ui.FrameworkUI;

/**
 * A simple wrapper for Guava event bus. Defines static convenience methods for
 * relevant actions.
 */
public class SimpleSolutionsEventBus implements SubscriberExceptionHandler {

	private final EventBus eventBus = new EventBus(this);

	public static void post(final Object event) {
		FrameworkUI.getDashboardEventbus().eventBus.post(event);
	}

	public static void register(final Object object) {
		FrameworkUI.getDashboardEventbus().eventBus.register(object);
	}

	public static void unregister(final Object object) {
		FrameworkUI.getDashboardEventbus().eventBus.unregister(object);
	}

	@Override
	public final void handleException(final Throwable exception, final SubscriberExceptionContext context) {
		exception.printStackTrace();
	}
}
