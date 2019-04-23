package software.simple.solutions.framework.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import software.simple.solutions.framework.core.constants.CxodeTables;

@Audited
@AuditOverride(forClass = MappedSuperClass.class)
@Entity
@Table(name = CxodeTables.ENTITY_FILES_.NAME)
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class EntityFile extends MappedSuperClass {

	private static final long serialVersionUID = -8974022530355774576L;

	@Id
	@TableGenerator(name = "table", table = "sequences_", pkColumnName = "PK_NAME", valueColumnName = "PK_VALUE", initialValue = 1000000)
	@GeneratedValue(generator = "table", strategy = GenerationType.TABLE)
	@Column(name = ID_)
	private Long id;

	@Column(name = CxodeTables.ENTITY_FILES_.COLUMNS.ACTIVE_)
	private Boolean active;

	@Column(name = CxodeTables.ENTITY_FILES_.COLUMNS.NAME_)
	private String name;

	@NotAudited
	@Lob
	@Column(name = CxodeTables.ENTITY_FILES_.COLUMNS.FILE_)
	private byte[] fileObject;

	@Column(name = CxodeTables.ENTITY_FILES_.COLUMNS.ENTITY_ID_)
	private String entityId;

	@Column(name = CxodeTables.ENTITY_FILES_.COLUMNS.ENTITY_NAME_)
	private String entity;

	@Column(name = CxodeTables.ENTITY_FILES_.COLUMNS.TYPE_OF_FILE_)
	private String typeOfFile;

	@Column(name = CxodeTables.ENTITY_FILES_.COLUMNS.FILE_PATH_)
	private String filePath;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getFileObject() {
		return fileObject;
	}

	public void setFileObject(byte[] fileObject) {
		this.fileObject = fileObject;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getTypeOfFile() {
		return typeOfFile;
	}

	public void setTypeOfFile(String typeOfFile) {
		this.typeOfFile = typeOfFile;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
