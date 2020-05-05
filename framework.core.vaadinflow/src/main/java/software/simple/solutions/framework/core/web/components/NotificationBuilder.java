package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;

public class NotificationBuilder {

	public static void buildSuccess(String message) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		Label label = new Label(message);
		notification.setPosition(Position.TOP_CENTER);
		notification.setDuration(3000);
		
		Div div = new Div();
		div.setSizeFull();
		div.add(label);
		notification.add(div);
		div.addClickListener(new ComponentEventListener<ClickEvent<Div>>() {

			private static final long serialVersionUID = -9172100613643589530L;

			@Override
			public void onComponentEvent(ClickEvent<Div> event) {
				notification.close();
			}
		});
		
		notification.open();
	}

	public static void buildError(String message) {
		Notification notification = new Notification();
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		Label label = new Label(message);
		notification.add(label);
	}

}
