package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationBuilder {

	public static void buildSuccess(String message){
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		Label label = new Label(message);
		notification.add(label);
	}
	
	public static void buildError(String message){
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		Label label = new Label(message);
		notification.add(label);
	}

}
