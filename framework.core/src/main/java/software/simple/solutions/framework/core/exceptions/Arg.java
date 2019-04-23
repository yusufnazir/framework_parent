package software.simple.solutions.framework.core.exceptions;

import java.util.ArrayList;
import java.util.List;

public class Arg {

	private List<Value> values;

	public Arg() {
		values = new ArrayList<Arg.Value>();
	}

	public void addValue(Value value) {
		values.add(value);
	}

	public Arg key(String value) {
		addValue(new Key(value));
		return this;
	}

	public Arg norm(Object value) {
		addValue(new Normal(value));
		return this;
	}

	public List<Value> getValues() {
		return values;
	}

	public interface Value {
		<T> T getValue();
		
	}

	public class Key implements Value {
		private String value;

		public Key(String value) {
			this.value = value;
		}

		@Override
		public String getValue() {
			return value;
		}
	}

	public class Normal implements Value {
		private Object value;

		public Normal(Object value) {
			this.value = value;
		}

		@Override
		public Object getValue() {
			return value;
		}
	}
}
