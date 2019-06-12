package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.service.IViewService;

public class ViewServiceFacade extends SuperServiceFacade<IViewService> implements IViewService {

	public static final long serialVersionUID = 620326602272166754L;

	public ViewServiceFacade(UI ui, Class<IViewService> s) {
		super(ui, s);
	}

	public static ViewServiceFacade get(UI ui) {
		return new ViewServiceFacade(ui, IViewService.class);
	}
}
