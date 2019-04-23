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

import software.simple.solutions.framework.core.constants.CxodeTables;

@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = CxodeTables.MESSAGES_PER_LOCALE.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class MessagePerLocale extends MappedSuperClass {

	private static final long serialVersionUID = -8223719865490484691L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.MESSAGES_PER_LOCALE.COLUMNS.SUBJECT)
	private String subject;

	@Column(name = CxodeTables.MESSAGES_PER_LOCALE.COLUMNS.REASON)
	private String reason;

	@Column(name = CxodeTables.MESSAGES_PER_LOCALE.COLUMNS.REMEDY)
	private String remedy;

	@ManyToOne
	@JoinColumn(name = CxodeTables.MESSAGES_PER_LOCALE.COLUMNS.LOCALE_ID)
	private Language language;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = CxodeTables.MESSAGES_PER_LOCALE.COLUMNS.MESSAGE_ID)
	private Message message;

	@Column(name = CxodeTables.MESSAGES_PER_LOCALE.COLUMNS.ACTIVE)
	private Boolean active;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemedy() {
		return remedy;
	}

	public void setRemedy(String remedy) {
		this.remedy = remedy;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
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
		MessagePerLocale other = (MessagePerLocale) obj;
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
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (remedy == null) {
			if (other.remedy != null)
				return false;
		} else if (!remedy.equals(other.remedy))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

}
