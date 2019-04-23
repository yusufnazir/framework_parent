package software.simple.solutions.framework.core.web.flow;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "secured/ViewA", layout = MainAppLayout.class)
public class ViewA extends VerticalLayout {

	public ViewA() {
		for (int i = 0; i < 100; i++) {
			add(new Span("a"));
		}
	}

}
