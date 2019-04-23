package software.simple.solutions.framework.core.pojo;

import java.math.BigDecimal;

public class DecimalInterval implements Interval<BigDecimal> {

	private BigDecimal from;
	private BigDecimal to;
	private String operator;

	public DecimalInterval(BigDecimal from, BigDecimal to, String operator) {
		this.from = from;
		this.operator = operator;
		this.to = to;
	}

	public BigDecimal getFrom() {
		return from;
	}

	public void setFrom(BigDecimal from) {
		this.from = from;
	}

	public BigDecimal getTo() {
		return to;
	}

	public void setTo(BigDecimal to) {
		this.to = to;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
