package software.simple.solutions.framework.core.valueobjects;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Locale;

import software.simple.solutions.framework.core.pojo.PagingInfo;
import software.simple.solutions.framework.core.util.SortingHelper;

public abstract class SuperVO implements Serializable, ISuperVO {

	private static final long serialVersionUID = -9047247060128956361L;

	private LocalDateTime updatedDate;
	private Long updatedBy;
	private Long version;
	private Long propertyId;
	private Long currentUserId;
	private Long currentRoleId;
	private boolean isNew = false;
	private Locale locale;
	private Long inactiveId;
	private Long languageId;
	private String languageValue;
	private PagingInfo pagingInfo;
	private SortingHelper sortingHelper;
	private Class<?> entityClass;

	public SuperVO() {
		this.updatedDate = LocalDateTime.now();
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(Long propertyId) {
		this.propertyId = propertyId;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getInForm(Collection<?> values) {
		if (values == null || values.size() == 0) {
			return null;
		}
		Object[] objects = values.toArray();
		StringBuffer in = new StringBuffer("(");
		for (int i = 0; i < objects.length; i++) {
			in.append("'" + objects[i].toString() + "'");
			if (i < (objects.length - 1)) {
				in.append(",");
			}
		}
		in.append(")");
		return in.toString();
	}

	public Long getInactiveId() {
		return inactiveId;
	}

	public void setInactiveId(Long inactiveId) {
		this.inactiveId = inactiveId;
	}

	public Long getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}

	public String getLanguageValue() {
		return languageValue;
	}

	public void setLanguageValue(String languageValue) {
		this.languageValue = languageValue;
	}

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	public SortingHelper getSortingHelper() {
		return sortingHelper;
	}

	public void setSortingHelper(SortingHelper sortingHelper) {
		this.sortingHelper = sortingHelper;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public Long getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}

	public Long getCurrentRoleId() {
		return currentRoleId;
	}

	public void setCurrentRoleId(Long currentRoleId) {
		this.currentRoleId = currentRoleId;
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
