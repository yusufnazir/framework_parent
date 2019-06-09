package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import software.simple.solutions.framework.core.repository.CustomRevisionListener;

@Table(name = "revinfo")
@Entity
@RevisionEntity(CustomRevisionListener.class)
public class UserRevEntity {

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = "REV")
	@RevisionNumber
	private Long id;

	@RevisionTimestamp
	@Column(name = "REVTSTMP")
	private Long timestamp;

	@Column(name = "USERNAME")
	private String username;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
