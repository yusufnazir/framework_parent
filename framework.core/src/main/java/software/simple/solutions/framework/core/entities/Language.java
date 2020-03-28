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
import software.simple.solutions.framework.core.properties.LanguageProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.LANGUAGE.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Language extends MappedSuperClass {

	private static final long serialVersionUID = 8229639990531140751L;

	@Id
	@TableGenerator(
			name = "table",
			table = "sequences_",
			pkColumnName = "PK_NAME",
			valueColumnName = "PK_VALUE",
			initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@FilterFieldProperty(fieldProperty = LanguageProperty.ID)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = LanguageProperty.CODE)
	@Column(name = CxodeTables.LANGUAGE.COLUMNS.CODE)
	private String code;

	@FilterFieldProperty(fieldProperty = LanguageProperty.NAME)
	@Column(name = CxodeTables.LANGUAGE.COLUMNS.NAME)
	private String name;

	@FilterFieldProperty(fieldProperty = LanguageProperty.DESCRIPTION)
	@Column(name = CxodeTables.LANGUAGE.COLUMNS.DESCRIPTION)
	private String description;

	@FilterFieldProperty(fieldProperty = LanguageProperty.ACTIVE)
	@Column(name = CxodeTables.LANGUAGE.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
