package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.ViewProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.VIEW.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class View extends MappedSuperClass {

	private static final long serialVersionUID = -720087732122778727L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@FilterFieldProperty(fieldProperty = ViewProperty.ID)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = ViewProperty.CODE)
	@Column(name = CxodeTables.VIEW.COLUMNS.CODE)
	private String code;

	@FilterFieldProperty(fieldProperty = ViewProperty.NAME)
	@Column(name = CxodeTables.VIEW.COLUMNS.NAME)
	private String name;

	@FilterFieldProperty(fieldProperty = ViewProperty.DESCRIPTION)
	@Column(name = CxodeTables.VIEW.COLUMNS.DESCRIPTION)
	private String description;

	@FilterFieldProperty(fieldProperty = ViewProperty.CLASS_NAME)
	@Column(name = CxodeTables.VIEW.COLUMNS.VIEW_CLASS_NAME)
	private String viewClassName;

	@FilterFieldProperty(fieldProperty = ViewProperty.ACTIVE)
	@Column(name = CxodeTables.VIEW.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getViewClassName() {
		return viewClassName;
	}

	public void setViewClassName(String viewClassName) {
		this.viewClassName = viewClassName;
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

	@Override
	public String getCaption() {
		StringBuilder caption = new StringBuilder();
		if (code != null && code.length() > 0) {
			caption.append("[").append(code).append("]");
		}
		if (name != null && name.length() > 0) {
			caption.append(" ").append(name);
		}

		return caption.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		View other = (View) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
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
		if (viewClassName == null) {
			if (other.viewClassName != null)
				return false;
		} else if (!viewClassName.equals(other.viewClassName))
			return false;
		return true;
	}

}
