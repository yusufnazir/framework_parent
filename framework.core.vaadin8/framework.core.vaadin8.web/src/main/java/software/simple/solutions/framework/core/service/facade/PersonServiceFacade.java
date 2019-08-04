package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPersonService;

public class PersonServiceFacade extends SuperServiceFacade<IPersonService> implements IPersonService {

	public static final long serialVersionUID = 7157315852328697790L;

	public PersonServiceFacade(UI ui, Class<IPersonService> s) {
		super(ui, s);
	}

	public static PersonServiceFacade get(UI ui) {
		return new PersonServiceFacade(ui, IPersonService.class);
	}

	@Override
	public void updatePersonEmail(Long personId, String email) throws FrameworkException {
		service.updatePersonEmail(personId, email);
	}

	@Override
	public void updatePersonMobileNumber(Long personId, String mobileNumber) throws FrameworkException {
		service.updatePersonMobileNumber(personId, mobileNumber);
	}

	@Override
	public void createPersonImage(Person person) throws FrameworkException {
		service.createPersonImage(person);
	}

}
