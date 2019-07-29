package software.simple.solutions.framework.core.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.PersonProperty;
import software.simple.solutions.framework.core.util.DateUtil;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.PERSON.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Person extends MappedSuperClass {

	private static final long serialVersionUID = 6967413018329190354L;

	@FilterFieldProperty(fieldProperty = PersonProperty.ID)
	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.PERSON.COLUMNS.CODE)
	private String code;

	@FilterFieldProperty(fieldProperty = PersonProperty.FIRST_NAME)
	@Column(name = CxodeTables.PERSON.COLUMNS.FIRST_NAME)
	private String firstName;

	@FilterFieldProperty(fieldProperty = PersonProperty.MIDDLE_NAME)
	@Column(name = CxodeTables.PERSON.COLUMNS.MIDDLE_NAME)
	private String middleName;

	@FilterFieldProperty(fieldProperty = PersonProperty.LAST_NAME)
	@Column(name = CxodeTables.PERSON.COLUMNS.LAST_NAME)
	private String lastName;

	@Column(name = CxodeTables.PERSON.COLUMNS.DATE_OF_BIRTH)
	private LocalDate dateOfBirth;

	@Column(name = CxodeTables.PERSON.COLUMNS.PLACE_OF_BIRTH)
	private String placeOfBirth;

	@ManyToOne
	@JoinColumn(name = CxodeTables.PERSON.COLUMNS.GENDER_ID, referencedColumnName = ID_)
	private Gender gender;

	@FilterFieldProperty(fieldProperty = PersonProperty.ACTIVE)
	@Column(name = CxodeTables.PERSON.COLUMNS.ACTIVE)
	private Boolean active;

	@Transient
	private Long age;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Long getAge() {
		return DateUtil.getAge(getDateOfBirth());
	}

	@Override
	public String getCaption() {
		StringBuilder caption = new StringBuilder();
		if (code != null && code.length() > 0) {
			caption.append("[").append(code).append("]");
		}
		if (firstName != null && firstName.length() > 0) {
			caption.append(" ").append(firstName);
		}
		if (lastName != null && lastName.length() > 0) {
			caption.append(" ").append(lastName);
		}

		return caption.toString();
	}

	@Override
	public String toString() {
		return getCaption();
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
		Person other = (Person) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		if (placeOfBirth == null) {
			if (other.placeOfBirth != null)
				return false;
		} else if (!placeOfBirth.equals(other.placeOfBirth))
			return false;
		return true;
	}

}
