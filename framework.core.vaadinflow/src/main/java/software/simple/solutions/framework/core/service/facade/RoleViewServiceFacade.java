package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.IRoleViewService;

public class RoleViewServiceFacade extends SuperServiceFacade<IRoleViewService> implements IRoleViewService {

	public static final long serialVersionUID = -7086377204053104334L;

	public RoleViewServiceFacade(UI ui, Class<IRoleViewService> s) {
		super(ui, s);
	}

	public static RoleViewServiceFacade get(UI ui) {
		return new RoleViewServiceFacade(ui, IRoleViewService.class);
	}
}
