package software.simple.solutions.framework.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Person;
import software.simple.solutions.framework.core.entities.PersonRelation;
import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPersonRelationRepository;
import software.simple.solutions.framework.core.service.IPersonRelationService;
import software.simple.solutions.framework.core.valueobjects.PersonRelationVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IPersonRelationRepository.class)
public class PersonRelationService extends SuperService implements IPersonRelationService {

	private static final long serialVersionUID = -5418534204973107697L;
	@Autowired
	private IPersonRelationRepository personRelationRepository;

	@Override
	public PersonRelation getByPerson(Long personId, Long relationId) throws FrameworkException {
		return personRelationRepository.getByPersonAndRelation(personId, relationId);
	}

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		PersonRelationVO vo = (PersonRelationVO) valueObject;
		PersonRelation personRelation = null;
		if (vo.isNew()) {
			personRelation = new PersonRelation();
		} else {
			personRelation = get(PersonRelation.class, vo.getId());
		}

		personRelation.setPerson(get(Person.class, vo.getPersonId()));
		personRelation.setRelation(get(Person.class, vo.getRelationId()));
		personRelation.setRelationType(get(RelationType.class, vo.getRelationTypeId()));
		personRelation.setStartDate(vo.getStartDate() == null ? null : vo.getStartDate());
		personRelation.setEndDate(vo.getEndDate() == null ? null : vo.getEndDate());

		return (T) saveOrUpdate(personRelation, vo.isNew());
	}

	@Override
	public Boolean isPersonOfType(Long personId, Long relationId) throws FrameworkException {
		return personRelationRepository.isPersonOfType(personId, relationId);
	}

}
