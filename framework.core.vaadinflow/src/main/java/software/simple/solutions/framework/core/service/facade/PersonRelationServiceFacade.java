package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.PersonRelation;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IPersonRelationService;

public class PersonRelationServiceFacade extends SuperServiceFacade<IPersonRelationService>
		implements IPersonRelationService {

	public static final long serialVersionUID = -3478926743533309557L;

	public PersonRelationServiceFacade(UI ui, Class<IPersonRelationService> s) {
		super(ui, s);
	}

	public static PersonRelationServiceFacade get(UI ui) {
		return new PersonRelationServiceFacade(ui, IPersonRelationService.class);
	}

	@Override
	public PersonRelation getByPerson(Long personId, Long relationId) throws FrameworkException {
		return service.getByPerson(personId, relationId);
	}

	@Override
	public Boolean isPersonOfType(Long personId, Long relationId) throws FrameworkException {
		return service.isPersonOfType(personId, relationId);
	}

}
