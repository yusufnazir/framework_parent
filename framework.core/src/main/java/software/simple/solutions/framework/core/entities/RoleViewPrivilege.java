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
import software.simple.solutions.framework.core.properties.PrivilegeProperty;
import software.simple.solutions.framework.core.properties.RoleProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.ROLE_VIEW_PRIVILEGE.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class RoleViewPrivilege extends MappedSuperClass {

	private static final long serialVersionUID = 1204818393432933738L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = RoleProperty.ID)
	@ManyToOne
	@JoinColumn(name = CxodeTables.ROLE_VIEW_PRIVILEGE.COLUMNS.ROLE_VIEW_ID_, referencedColumnName = ID_)
	private RoleView roleView;

	@FilterFieldProperty(fieldProperty = PrivilegeProperty.ID)
	@ManyToOne
	@JoinColumn(name = CxodeTables.ROLE_VIEW_PRIVILEGE.COLUMNS.PRIVILEGE_ID_, referencedColumnName = ID_)
	private Privilege privilege;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RoleView getRoleView() {
		return roleView;
	}

	public void setRoleView(RoleView roleView) {
		this.roleView = roleView;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
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
