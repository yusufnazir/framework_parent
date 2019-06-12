package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.service.IPersonService;
import software.simple.solutions.framework.core.service.ISuperService;

public class PersonServiceFacade extends SuperServiceFacade<IPersonService> implements IPersonService {

	public static final long serialVersionUID = 7157315852328697790L;

	public PersonServiceFacade(UI ui, Class<IPersonService> s) {
		super(ui, s);
	}

	public static PersonServiceFacade get(UI ui) {
		return new PersonServiceFacade(ui, IPersonService.class);
	}

}
