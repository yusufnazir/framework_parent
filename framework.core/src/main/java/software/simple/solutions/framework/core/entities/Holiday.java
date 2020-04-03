package software.simple.solutions.framework.core.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.HolidayProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.HOLIDAY.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Holiday extends MappedSuperClass {

	private static final long serialVersionUID = 1385205519828303252L;

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

	@FilterFieldProperty(fieldProperty = HolidayProperty.NAME)
	@Column(name = CxodeTables.HOLIDAY.COLUMNS.NAME)
	private String name;

	@Column(name = CxodeTables.HOLIDAY.COLUMNS.DATE)
	private LocalDate date;

	@FilterFieldProperty(fieldProperty = HolidayProperty.DAY)
	@Column(name = CxodeTables.HOLIDAY.COLUMNS.DAY)
	private Long day;

	@FilterFieldProperty(fieldProperty = HolidayProperty.MONTH)
	@Column(name = CxodeTables.HOLIDAY.COLUMNS.MONTH)
	private Long month;

	@FilterFieldProperty(fieldProperty = HolidayProperty.YEAR)
	@Column(name = CxodeTables.HOLIDAY.COLUMNS.YEAR)
	private Long year;

	@Column(name = CxodeTables.HOLIDAY.COLUMNS.ACTIVE)
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long day) {
		this.day = day;
	}

	public Long getMonth() {
		return month;
	}

	public void setMonth(Long month) {
		this.month = month;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

}
