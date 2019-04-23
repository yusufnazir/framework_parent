package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.PropertyProperty;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = CxodeTables.PROPERTY.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Property extends MappedSuperClass implements ILocalized {

	private static final long serialVersionUID = -8223719865490484691L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = PropertyProperty.KEY)
	@Column(name = CxodeTables.PROPERTY.COLUMNS.KEY)
	private String key;

	@Column(name = CxodeTables.PROPERTY.COLUMNS.ACTIVE)
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

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public void setKey(String key) {
		this.key = StringUtils.lowerCase(key);
	}

}
