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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.constants.CxodeTables;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.PERSON_RELATIONS.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class PersonRelation extends MappedSuperClass {

	private static final long serialVersionUID = 6967413018329190354L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@ManyToOne
	@JoinColumn(name = CxodeTables.PERSON_RELATIONS.COLUMNS.PERSON_ID)
	private Person person;

	@ManyToOne
	@JoinColumn(name = CxodeTables.PERSON_RELATIONS.COLUMNS.RELATION_TYPE_ID)
	private RelationType relationType;

	@ManyToOne
	@JoinColumn(name = CxodeTables.PERSON_RELATIONS.COLUMNS.RELATION_ID)
	private Person relation;

	@Column(name = CxodeTables.PERSON_RELATIONS.COLUMNS.START_DATE)
	private LocalDate startDate;

	@Column(name = CxodeTables.PERSON_RELATIONS.COLUMNS.END_DATE)
	private LocalDate endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Person getRelation() {
		return relation;
	}

	public void setRelation(Person relation) {
		this.relation = relation;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public Boolean getActive() {
		return endDate == null;
	}

	@Override
	public void setActive(Boolean active) {
		// TODO Auto-generated method stub

	}

}
