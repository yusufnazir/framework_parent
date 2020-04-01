package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.PersonEmergencyContactProperty;

public class PersonEmergencyContactVO extends SuperVO {

	private static final long serialVersionUID = -7920256594285433496L;

	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.PERSON_FIRST_NAME)
	private StringInterval personFirstNameInterval;
	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.PERSON_LAST_NAME)
	private StringInterval personLastNameInterval;
	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.NAME)
	private StringInterval nameInterval;
	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.RELATION_TYPE)
	private StringInterval relationshipInterval;
	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.CONTACT_NUMBER)
	private StringInterval contactNumberInterval;

	private Long id;
	private Boolean active;
	private Long personId;
	private String name;
	private Long relationTypeId;
	private String contactNumber;

	public StringInterval getPersonFirstNameInterval() {
		return personFirstNameInterval;
	}

	public void setPersonFirstNameInterval(StringInterval personFirstNameInterval) {
		this.personFirstNameInterval = personFirstNameInterval;
	}

	public StringInterval getPersonLastNameInterval() {
		return personLastNameInterval;
	}

	public void setPersonLastNameInterval(StringInterval personLastNameInterval) {
		this.personLastNameInterval = personLastNameInterval;
	}

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
	}

	public StringInterval getRelationshipInterval() {
		return relationshipInterval;
	}

	public void setRelationshipInterval(StringInterval relationshipInterval) {
		this.relationshipInterval = relationshipInterval;
	}

	public StringInterval getContactNumberInterval() {
		return contactNumberInterval;
	}

	public void setContactNumberInterval(StringInterval contactNumberInterval) {
		this.contactNumberInterval = contactNumberInterval;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getRelationTypeId() {
		return relationTypeId;
	}

	public void setRelationTypeId(Long relationTypeId) {
		this.relationTypeId = relationTypeId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
