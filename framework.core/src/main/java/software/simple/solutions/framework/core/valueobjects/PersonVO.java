package software.simple.solutions.framework.core.valueobjects;

import java.time.LocalDate;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.PersonProperty;

public class PersonVO extends SuperVO {

	private static final long serialVersionUID = -5922441227256038766L;

	private Long id;

	private Boolean active;

	@FilterFieldProperty(fieldProperty = PersonProperty.FIRST_NAME)
	private StringInterval firstNameInterval;
	@FilterFieldProperty(fieldProperty = PersonProperty.MIDDLE_NAME)
	private StringInterval middleNameInterval;
	@FilterFieldProperty(fieldProperty = PersonProperty.LAST_NAME)
	private StringInterval lastNameInterval;

	private String firstName;
	private String middleName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String placeOfBirth;
	private Long genderId;
	private Long personInformationId;
	private String mobileNumber;
	private String email;

	public PersonVO() {
		super();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public StringInterval getFirstNameInterval() {
		return firstNameInterval;
	}

	public void setFirstNameInterval(StringInterval firstNameInterval) {
		this.firstNameInterval = firstNameInterval;
	}

	public StringInterval getMiddleNameInterval() {
		return middleNameInterval;
	}

	public void setMiddleNameInterval(StringInterval middleNameInterval) {
		this.middleNameInterval = middleNameInterval;
	}

	public StringInterval getLastNameInterval() {
		return lastNameInterval;
	}

	public void setLastNameInterval(StringInterval lastNameInterval) {
		this.lastNameInterval = lastNameInterval;
	}

	public Long getPersonInformationId() {
		return personInformationId;
	}

	public void setPersonInformationId(Long personInformationId) {
		this.personInformationId = personInformationId;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
