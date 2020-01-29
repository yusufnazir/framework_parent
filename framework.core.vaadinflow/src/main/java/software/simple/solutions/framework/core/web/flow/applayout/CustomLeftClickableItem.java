package software.simple.solutions.framework.core.web.flow.applayout;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.IronIcon;

/**
 * A wrapper class for {@link CustomLeftClickableItem}.
 */
public class CustomLeftClickableItem extends CustomLeftBadgeIconItem {
	private final String name;
	private IronIcon icon;
	private ComponentEventListener<ClickEvent<?>> listener;

	public CustomLeftClickableItem(String name, IronIcon icon, ComponentEventListener<ClickEvent<?>> listener) {
		super(name, icon, null);
		this.name = name;
		this.icon = icon;
		this.listener = listener;
		setHighlightCondition((routerLink, event) -> false);
		setClickListener(appMenuIconItemClickEvent -> getListener().onComponentEvent(null));
	}

	public String getName() {
		return name;
	}

	public IronIcon getIcon() {
		return icon;
	}

	public ComponentEventListener<ClickEvent<?>> getListener() {
		return listener;
	}
}
