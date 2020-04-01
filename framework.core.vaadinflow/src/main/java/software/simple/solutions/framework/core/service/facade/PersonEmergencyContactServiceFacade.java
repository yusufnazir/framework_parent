package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.IPersonEmergencyContactService;

public class PersonEmergencyContactServiceFacade extends SuperServiceFacade<IPersonEmergencyContactService>
		implements IPersonEmergencyContactService {

	public static final long serialVersionUID = -3478926743533309557L;

	public PersonEmergencyContactServiceFacade(UI ui, Class<IPersonEmergencyContactService> s) {
		super(ui, s);
	}

	public static PersonEmergencyContactServiceFacade get(UI ui) {
		return new PersonEmergencyContactServiceFacade(ui, IPersonEmergencyContactService.class);
	}

}
