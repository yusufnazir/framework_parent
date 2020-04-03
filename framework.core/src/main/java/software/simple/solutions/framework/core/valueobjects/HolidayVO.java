package software.simple.solutions.framework.core.valueobjects;

import java.time.LocalDate;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.properties.HolidayProperty;

public class HolidayVO extends SuperVO {

	private static final long serialVersionUID = -2008276944089219114L;

	private Long id;
	private String name;
	private LocalDate date;
	private Boolean active;
	
	@FilterFieldProperty(fieldProperty = HolidayProperty.DAY)
	private Long day;
	@FilterFieldProperty(fieldProperty = HolidayProperty.MONTH)
	private Long month;
	@FilterFieldProperty(fieldProperty = HolidayProperty.YEAR)
	private Long year;

	@FilterFieldProperty(fieldProperty = HolidayProperty.NAME)
	private StringInterval nameInterval;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public StringInterval getNameInterval() {
		return nameInterval;
	}

	public void setNameInterval(StringInterval nameInterval) {
		this.nameInterval = nameInterval;
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
