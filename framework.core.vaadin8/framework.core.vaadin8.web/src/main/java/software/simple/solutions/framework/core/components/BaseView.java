package software.simple.solutions.framework.core.components;

import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public interface BaseView extends View, Build {

	Resource getIcon();

	String getViewName();

	Class<? extends View> getViewClass();

}
