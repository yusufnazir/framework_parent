package software.simple.solutions.framework.core.web.flow;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.Div;

//@Route("")
// @PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project
// Base")
//@Push
//@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class ApplicationView extends Div
// implements RouterLayout
{

	private static final long serialVersionUID = 290227650255153383L;

	public ApplicationView(@Autowired MessageBean bean) {
		setWidth("100%");
		setHeight("100%");
		getElement().getStyle().set("background-image", "url('images/dynamic-style.png')");
	}

	// @Override
	// public void beforeEnter(BeforeEnterEvent event) {
	// SessionHolder sessionHolder = (SessionHolder)
	// VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
	// if (sessionHolder == null || sessionHolder.getApplicationUser() == null)
	// {
	// sessionHolder = new SessionHolder();
	// VaadinSession.getCurrent().setAttribute(Constants.SESSION_HOLDER,
	// sessionHolder);
	// event.forwardTo("login");
	// } else {
	// event.forwardTo("home");
	// }
	// }

}
