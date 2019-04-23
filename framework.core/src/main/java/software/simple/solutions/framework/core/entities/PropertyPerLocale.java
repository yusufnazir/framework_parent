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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.LanguageProperty;
import software.simple.solutions.framework.core.properties.PropertyPerLocaleProperty;
import software.simple.solutions.framework.core.properties.PropertyProperty;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = CxodeTables.PROPERTY_PER_LOCALE.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class PropertyPerLocale extends MappedSuperClass {

	private static final long serialVersionUID = -1582998911123962465L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.LANGUAGE, referencedFieldProperty = LanguageProperty.ID)
	@ManyToOne
	@JoinColumn(name = CxodeTables.PROPERTY_PER_LOCALE.COLUMNS.LOCALE_ID, referencedColumnName = ID_)
	private Language language;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.PROPERTY, referencedFieldProperty = PropertyProperty.KEY)
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = CxodeTables.PROPERTY_PER_LOCALE.COLUMNS.PROPERTY_ID)
	private Property property;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.VALUE)
	@Column(name = CxodeTables.PROPERTY_PER_LOCALE.COLUMNS.VALUE)
	private String value;

	@FilterFieldProperty(fieldProperty = PropertyPerLocaleProperty.ACTIVE)
	@Column(name = CxodeTables.PROPERTY_PER_LOCALE.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyPerLocale other = (PropertyPerLocale) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
