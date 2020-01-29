package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPrivilegeService;

public class PrivilegeServiceFacade extends SuperServiceFacade<IPrivilegeService> implements IPrivilegeService {

	public static final long serialVersionUID = 2280357556133046104L;

	public PrivilegeServiceFacade(UI ui, Class<IPrivilegeService> s) {
		super(ui, s);
	}

	public static PrivilegeServiceFacade get(UI ui) {
		return new PrivilegeServiceFacade(ui, IPrivilegeService.class);
	}

	@Override
	public List<Privilege> getPrivileges(List<String> privilegeCodes) throws FrameworkException {
		return service.getPrivileges(privilegeCodes);
	}
}
