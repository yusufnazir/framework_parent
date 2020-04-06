package software.simple.solutions.framework.core.web.flow;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.StreamRegistration;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.util.SessionHolder;

@Push
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@PreserveOnRefresh
@CssImport(value = "./styles/my-custom-dialog.css", themeFor = "vaadin-dialog-overlay")
@CssImport(value = "./styles/my-custom-menu-bar.css", themeFor = "vaadin-menu-bar")
@CssImport(value = "./styles/my-custom-menu-bar-button.css", themeFor = "vaadin-menu-bar-button")
@CssImport(value = "./styles/my-custom-combo-box-styles.css", themeFor = "vaadin-combo-box")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@CssImport(value = "./styles/my-custom-vaadin-sub-tab.css")
public class MainView extends Composite<VerticalLayout> implements RouterLayout {

	private static final long serialVersionUID = 8111279702273562027L;

		// @formatter:off
		private String styles = ".applayout-profile-image { "
		        + "width: 50px;"
		        + "height: 50px;"
		        + "border-radius: 50%;"
		        + "object-fit: cover;"
		        + "border: 2px solid #ffc13f;"
		        + " }"
		        + ""
		        + "vaadin-custom-field::before {"
		        	+ "content: none;"
		        + "}"
//		        + ""
//		        + "vaadin-email-field::before {"
//		        	+ "content: none;"
//		        + "}"
//		        + ""
//		        + "vaadin-text-field::before {"
//		        	+ "content: none;"
//		        + "}"
		        + ""
		        + ".my-custom-label {"
			        + "align-self: flex-start;"
			        + "color: var(--lumo-secondary-text-color);"
			        + "font-weight: 500;"
			        + "font-size: var(--lumo-font-size-s);"
			        + "margin-left: calc(var(--lumo-border-radius-m) / 4);"
			        + "transition: color 0.2s;"
			        + "line-height: 1;"
			        + "padding-bottom: 0.5em;"
			        + "overflow: hidden;"
			        + "white-space: nowrap;"
			        + "text-overflow: ellipsis;"
			        + "position: relative;"
			        + "max-width: 100%;"
			        + "box-sizing: border-box;"
			        + "padding-top: var(--lumo-space-m);"
		        + "}"
		        + ""
		        ;
		// @formatter:on

	private SessionHolder sessionHolder;
	private ContentView contentView;

	public MainView() {
		getContent().setPadding(false);
		getContent().setMargin(false);
		getContent().setSizeFull();
		getContent().getElement().getStyle().set("overflow", "auto");

		/*
		 * The code below register the style file dynamically. Normally you
		 * use @StyleSheet annotation for the component class. This way is
		 * chosen just to show the style file source code.
		 */
		StreamRegistration resource = UI.getCurrent().getSession().getResourceRegistry()
				.registerResource(new StreamResource("styles.css", () -> {
					byte[] bytes = styles.getBytes(StandardCharsets.UTF_8);
					return new ByteArrayInputStream(bytes);
				}));
		UI.getCurrent().getPage().addStyleSheet("base://" + resource.getResourceUri().toString());

		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		if (sessionHolder == null) {
			sessionHolder = new SessionHolder();
			VaadinSession.getCurrent().setAttribute(Constants.SESSION_HOLDER, sessionHolder);
		}
		contentView = new ContentView();
	}

	@Override
	public void showRouterLayoutContent(HasElement content) {
		SessionHolder sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		if (sessionHolder == null || sessionHolder.getApplicationUser() == null) {
			getContent().getElement().appendChild(content.getElement());
		} else {
			if (contentView.shouldReset()) {
				contentView.initialize();
			}
			contentView.setContent(content);
			getContent().getElement().appendChild(contentView.getElement());
		}
	}

}
