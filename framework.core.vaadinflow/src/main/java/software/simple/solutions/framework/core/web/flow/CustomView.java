package software.simple.solutions.framework.core.web.flow;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CustomView extends VerticalLayout {

	public CustomView() {
		add(new Text("hello"));
	}
}
