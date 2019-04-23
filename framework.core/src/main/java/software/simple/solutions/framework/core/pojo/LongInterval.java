package software.simple.solutions.framework.core.pojo;

public class LongInterval implements Interval<Long> {

	private Long from;
	private Long to;
	private String operator;

	public LongInterval(Long from, Long to, String operator) {
		this.from = from;
		this.operator = operator;
		this.to = to;
	}

	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
