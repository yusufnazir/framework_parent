package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import software.simple.solutions.framework.core.constants.CxodeTables;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.PRIVILEGE.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Privilege extends MappedSuperClass {

	private static final long serialVersionUID = 1204818393432933738L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.PRIVILEGE.COLUMNS.CODE_)
	private String code;

	@Column(name = CxodeTables.PRIVILEGE.COLUMNS.KEY_)
	private String key;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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
