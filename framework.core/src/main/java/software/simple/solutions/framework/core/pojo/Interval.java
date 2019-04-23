package software.simple.solutions.framework.core.pojo;

public interface Interval<T> {

	String getOperator();

	T getTo();

	T getFrom();
}
