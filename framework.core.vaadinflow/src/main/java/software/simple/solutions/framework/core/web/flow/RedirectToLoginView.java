package software.simple.solutions.framework.core.web.flow;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "")
public class RedirectToLoginView extends Div implements BeforeEnterObserver {

	private static final long serialVersionUID = -4751602721729245311L;

	public RedirectToLoginView() {
		super();
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		event.forwardTo(LoginView.class);
	}

}
