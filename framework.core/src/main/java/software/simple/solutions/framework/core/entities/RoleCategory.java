package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.RoleCategoryProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.ROLE_CATEGORY.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class RoleCategory extends MappedSuperClass {

	private static final long serialVersionUID = -1706327040874057627L;
	private static final Logger logger = LogManager.getLogger(RoleCategory.class);

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@FilterFieldProperty(fieldProperty = RoleCategoryProperty.ID)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = RoleCategoryProperty.NAME)
	@Column(name = CxodeTables.ROLE_CATEGORY.COLUMNS.NAME)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Boolean getActive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActive(Boolean active) {
		// TODO Auto-generated method stub

	}

}
