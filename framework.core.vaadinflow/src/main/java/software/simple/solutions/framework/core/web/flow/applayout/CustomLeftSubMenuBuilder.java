package software.simple.solutions.framework.core.web.flow.applayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.appreciated.app.layout.component.builder.AppLayoutBuilder;
import com.github.appreciated.app.layout.component.builder.ComponentBuilder;
import com.github.appreciated.app.layout.component.menu.left.LeftSubmenu;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.IronIcon;

/**
 * A Builder to build {@link LeftSubmenu} this builder is meant to be used in
 * combination with the {@link AppLayoutBuilder}
 */
public class CustomLeftSubMenuBuilder implements ComponentBuilder<CustomLeftSubmenu> {

	private final String title;
	private final IronIcon icon;
	private List<Component> components = new ArrayList<>();

	private CustomLeftSubMenuBuilder(String title, IronIcon icon) {
		this.title = title;
		this.icon = icon;
	}

	public static CustomLeftSubMenuBuilder get(String title, IronIcon icon) {
		return new CustomLeftSubMenuBuilder(title, icon);
	}

	/**
	 * returns a SubmenuBuilder with a predefined expanding element that only
	 * has a title
	 *
	 * @param title
	 * @return
	 */
	public static CustomLeftSubMenuBuilder get(String title) {
		return new CustomLeftSubMenuBuilder(title, null);
	}

	/**
	 * returns a SubmenuBuilder with a predefined expanding element that only
	 * has an icon
	 *
	 * @param icon
	 * @return
	 */
	public static CustomLeftSubMenuBuilder get(IronIcon icon) {
		return new CustomLeftSubMenuBuilder(null, icon);
	}

	/**
	 * Adds a MenuElement
	 *
	 * @param elements
	 * @return
	 */
	public CustomLeftSubMenuBuilder add(Component... elements) {
		components.addAll(Arrays.asList(elements));
		return this;
	}

	@Override
	public CustomLeftSubmenu build() {
		return new CustomLeftSubmenu(title, icon, components);
	}
}
