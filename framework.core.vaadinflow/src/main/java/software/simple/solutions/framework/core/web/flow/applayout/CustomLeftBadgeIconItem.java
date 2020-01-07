package software.simple.solutions.framework.core.web.flow.applayout;

import com.github.appreciated.app.layout.component.menu.MenuBadgeComponent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.IronIcon;

public class CustomLeftBadgeIconItem extends CustomLeftIconItem {

	private static int idCounter = 0;

	private final MenuBadgeComponent badge;

	public CustomLeftBadgeIconItem(String caption, IronIcon icon, ComponentEventListener<ClickEvent<Button>> listener) {
		this(caption, icon);
		if (listener != null) {
			setClickListener(appMenuIconItemClickEvent -> listener.onComponentEvent(null));
		}
	}

	public CustomLeftBadgeIconItem(String name, IronIcon icon) {
		super(name, icon);
		setId("menu-btn-" + idCounter++);
		badge = new MenuBadgeComponent();
		badge.setVisible(false);
		add(badge);
	}

	public HasText getBadge() {
		return badge;
	}

}
