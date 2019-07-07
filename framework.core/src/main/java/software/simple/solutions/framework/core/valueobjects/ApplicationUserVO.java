package software.simple.solutions.framework.core.valueobjects;

import java.time.LocalDate;
import java.util.Date;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;

public class ApplicationUserVO extends SuperVO {

	private static final long serialVersionUID = -8890595553328976266L;

	private Long id;

	@FilterFieldProperty(fieldProperty = ApplicationUserProperty.USERNAME)
	private StringInterval usernameInterval;

	@FilterFieldProperty(fieldProperty = PersonProperty.FIRST_NAME)
	private StringInterval firstNameInterval;

	private StringInterval middleNameInterval;

	@FilterFieldProperty(fieldProperty = PersonProperty.LAST_NAME)
	private StringInterval lastNameInterval;

	@FilterFieldProperty(fieldProperty = ApplicationUserProperty.ACTIVE)
	private Boolean active;

	private Boolean useLdap;
	private String username;
	private String password;
	private String passwordConfirm;
	private Boolean resetPassword;
	private Date passwordChangeDate;
	private String alias;
	private Long personId;
	private String email;

	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String mobileNumber;
	private Long genderId;
	private Boolean termsAccepted;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(Boolean resetPassword) {
		this.resetPassword = resetPassword;
	}

	public Date getPasswordChangeDate() {
		return passwordChangeDate;
	}

	public void setPasswordChangeDate(Date passwordChangeDate) {
		this.passwordChangeDate = passwordChangeDate;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public StringInterval getUsernameInterval() {
		return usernameInterval;
	}

	public void setUsernameInterval(StringInterval usernameInterval) {
		this.usernameInterval = usernameInterval;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public Boolean getUseLdap() {
		return useLdap;
	}

	public void setUseLdap(Boolean useLdap) {
		this.useLdap = useLdap;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Long getGenderId() {
		return genderId;
	}

	public void setGenderId(Long genderId) {
		this.genderId = genderId;
	}

	public Boolean getTermsAccepted() {
		return termsAccepted;
	}

	public void setTermsAccepted(Boolean termsAccepted) {
		this.termsAccepted = termsAccepted;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
