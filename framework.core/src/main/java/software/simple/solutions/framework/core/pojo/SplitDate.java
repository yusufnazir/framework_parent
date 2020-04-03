package software.simple.solutions.framework.core.pojo;

public class SplitDate {

	private Long day;
	private Long month;
	private Long year;

	public SplitDate(Long year, Long month, Long day) {
		this.year = year;
		this.day = day;
		this.month = month;
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
