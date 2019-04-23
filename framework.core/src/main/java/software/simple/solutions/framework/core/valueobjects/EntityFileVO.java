package software.simple.solutions.framework.core.valueobjects;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityFileVO extends SuperVO implements Serializable {

	private static final Logger logger = LogManager.getLogger(EntityFileVO.class);

	private Long id;
	private byte[] fileObject;
	private String entityId;
	private String entityName;
	private String filePath;
	private String filename;
	private Boolean active;
	private String typeOfFile;
	private boolean database = false;

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

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public Boolean getActive() {
		return active;
	}

	@Override
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeOfFile() {
		return typeOfFile;
	}

	public void setTypeOfFile(String typeOfFile) {
		this.typeOfFile = typeOfFile;
	}

	public boolean isDatabase() {
		return database;
	}

	public void setDatabase(boolean database) {
		this.database = database;
	}

}
