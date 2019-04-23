package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import software.simple.solutions.framework.core.annotations.FilterFieldProperties;
import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.properties.ViewProperty;

@Deprecated
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@AuditOverride(forClass = MappedSuperClass.class)
@MappedSuperclass
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public abstract class ExtendedMappedSuperClass extends MappedSuperClass {

	private static final long serialVersionUID = -8465002399533758175L;

	@FilterFieldProperties(fieldProperties = { @FilterFieldProperty(fieldProperty = ViewProperty.CODE) })
	@Column(name = CODE_)
	private String code;

	@Column(name = NAME_)
	private String name;

	@Column(name = DESCRIPTION_)
	private String description;

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

	public String getCodeName() {
		String full = "";
		if (StringUtils.isNotBlank(code)) {
			full = code;
		}
		if (StringUtils.isNotBlank(name)) {
			if (StringUtils.isNotBlank(full)) {
				full += " - " + name;
			} else {
				full = name;
			}
		}
		return full;
	}

	@Override
	public String toString() {
		return getCodeName();
	}
}
