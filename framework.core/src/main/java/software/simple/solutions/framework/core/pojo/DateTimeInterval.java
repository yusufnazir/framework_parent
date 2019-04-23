package software.simple.solutions.framework.core.pojo;

import java.time.LocalDateTime;

public class DateTimeInterval implements Interval<LocalDateTime> {

	private LocalDateTime from;
	private LocalDateTime to;
	private String operator;

	public DateTimeInterval(LocalDateTime from, LocalDateTime to, String operator) {
		this.from = from;
		this.operator = operator;
		this.to = to;
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public void setTo(LocalDateTime to) {
		this.to = to;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
