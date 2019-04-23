package software.simple.solutions.framework.core.pojo;

public class StringInterval implements Interval<String> {

	private String from;
	private String operator;

	public StringInterval(String from) {
		this.from = from;
	}

	public StringInterval(String from, String operator) {
		this.from = from;
		this.operator = operator;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String getTo() {
		// TODO Auto-generated method stub
		return null;
	}

}
