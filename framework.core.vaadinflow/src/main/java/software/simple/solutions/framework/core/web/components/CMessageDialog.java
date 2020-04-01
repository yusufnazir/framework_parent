package software.simple.solutions.framework.core.web.components;

import java.util.function.Consumer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.IconFactory;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * A dialog component providing a convenient base structure that is suitable for
 * most message, confirmation, question dialogs.
 */
public class CMessageDialog extends Dialog {

	private VerticalLayout rootLayout;

	private Div header = new Div();

	private Div content = new Div();

	private Div footer = new Div();

	private ButtonBar buttonBar = new ButtonBar();

	private Hr detailsSeparator = new Hr();

	private Div details = new Div();

	public CMessageDialog() {
		setCloseOnEsc(false);
		setCloseOnOutsideClick(false);

		getElement().getStyle().set("background-color", "var(--lumo-error-color)");

		header.setVisible(false);

		rootLayout = new VerticalLayout();
		add(rootLayout);
		rootLayout.setPadding(false);

		// make the footer break out of the content area of the dialog so it
		// covers the whole bottom
		// area
		footer.getStyle().set("margin", "calc(var(--lumo-space-l) * -1)");
		footer.getStyle().set("margin-top", "var(--lumo-space-l)");
		footer.getStyle().set("width", "calc(100% + (2 * var(--lumo-space-l)))");
		footer.getStyle().set("box-sizing", "border-box");

		footer.getStyle().set("background-color", "var(--lumo-contrast-5pct)");
		footer.getStyle().set("padding", "var(--lumo-space-s) var(--lumo-space-m)");

		rootLayout.add(header, content, footer);

		detailsSeparator.setVisible(false);
		details.setVisible(false);
		details.setWidthFull();

		footer.add(buttonBar, detailsSeparator, details);
	}

	/**
	 * Sets the given text as title in the header area. Previous content of the
	 * header area is removed.
	 *
	 * @param title
	 *            the title
	 * @return this dialog
	 */
	public CMessageDialog setTitle(String title) {
		setTitle(title, (Component) null);
		return this;
	}

	/**
	 * Sets the given icon and text as title in the header area. Previous
	 * content of the header area is removed.
	 *
	 * @param title
	 *            the title
	 * @param icon
	 *            the icon
	 *
	 * @return this dialog
	 */
	public CMessageDialog setTitle(String title, Component icon) {
		getHeader().removeAll();

		getHeader().getStyle().set("display", "flex");
		getHeader().getStyle().set("align-items", "center");

		if (icon != null) {
			icon.getElement().getStyle().set("width", "var(--lumo-size-l)");
			icon.getElement().getStyle().set("height", "var(--lumo-size-l)");
			icon.getElement().getStyle().set("margin-right", "var(--lumo-space-m)");
			getHeader().add(icon);
		}

		H3 header = new H3(title);
		header.getStyle().set("margin", "0");
		header.getElement().getStyle().set("max-width", "25rem");
		header.getElement().getStyle().set("display", "inline");
		getHeader().add(header);

		return this;
	}

	/**
	 * Sets the given text as message in the content area. Previous content of
	 * the content area is removed.
	 *
	 * @param message
	 *            the message
	 * @return this dialog
	 */
	public CMessageDialog setMessage(String message) {
		getContent().removeAll();

		Div content = new Div();
//		content.setText(message);
		content.getElement().setProperty("innerHTML", message);
		content.getElement().getStyle().set("max-width", "30rem");

		getContent().add(content);

		return this;
	}

	/**
	 * Adds the header area and returns it.
	 *
	 * @return the header area
	 */
	public Div getHeader() {
		header.setVisible(true);
		return header;
	}

	/**
	 * Returns the content area.
	 *
	 * @return the content area
	 */
	public Div getContent() {
		return content;
	}

	/**
	 * Returns the footer area which contains the button bar, a separator and a
	 * details area by default; the latter two initially invisible.
	 *
	 * @return the footer area
	 */
	public Div getFooter() {
		return footer;
	}

	/**
	 * Returns the button bar.
	 *
	 * @return the button bar
	 */
	public ButtonBar getButtonBar() {
		if (!buttonBar.getParent().isPresent())
			throw new IllegalStateException("Button bar has been removed.");

		return buttonBar;
	}

	/**
	 * Adds a button to the right side of the button bar and returns it.
	 *
	 * @return the button
	 * @see ButtonBar#addButton()
	 * @see ButtonBar#add(Component)
	 */
	public FluentButton addButton() {
		return getButtonBar().addButton();
	}

	/**
	 * Adds a button to the left side of the button bar and returns it.
	 *
	 * @return the button
	 * @see ButtonBar#addButtonToLeft()
	 * @see ButtonBar#addToLeft(Component)
	 */
	public FluentButton addButtonToLeft() {
		return getButtonBar().addButtonToLeft();
	}

	/**
	 * Adds a button to the middle of the button bar and returns it.
	 *
	 * @return the button
	 * @see ButtonBar#addButtonToMiddle()
	 * @see ButtonBar#addToMiddle(Component)
	 */
	public FluentButton addButtonToMiddle() {
		return getButtonBar().addButtonToMiddle();
	}

	/**
	 * Returns the details area. It's hidden by default.
	 *
	 * @return the details area
	 * @see #setDetailsVisible(boolean)
	 */
	public Div getDetails() {
		if (!details.getParent().isPresent())
			throw new IllegalStateException("Details area has been removed.");

		return details;
	}

	/**
	 * Returns whether the details area is currently visible.
	 *
	 * @return whether the details area is currently visible
	 */
	public boolean isDetailsVisible() {
		return getDetails().isVisible();
	}

	/**
	 * Sets the visibility of the details area.
	 *
	 * @param visible
	 *            the new visibility of the details area
	 */
	public void setDetailsVisible(boolean visible) {
		getDetails().setVisible(visible);
		if (detailsSeparator != null)
			detailsSeparator.setVisible(visible);
	}

	public class ButtonBar extends Composite<HorizontalLayout> {

		private Div leftSpacer = new Div();

		private Div rightSpacer = new Div();

		@Override
		protected HorizontalLayout initContent() {
			HorizontalLayout content = super.initContent();
			content.setWidthFull();
			leftSpacer.setMinWidth("var(--lumo-space-m)");
			rightSpacer.setMinWidth("var(--lumo-space-m)");
			content.add(leftSpacer, rightSpacer);
			content.setFlexGrow(1, leftSpacer);
			content.setFlexGrow(1, rightSpacer);
			return content;
		}

		/**
		 * Adds the component to the right side of the button bar.
		 *
		 * @param <C>
		 *            the component type
		 * @param component
		 *            the component to add
		 * @return the component
		 */
		public <C extends Component> C add(C component) {
			getContent().add(component);
			return component;
		}

		/**
		 * Adds the component to the left side of the button bar.
		 *
		 * @param <C>
		 *            the component type
		 * @param component
		 *            the component to add
		 * @return the component
		 */
		public <C extends Component> C addToLeft(C component) {
			getContent().addComponentAtIndex(getContent().indexOf(leftSpacer), component);
			return component;
		}

		/**
		 * Adds the component to the middle of the button bar.
		 *
		 * @param <C>
		 *            the component type
		 * @param component
		 *            the component to add
		 * @return the component
		 */
		public <C extends Component> C addToMiddle(C component) {
			getContent().addComponentAtIndex(getContent().indexOf(rightSpacer), component);
			return component;
		}

		/**
		 * Adds a button to the right side of the button bar and returns it.
		 *
		 * @return the button
		 */
		public FluentButton addButton() {
			return new FluentButton(add(new Button()));
		}

		/**
		 * Adds a button to the left side of the button bar and returns it.
		 *
		 * @return the button
		 */
		public FluentButton addButtonToLeft() {
			return new FluentButton(addToLeft(new Button()));
		}

		/**
		 * Adds a button to the middle of the button bar and returns it.
		 *
		 * @return the button
		 */
		public FluentButton addButtonToMiddle() {
			return new FluentButton(addToMiddle(new Button()));
		}

	}

	public class FluentButton {

		private Button button;

		public FluentButton(Button button) {
			this.button = button;
		}

		public Button getButton() {
			return button;
		}

		public FluentButton text(String text) {
			button.setText(text);
			return this;
		}

		public FluentButton icon(Component icon) {
			button.setIcon(icon);
			return this;
		}

		public FluentButton icon(IconFactory icon) {
			button.setIcon(icon.create());
			return this;
		}

		public FluentButton title(String title) {
			button.getElement().setAttribute("title", title);
			return this;
		}

		public FluentButton variant(ButtonVariant variant) {
			button.addThemeVariants(variant);
			return this;
		}

		public FluentButton primary() {
			return variant(ButtonVariant.LUMO_PRIMARY);
		}

		public FluentButton tertiary() {
			return variant(ButtonVariant.LUMO_TERTIARY);
		}

		public FluentButton error() {
			return variant(ButtonVariant.LUMO_ERROR);
		}

		public FluentButton success() {
			return variant(ButtonVariant.LUMO_SUCCESS);
		}

		public FluentButton contrast() {
			return variant(ButtonVariant.LUMO_CONTRAST);
		}

		public FluentButton onClick(ComponentEventListener<ClickEvent<Button>> clickListener) {
			button.addClickListener(clickListener);
			return this;
		}

		public FluentButton clickShortcut(Key key, KeyModifier... keyModifiers) {
			button.addClickShortcut(key, keyModifiers);
			return this;
		}

		public FluentButton clickShortcutEscape() {
			return clickShortcut(Key.ESCAPE);
		}

		public FluentButton clickShortcutEnter() {
			return clickShortcut(Key.ENTER);
		}

		public FluentButton closeOnClick() {
			button.addClickListener(e -> close());
			return this;
		}

		public FluentButton toggleDetails() {
			button.addClickListener(e -> {
				setDetailsVisible(!isDetailsVisible());
			});
			return this;
		}

		public FluentButton with(Consumer<Button> configurator) {
			configurator.accept(button);
			return this;
		}

	}

}
