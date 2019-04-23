package software.simple.solutions.framework.core.util;

public class ColumnSort {

	private String columnId;
	private String sortDirection;

	public ColumnSort() {
		super();
	}

	public ColumnSort(String columnId, String sortDirection) {
		super();
		this.columnId = columnId;
		this.sortDirection = sortDirection;
	}

	public ColumnSort(String columnId, boolean sortDirection) {
		super();
		this.columnId = columnId;
		this.sortDirection = sortDirection ? SortingHelper.ASCENDING : SortingHelper.DESCENDING;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
}
