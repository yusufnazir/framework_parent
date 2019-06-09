package software.simple.solutions.framework.core.pojo;

public class RevisionPojo {

	private Long revision;
	private String revisionType;
	private String revisionDate;
	private Object entity;

	public Long getRevision() {
		return revision;
	}

	public void setRevision(Long revision) {
		this.revision = revision;
	}

	public String getRevisionType() {
		return revisionType;
	}

	public void setRevisionType(String revisionType) {
		this.revisionType = revisionType;
	}

	public String getRevisionDate() {
		return revisionDate;
	}

	public void setRevisionDate(String revisionDate) {
		this.revisionDate = revisionDate;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

}
