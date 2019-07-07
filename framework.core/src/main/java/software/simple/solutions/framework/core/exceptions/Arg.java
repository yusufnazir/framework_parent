package software.simple.solutions.framework.core.exceptions;

import java.util.ArrayList;
import java.util.List;

public class Arg {

	private List<Value> values;
	private List<Value> keyValues;
	private List<Value> normValues;

	public Arg() {
		values = new ArrayList<Arg.Value>();
		keyValues = new ArrayList<Arg.Value>();
		normValues = new ArrayList<Arg.Value>();
	}

	public void addValue(Value value) {
		values.add(value);
	}

	public Arg key(String value) {
		Key key = new Key(value);
		addValue(key);
		keyValues.add(key);
		return this;
	}

	public Arg norm(Object value) {
		Normal normal = new Normal(value);
		addValue(normal);
		normValues.add(normal);
		return this;
	}

	public List<Value> getValues() {
		return values;
	}

	public List<Value> getKeyValues() {
		return keyValues;
	}

	public List<Value> getNormValues() {
		return normValues;
	}

	public interface Value {
		<T> T getValue();

		default boolean isKey() {
			return false;
		}

	}

	public class Key implements Value {
		private String value;
		private boolean isKey;

		public Key(String value) {
			this.value = value;
			setKey(true);
		}

		@Override
		public String getValue() {
			return value;
		}

		public void setKey(boolean isKey) {
			this.isKey = isKey;
		}

		@Override
		public boolean isKey() {
			return isKey;
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
