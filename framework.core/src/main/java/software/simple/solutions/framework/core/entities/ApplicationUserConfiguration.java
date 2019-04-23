package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import software.simple.solutions.framework.core.constants.CxodeTables;

@Entity
@Table(name = CxodeTables.APPLICATION_USER_CONFIGURATION.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class ApplicationUserConfiguration extends MappedSuperClass {

	private static final long serialVersionUID = 4166847121415359705L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@ManyToOne
	@JoinColumn(name = CxodeTables.APPLICATION_USER_CONFIGURATION.COLUMNS.APPLICATION_USER_ID, referencedColumnName = ID_)
	private ApplicationUser applicationUser;

	@Column(name = CxodeTables.APPLICATION_USER_CONFIGURATION.COLUMNS.CODE)
	private String code;

	@Column(name = CxodeTables.APPLICATION_USER_CONFIGURATION.COLUMNS.NAME)
	private String name;

	@Column(name = CxodeTables.APPLICATION_USER_CONFIGURATION.COLUMNS.DESCRIPTION)
	private String description;

	@Column(name = CxodeTables.APPLICATION_USER_CONFIGURATION.COLUMNS.VALUE)
	private String value;

	@Column(name = CxodeTables.APPLICATION_USER_CONFIGURATION.COLUMNS.BIG_VALUE)
	private String bigValue;

	@Column(name = CxodeTables.APPLICATION_USER_CONFIGURATION.COLUMNS.ACTIVE)
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

	public ApplicationUser getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationUserConfiguration other = (ApplicationUserConfiguration) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (applicationUser == null) {
			if (other.applicationUser != null)
				return false;
		} else if (!applicationUser.equals(other.applicationUser))
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
