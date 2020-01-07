package software.simple.solutions.framework.core.web;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;

public interface BaseView extends Build {

	Icon getIcon();

	String getViewName();

	Class<? extends Component> getViewClass();

}
