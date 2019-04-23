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
import software.simple.solutions.framework.core.properties.MailTemplateProperty;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.GENERATED_MAIL.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class GeneratedMail extends MappedSuperClass {

	private static final long serialVersionUID = -1706327040874057627L;
	private static final Logger logger = LogManager.getLogger(GeneratedMail.class);

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.GENERATED_MAIL.COLUMNS.FROM)
	private String from;

	@FilterFieldProperty(fieldProperty = MailTemplateProperty.NAME)
	@Column(name = CxodeTables.GENERATED_MAIL.COLUMNS.TO)
	private String to;

	@Column(name = CxodeTables.MAIL_TEMPLATE.COLUMNS.ACTIVE)
	private Boolean active;

	@Column(name = CxodeTables.MAIL_TEMPLATE.COLUMNS.SUBJECT)
	private String subject;

	@Column(name = CxodeTables.MAIL_TEMPLATE.COLUMNS.MESSAGE)
	private String message;

	@Column(name = CxodeTables.MAIL_TEMPLATE.COLUMNS.STATE)
	private String state;

	@Column(name = CxodeTables.MAIL_TEMPLATE.COLUMNS.RETRY_COUNT)
	private Long retryCount;

	@Column(name = CxodeTables.MAIL_TEMPLATE.COLUMNS.ERROR_MESSAGE)
	private String errorMessage;

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Long retryCount) {
		this.retryCount = retryCount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
