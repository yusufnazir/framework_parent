package software.simple.solutions.framework.core.pojo;

import java.util.Set;

public class MultiStringInterval implements Interval<Set<String>> {

	private Set<String> from;
	private String operator;

	public MultiStringInterval(Set<String> from) {
		this.from = from;
	}

	public MultiStringInterval(Set<String> from, String operator) {
		this.from = from;
		this.operator = operator;
	}

	public Set<String> getFrom() {
		return from;
	}

	public void setFrom(Set<String> from) {
		this.from = from;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public Set<String> getTo() {
		// TODO Auto-generated method stub
		return null;
	}

}
