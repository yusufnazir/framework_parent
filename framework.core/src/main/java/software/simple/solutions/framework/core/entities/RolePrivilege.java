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
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.annotations.FilterFieldProperty;
import software.simple.solutions.framework.core.constants.CxodeTables;
import software.simple.solutions.framework.core.properties.RoleProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.ROLE_PRIVILEGE.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class RolePrivilege extends MappedSuperClass {

	private static final long serialVersionUID = 1204818393432933738L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = RoleProperty.ID)
	@ManyToOne
	@JoinColumn(name = CxodeTables.ROLE_PRIVILEGE.COLUMNS.ROLE_ID_, referencedColumnName = ID_)
	private Role role;

	@ManyToOne
	@JoinColumn(name = CxodeTables.ROLE_PRIVILEGE.COLUMNS.PRIVILEGE_ID_, referencedColumnName = ID_)
	private Privilege privilege;

	@Column(name = CxodeTables.ROLE_PRIVILEGE.COLUMNS.ACTIVE_)
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

}
