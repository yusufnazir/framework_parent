package software.simple.solutions.framework.core.pojo;

import java.io.Serializable;
import java.util.Objects;

public class ComboItem implements Serializable {

	private static final long serialVersionUID = -1665500657916150291L;
	private Serializable id;
	private String code;
	private String name;

	public ComboItem() {
		super();
	}

	public ComboItem(Serializable id) {
		this.id = id;
	}

	public ComboItem(Serializable id, String name) {
		this.id = id;
		this.name = name;
	}

	public ComboItem(Serializable id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public <T> T getId() {
		return (T) id;
	}

	public Long getLongId() {
		return (Long) id;
	}

	// public Serializable getId() {
	// return id;
	// }

	public void setId(Serializable id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCaption() {
		if (code != null && name != null) {
			return "[" + code + "] " + name;
		} else if (code != null) {
			return code;
		} else if (name != null) {
			return name;
		} else {
			return id.toString();
		}
	}

	public interface Listable {

		public ComboItem getComboItem();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		ComboItem itemId = (ComboItem) obj;
		return Objects.equals(id, itemId.id) && Objects.equals(code, itemId.code) && Objects.equals(name, itemId.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
}
