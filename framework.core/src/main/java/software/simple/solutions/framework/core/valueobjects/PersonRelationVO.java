package software.simple.solutions.framework.core.valueobjects;

import java.time.LocalDate;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.DateInterval;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.PersonRelationProperty;

public class PersonRelationVO extends SuperVO {

	private static final long serialVersionUID = -5922441227256038766L;

	private Long id;
	private Long personId;
	private Long relationId;
	private Long relationTypeId;
	private LocalDate startDate;
	private LocalDate endDate;

	@FilterFieldProperty(fieldProperty = PersonRelationProperty.FRIEND_FIRST_NAME)
	private StringInterval personFirstName;
	@FilterFieldProperty(fieldProperty = PersonRelationProperty.FRIEND_LAST_NAME)
	private StringInterval personLastName;
	@FilterFieldProperty(fieldProperty = PersonRelationProperty.REFERRER_FIRST_NAME)
	private StringInterval referrerFirstName;
	@FilterFieldProperty(fieldProperty = PersonRelationProperty.REFERRER_LAST_NAME)
	private StringInterval referrerLastName;
	@FilterFieldProperty(fieldProperty = PersonRelationProperty.START_DATE)
	private DateInterval startDateInterval;
	@FilterFieldProperty(fieldProperty = PersonRelationProperty.END_DATE)
	private DateInterval endDateInterval;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
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

	public StringInterval getPersonFirstName() {
		return personFirstName;
	}

	public void setPersonFirstName(StringInterval personFirstName) {
		this.personFirstName = personFirstName;
	}

	public StringInterval getPersonLastName() {
		return personLastName;
	}

	public void setPersonLastName(StringInterval personLastName) {
		this.personLastName = personLastName;
	}

	public StringInterval getReferrerFirstName() {
		return referrerFirstName;
	}

	public void setReferrerFirstName(StringInterval referrerFirstName) {
		this.referrerFirstName = referrerFirstName;
	}

	public StringInterval getReferrerLastName() {
		return referrerLastName;
	}

	public void setReferrerLastName(StringInterval referrerLastName) {
		this.referrerLastName = referrerLastName;
	}

	public DateInterval getStartDateInterval() {
		return startDateInterval;
	}

	public void setStartDateInterval(DateInterval startDateInterval) {
		this.startDateInterval = startDateInterval;
	}

	public DateInterval getEndDateInterval() {
		return endDateInterval;
	}

	public void setEndDateInterval(DateInterval endDateInterval) {
		this.endDateInterval = endDateInterval;
	}

	public Long getRelationTypeId() {
		return relationTypeId;
	}

	public void setRelationTypeId(Long relationTypeId) {
		this.relationTypeId = relationTypeId;
	}

}
