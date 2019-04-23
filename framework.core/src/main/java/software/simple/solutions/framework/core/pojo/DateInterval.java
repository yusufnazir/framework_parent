package software.simple.solutions.framework.core.pojo;

import java.time.LocalDate;

public class DateInterval implements Interval<LocalDate> {

	private LocalDate from;
	private LocalDate to;
	private String operator;

	public DateInterval(LocalDate from, LocalDate to, String operator) {
		this.from = from;
		this.operator = operator;
		this.to = to;
	}

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
