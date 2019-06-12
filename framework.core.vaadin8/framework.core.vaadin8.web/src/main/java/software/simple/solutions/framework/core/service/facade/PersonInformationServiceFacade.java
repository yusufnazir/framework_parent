package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.entities.PersonInformation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPersonInformationService;
import software.simple.solutions.framework.core.service.ISuperService;
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
	public PersonInformation updateApplicationUserEmail(PersonInformationVO vo) throws FrameworkException {
		return service.updateApplicationUserEmail(vo);
	}

	@Override
	public PersonInformation updateApplicationUserMobileNumber(PersonInformationVO vo) throws FrameworkException {
		return service.updateApplicationUserMobileNumber(vo);
	}

	@Override
	public String getEmail(Long personId) throws FrameworkException {
		return service.getEmail(personId);
	}

}
