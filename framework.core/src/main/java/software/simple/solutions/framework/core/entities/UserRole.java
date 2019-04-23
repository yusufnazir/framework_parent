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
import software.simple.solutions.framework.core.properties.ApplicationUserProperty;
import software.simple.solutions.framework.core.properties.RoleProperty;
import software.simple.solutions.framework.core.properties.UserRoleProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.USER_ROLE.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class UserRole extends MappedSuperClass {

	private static final long serialVersionUID = 1204818393432933738L;
	public static final String USER_ = "USER_";
	public static final String ROLE_ = "ROLE_";

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@FilterFieldProperty(fieldProperty = ApplicationUserProperty.ID)
	@ManyToOne
	@JoinColumn(name = CxodeTables.USER_ROLE.COLUMNS.USER_ID, referencedColumnName = ID_)
	private ApplicationUser applicationUser;

	@FilterFieldProperty(fieldProperty = RoleProperty.ID)
	@ManyToOne
	@JoinColumn(name = CxodeTables.USER_ROLE.COLUMNS.ROLE_ID, referencedColumnName = ID_)
	private Role role;

	@FilterFieldProperty(fieldProperty = UserRoleProperty.ACTIVE)
	@Column(name = CxodeTables.USER_ROLE.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ApplicationUser getApplicationUser() {
		return applicationUser;
	}

	public void setApplicationUser(ApplicationUser applicationUser) {
		this.applicationUser = applicationUser;
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
		UserRole other = (UserRole) obj;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		return true;
	}

}
