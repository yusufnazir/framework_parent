package software.simple.solutions.framework.core.constants;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

/**
 * Actionstate contains the authorization levels. This can be used per view.
 * Authorizations levels contained are, these can be derived from the mask
 * value:
 * <ul>
 * <li>Search</li>
 * <li>Insert</li>
 * <li>Update</li>
 * <li>Delete</li>
 * <li>Print</li>
 * <li>Upload document</li>
 * <li>View monetary values</li>
 * </ul>
 * <br>
 * The following values are not contained within the mask.
 * <ul>
 * <li>Cancel</li>
 * <li>Clear</li>
 * <li>Audit</li>
 * <li>Process history</li>
 * <li>Export to excel</li>
 * </ul>
 * 
 * @author yusuf
 *
 */
public class ActionState implements Serializable {

	private static final long serialVersionUID = -8041034234517054623L;
	private Boolean searchEnabled = Boolean.valueOf(false);
	private Boolean insertEnabled = Boolean.valueOf(false);
	private Boolean updateEnabled = Boolean.valueOf(false);
	private Boolean deleteEnabled = Boolean.valueOf(false);
	private Boolean reportsEnabled = Boolean.valueOf(false);
	private Boolean docEnabled = Boolean.valueOf(false);
	private Boolean financeEnabled = Boolean.valueOf(false);
	private Boolean hasAuthorizedTabs = Boolean.valueOf(false);

	// the following do not contain a mask.

	private Boolean cancelEnabled = Boolean.valueOf(false);
	private Boolean clearEnabled = Boolean.valueOf(false);
	private Boolean auditEnabled = Boolean.valueOf(false);
	private Boolean procesEnabled = Boolean.valueOf(false);
	private Boolean exportExcelEnabled = Boolean.valueOf(false);

	private List<String> privileges;

	public ActionState(List<String> privileges) {
		this.privileges = privileges;
		parseList();
	}

	private void parseList() {
		if (privileges != null && !privileges.isEmpty()) {
			privileges.parallelStream().forEach(new Consumer<String>() {

				@Override
				public void accept(String t) {
					switch (t) {
					case Privileges.SEARCH:
						setSearchEnabled(true);
						break;
					case Privileges.INSERT:
						setInsertEnabled(true);
						break;
					case Privileges.UPDATE:
						setUpdateEnabled(true);
						break;
					case Privileges.DELETE:
						setDeleteEnabled(true);
						break;
					default:
						break;
					}
				}
			});
		}
	}

	public Boolean getReportsEnabled() {
		return this.reportsEnabled;
	}

	public void setReportsEnabled(Boolean reportsEnabled) {
		this.reportsEnabled = reportsEnabled;
	}

	public Boolean getSearchEnabled() {
		return this.searchEnabled;
	}

	public void setSearchEnabled(Boolean searchEnabled) {
		this.searchEnabled = searchEnabled;
	}

	public Boolean getUpdateEnabled() {
		return updateEnabled;
	}

	public void setUpdateEnabled(Boolean updateEnabled) {
		this.updateEnabled = updateEnabled;
	}

	public Boolean getCancelEnabled() {
		return this.cancelEnabled;
	}

	public void setCancelEnabled(Boolean cancelEnabled) {
		this.cancelEnabled = cancelEnabled;
	}

	public Boolean getDeleteEnabled() {
		return this.deleteEnabled;
	}

	public void setDeleteEnabled(Boolean deleteEnabled) {
		this.deleteEnabled = deleteEnabled;
	}

	public Boolean getInsertEnabled() {
		return insertEnabled;
	}

	public void setInsertEnabled(Boolean insertEnabled) {
		this.insertEnabled = insertEnabled;
	}

	public Boolean getClearEnabled() {
		return this.clearEnabled;
	}

	public void setClearEnabled(Boolean clearEnabled) {
		this.clearEnabled = clearEnabled;
	}

	public Boolean getExportExcelEnabled() {
		return this.exportExcelEnabled;
	}

	public void setExportExcelEnabled(Boolean exportExcelEnabled) {
		this.exportExcelEnabled = exportExcelEnabled;
	}

	public Boolean getAuditEnabled() {
		return auditEnabled;
	}

	public void setAuditEnabled(Boolean auditEnabled) {
		this.auditEnabled = auditEnabled;
	}

	public Boolean getDocEnabled() {
		return docEnabled;
	}

	public void setDocEnabled(Boolean docEnabled) {
		this.docEnabled = docEnabled;
	}

	public Boolean getProcesEnabled() {
		return procesEnabled;
	}

	public void setProcesEnabled(Boolean procesEnabled) {
		this.procesEnabled = procesEnabled;
	}

	public Boolean getFinanceEnabled() {
		return financeEnabled;
	}

	public void setFinanceEnabled(Boolean financeEnabled) {
		this.financeEnabled = financeEnabled;
	}

	public Boolean getHasAuthorizedTabs() {
		return hasAuthorizedTabs;
	}

	public void setHasAuthorizedTabs(Boolean hasAuthorizedTabs) {
		this.hasAuthorizedTabs = hasAuthorizedTabs;
	}

}
