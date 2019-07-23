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
@Table(name = CxodeTables.RELATION_TYPE_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class RelationType extends MappedSuperClass {

	private static final long serialVersionUID = 6967413018329190354L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.RELATION_TYPE_.COLUMNS.CODE)
	private String code;

	@Column(name = CxodeTables.RELATION_TYPE_.COLUMNS.NAME)
	private String name;

	@Column(name = CxodeTables.RELATION_TYPE_.COLUMNS.REQUIRES_RELATED_TO_)
	private Boolean requiresRelatedTo;

	@Column(name = CxodeTables.RELATION_TYPE_.COLUMNS.KEY_)
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRequiresRelatedTo() {
		return requiresRelatedTo;
	}

	public void setRequiresRelatedTo(Boolean requiresRelatedTo) {
		this.requiresRelatedTo = requiresRelatedTo;
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
