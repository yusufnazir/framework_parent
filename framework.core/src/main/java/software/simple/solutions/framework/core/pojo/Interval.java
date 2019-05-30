package software.simple.solutions.framework.core.pojo;

import java.io.Serializable;

public interface Interval<T> extends Serializable {

	String getOperator();

	T getTo();

	T getFrom();
}
