package software.simple.solutions.framework.core.valueobjects;

import software.simple.solutions.framework.core.pojo.StringInterval;

public class PersonContactVO extends SuperVO {

	private static final long serialVersionUID = -5922441227256038766L;

	private Long id;
	private Boolean active;
	private Long personId;
	private Long streetId;
	private Long countryId;
	private String location;
	private String phoneNumber;
	private String mobileNumber;
	private String email;
	private StringInterval locationInterval;
	private StringInterval phoneNumberInterval;
	private StringInterval mobileNumberInterval;
	private StringInterval emailInterval;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public StringInterval getLocationInterval() {
		return locationInterval;
	}

	public void setLocationInterval(StringInterval locationInterval) {
		this.locationInterval = locationInterval;
	}

	public StringInterval getPhoneNumberInterval() {
		return phoneNumberInterval;
	}

	public void setPhoneNumberInterval(StringInterval phoneNumberInterval) {
		this.phoneNumberInterval = phoneNumberInterval;
	}

	public StringInterval getMobileNumberInterval() {
		return mobileNumberInterval;
	}

	public void setMobileNumberInterval(StringInterval mobileNumberInterval) {
		this.mobileNumberInterval = mobileNumberInterval;
	}

	public StringInterval getEmailInterval() {
		return emailInterval;
	}

	public void setEmailInterval(StringInterval emailInterval) {
		this.emailInterval = emailInterval;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getStreetId() {
		return streetId;
	}

	public void setStreetId(Long streetId) {
		this.streetId = streetId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

}
