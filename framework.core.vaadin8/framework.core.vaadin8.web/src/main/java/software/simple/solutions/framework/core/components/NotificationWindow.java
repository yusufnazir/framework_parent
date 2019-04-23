package software.simple.solutions.framework.core.components;

import java.util.Locale;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.util.PropertyResolver;

public class NotificationWindow {

	public static void notificationErrorWindow(String description, Locale locale) {
		Notification notification = new Notification(
				PropertyResolver.getPropertyValueByLocale("application.name", locale), Type.ERROR_MESSAGE);
		notification.setDescription("<span>" + description + "</span>");
		notification.setHtmlContentAllowed(true);
		// notification.setStyleName("tray small closable");
		notification.setStyleName(ValoTheme.NOTIFICATION_ERROR + " " + ValoTheme.NOTIFICATION_TRAY + " "
				+ ValoTheme.NOTIFICATION_SMALL + " " + ValoTheme.NOTIFICATION_CLOSABLE);
		notification.setPosition(Position.TOP_CENTER);
		// notification.setDelayMsec(20000);
		notification.show(Page.getCurrent());
	}

	public static void notificationWarningWindow(String description, Object[] args) {
		Notification notification = new Notification(
				PropertyResolver.getPropertyValueByLocale(description, UI.getCurrent().getLocale(), args),
				Type.WARNING_MESSAGE);
		notification.setHtmlContentAllowed(true);
		notification.setPosition(Position.TOP_CENTER);
		notification.setDelayMsec(2000);
		notification.show(Page.getCurrent());
	}

	public static void notificationWarningWindow(String description) {
		notificationWarningWindow(description, null);
	}

	public static void notificationNormalWindow(String description, Object[] args) {
		Notification notification = new Notification(
				PropertyResolver.getPropertyValueByLocale(description, UI.getCurrent().getLocale(), args),
				Type.HUMANIZED_MESSAGE);
		notification.setHtmlContentAllowed(true);
		notification.setPosition(Position.TOP_CENTER);
		notification.setDelayMsec(2000);
		notification.show(Page.getCurrent());
	}

	public static void notificationNormalWindow(String description) {
		notificationNormalWindow(description, null);
	}

}
