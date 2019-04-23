package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.PersonInformationProperty;

public class PersonInformationVO extends SuperVO {

	private static final long serialVersionUID = -7920256594285433496L;

	private Long id;
	private Boolean active;
	@FilterFieldProperty(fieldProperty = PersonInformationProperty.PRIMARY_EMAIL)
	private StringInterval emailInterval;
	@FilterFieldProperty(fieldProperty = PersonInformationProperty.PRIMARY_CONTACT_NUMBER)
	private StringInterval contactNumberInterval;
	@FilterFieldProperty(fieldProperty = PersonInformationProperty.PERSON_FIRST_NAME)
	private StringInterval personFirstNameInterval;
	@FilterFieldProperty(fieldProperty = PersonInformationProperty.PERSON_LAST_NAME)
	private StringInterval personLastNameInterval;

	private Long personId;
	private String primaryEmail;
	private String secondaryEmail;
	private String primaryContactNumber;
	private String secondaryContactNumber;

	public String getPrimaryEmail() {
		return primaryEmail;
	}

	public void setPrimaryEmail(String primaryEmail) {
		this.primaryEmail = primaryEmail;
	}

	public String getSecondaryEmail() {
		return secondaryEmail;
	}

	public void setSecondaryEmail(String secondaryEmail) {
		this.secondaryEmail = secondaryEmail;
	}

	public String getPrimaryContactNumber() {
		return primaryContactNumber;
	}

	public void setPrimaryContactNumber(String primaryContactNumber) {
		this.primaryContactNumber = primaryContactNumber;
	}

	public String getSecondaryContactNumber() {
		return secondaryContactNumber;
	}

	public void setSecondaryContactNumber(String secondaryContactNumber) {
		this.secondaryContactNumber = secondaryContactNumber;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

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

	public StringInterval getEmailInterval() {
		return emailInterval;
	}

	public void setEmailInterval(StringInterval emailInterval) {
		this.emailInterval = emailInterval;
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

	@Override
	public Boolean getActive() {
		return active;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

}
