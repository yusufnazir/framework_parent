package software.simple.solutions.framework.core.entities;

import java.time.LocalDateTime;

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

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.APPLICATION_USER_REQUEST_PASSWORD_RESET.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class ApplicationUserRequestResetPassword extends MappedSuperClass {

	private static final long serialVersionUID = -7662467214498366631L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@FilterFieldProperty(fieldProperty = ApplicationUserProperty.ID)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.APPLICATION_USER_REQUEST_PASSWORD_RESET.COLUMNS.RESET_KEY)
	private String resetKey;

	@Column(name = CxodeTables.APPLICATION_USER_REQUEST_PASSWORD_RESET.COLUMNS.VALID_DATE_TIME)
	private LocalDateTime validateDateTime;

	@ManyToOne
	@JoinColumn(name = CxodeTables.APPLICATION_USER_REQUEST_PASSWORD_RESET.COLUMNS.APPLICATION_USER_ID, referencedColumnName = ID_)
	private ApplicationUser applicationUser;

	@Column(name = ACTIVE_)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getResetKey() {
		return resetKey;
	}

	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	public LocalDateTime getValidateDateTime() {
		return validateDateTime;
	}

	public void setValidateDateTime(LocalDateTime validateDateTime) {
		this.validateDateTime = validateDateTime;
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

}
