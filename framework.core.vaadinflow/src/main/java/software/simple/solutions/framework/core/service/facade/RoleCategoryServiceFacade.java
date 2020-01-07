package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.IRoleCategoryService;

public class RoleCategoryServiceFacade extends SuperServiceFacade<IRoleCategoryService>
		implements IRoleCategoryService {

	public static final long serialVersionUID = 519168034970329961L;

	public RoleCategoryServiceFacade(UI ui, Class<IRoleCategoryService> s) {
		super(ui, s);
	}

	public static RoleCategoryServiceFacade get(UI ui) {
		return new RoleCategoryServiceFacade(ui, IRoleCategoryService.class);
	}
}
