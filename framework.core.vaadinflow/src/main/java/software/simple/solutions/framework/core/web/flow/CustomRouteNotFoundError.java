package software.simple.solutions.framework.core.web.flow;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.RouteNotFoundError;

//@ParentLayout(value = MainView.class)
public class CustomRouteNotFoundError 
//extends RouteNotFoundError implements BeforeEnterObserver 
{

	private static final long serialVersionUID = -2987012211920485332L;

//	@Override
//	public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter) {
//		System.out.println(parameter);
//		event.forwardTo("login");
//		// UI.getCurrent().getPage().setLocation("login");
//		// UI.getCurrent().navigate("login");
//		return HttpServletResponse.SC_ACCEPTED;
//	}
//
//	@Override
//	public void beforeEnter(BeforeEnterEvent event) {
//		// UI.getCurrent().navigate("login");
//	}
}
