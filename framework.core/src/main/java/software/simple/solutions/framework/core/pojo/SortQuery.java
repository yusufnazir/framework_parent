package software.simple.solutions.framework.core.pojo;

public class SortQuery {

	private String sorted;
	private boolean sortDirection;

	public SortQuery() {
		super();
	}

	public SortQuery(String sorted, boolean sortDirection) {
		this();
		this.sorted = sorted;
		this.sortDirection = sortDirection;
	}

	public String getSorted() {
		return sorted;
	}

	public void setSorted(String sorted) {
		this.sorted = sorted;
	}

	public boolean isSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(boolean sortDirection) {
		this.sortDirection = sortDirection;
	}

}
