package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.util.NumberUtil;

@Entity
@Table(name = CxodeTables.CONFIGURATION.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Configuration extends MappedSuperClass {

	private static final long serialVersionUID = 4166847121415359705L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.CONFIGURATION.COLUMNS.CODE)
	private String code;

	@Column(name = CxodeTables.CONFIGURATION.COLUMNS.NAME)
	private String name;

	@Column(name = CxodeTables.CONFIGURATION.COLUMNS.DESCRIPTION)
	private String description;

	@Column(name = CxodeTables.CONFIGURATION.COLUMNS.VALUE)
	private String value;

	@Column(name = CxodeTables.CONFIGURATION.COLUMNS.BIG_VALUE)
	private String bigValue;

	@Column(name = CxodeTables.CONFIGURATION.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBigValue() {
		return bigValue;
	}

	public void setBigValue(String bigValue) {
		this.bigValue = bigValue;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	public Long getLong() {
		Long l = NumberUtil.getLong(value);
		return (l == null ? 0L : l);
	}

	@Transient
	public Boolean getBoolean() {
		return Boolean.parseBoolean(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Configuration other = (Configuration) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (bigValue == null) {
			if (other.bigValue != null)
				return false;
		} else if (!bigValue.equals(other.bigValue))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
