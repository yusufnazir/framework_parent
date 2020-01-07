package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.valueobjects.PersonInformationVO;

public class PersonInformationServiceFacade extends SuperServiceFacade<IPersonInformationService>
		implements IPersonInformationService {

	public static final long serialVersionUID = -3478926743533309557L;

	public PersonInformationServiceFacade(UI ui, Class<IPersonInformationService> s) {
		super(ui, s);
	}

	public static PersonInformationServiceFacade get(UI ui) {
		return new PersonInformationServiceFacade(ui, IPersonInformationService.class);
	}

	@Override
	public PersonInformation getByPerson(Long personId) throws FrameworkException {
		return service.getByPerson(personId);
	}

	@Override
	public PersonInformation updatePersonEmail(PersonInformationVO vo) throws FrameworkException {
		return service.updatePersonEmail(vo);
	}

	@Override
	public PersonInformation updatePersonMobileNumber(PersonInformationVO vo) throws FrameworkException {
		return service.updatePersonMobileNumber(vo);
	}

	@Override
	public String getEmail(Long personId) throws FrameworkException {
		return service.getEmail(personId);
	}

}
