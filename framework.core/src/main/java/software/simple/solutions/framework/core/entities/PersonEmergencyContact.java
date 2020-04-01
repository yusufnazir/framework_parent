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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.annotations.FilterFieldProperties;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.PersonEmergencyContactProperty;
import software.simple.solutions.framework.core.properties.PersonProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.PERSON_EMERGENCY_CONTACT.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class PersonEmergencyContact extends MappedSuperClass {

	private static final long serialVersionUID = -1706327040874057627L;
	private static final Logger logger = LogManager.getLogger(PersonEmergencyContact.class);

	@Id
	@TableGenerator(
			name = "table",
			table = "sequences_",
			pkColumnName = "PK_NAME",
			valueColumnName = "PK_VALUE",
			initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperties(
			fieldProperties = {
					@FilterFieldProperty(
							fieldProperty = PersonEmergencyContactProperty.PERSON_FIRST_NAME,
							referencedFieldProperty = PersonProperty.FIRST_NAME),
					@FilterFieldProperty(
							fieldProperty = PersonEmergencyContactProperty.PERSON_LAST_NAME,
							referencedFieldProperty = PersonProperty.LAST_NAME) })
	@ManyToOne
	@JoinColumn(name = CxodeTables.PERSON_EMERGENCY_CONTACT.COLUMNS.PERSON_ID)
	private Person person;

	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.NAME)
	@Column(name = CxodeTables.PERSON_EMERGENCY_CONTACT.COLUMNS.NAME)
	private String name;

	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.RELATION_TYPE)
	@ManyToOne
	@JoinColumn(name = CxodeTables.PERSON_EMERGENCY_CONTACT.COLUMNS.RELATION_TYPE_ID)
	private RelationType relationType;

	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.CONTACT_NUMBER)
	@Column(name = CxodeTables.PERSON_EMERGENCY_CONTACT.COLUMNS.CONTACT_NUMBER)
	private String contactNumber;

	@FilterFieldProperty(fieldProperty = PersonEmergencyContactProperty.ACTIVE)
	@Column(name = CxodeTables.PERSON_EMERGENCY_CONTACT.COLUMNS.ACTIVE)
	private Boolean active;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

}
