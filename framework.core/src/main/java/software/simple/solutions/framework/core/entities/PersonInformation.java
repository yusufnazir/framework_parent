package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.annotations.FilterFieldProperties;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.PersonInformationProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.PERSON_INFORMATION.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class PersonInformation extends MappedSuperClass {

	private static final long serialVersionUID = -1557214385825706822L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperties(fieldProperties = {
			@FilterFieldProperty(fieldProperty = PersonInformationProperty.PERSON_FIRST_NAME, referencedFieldProperty = PersonProperty.FIRST_NAME),
			@FilterFieldProperty(fieldProperty = PersonInformationProperty.PERSON_LAST_NAME, referencedFieldProperty = PersonProperty.LAST_NAME) })
	@ManyToOne
	@JoinColumn(name = CxodeTables.PERSON_INFORMATION.COLUMNS.PERSON_ID)
	private Person person;

	@FilterFieldProperty(fieldProperty = PersonInformationProperty.PRIMARY_EMAIL)
	@Column(name = CxodeTables.PERSON_INFORMATION.COLUMNS.PRIMARY_EMAIL)
	private String primaryEmail;

	@FilterFieldProperty(fieldProperty = PersonInformationProperty.SECONDARY_EMAIL)
	@Column(name = CxodeTables.PERSON_INFORMATION.COLUMNS.SECONDARY_EMAIL)
	private String secondaryEmail;

	@Column(name = CxodeTables.PERSON_INFORMATION.COLUMNS.PRIMARY_CONTACT_NUMBER)
	private String primaryContactNumber;

	@Column(name = CxodeTables.PERSON_INFORMATION.COLUMNS.SECONDARY_CONTACT_NUMBER)
	private String secondaryContactNumber;

	@FilterFieldProperty(fieldProperty = PersonInformationProperty.ACTIVE)
	@Column(name = CxodeTables.PERSON_INFORMATION.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonInformation other = (PersonInformation) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (primaryContactNumber == null) {
			if (other.primaryContactNumber != null)
				return false;
		} else if (!primaryContactNumber.equals(other.primaryContactNumber))
			return false;
		if (primaryEmail == null) {
			if (other.primaryEmail != null)
				return false;
		} else if (!primaryEmail.equals(other.primaryEmail))
			return false;
		if (secondaryContactNumber == null) {
			if (other.secondaryContactNumber != null)
				return false;
		} else if (!secondaryContactNumber.equals(other.secondaryContactNumber))
			return false;
		if (secondaryEmail == null) {
			if (other.secondaryEmail != null)
				return false;
		} else if (!secondaryEmail.equals(other.secondaryEmail))
			return false;
		return true;
	}

}
