package software.simple.solutions.framework.core.util;

import java.util.ArrayList;
import java.util.List;

public class SortingHelper {

	public static final String ASCENDING = "asc";
	public static final String DESCENDING = "desc";

	private List<ColumnSort> columnsSort;

	public SortingHelper() {
		columnsSort = new ArrayList<ColumnSort>();
	}

	public List<ColumnSort> getColumnsSort() {
		return columnsSort;
	}

	public void setColumnsSort(List<ColumnSort> columnsSort) {
		this.columnsSort = columnsSort;
	}

	public List<ColumnSort> addColumnSort(ColumnSort columnSort) {
		if (columnsSort == null) {
			columnsSort = new ArrayList<ColumnSort>();
		}
		columnsSort.add(columnSort);
		return columnsSort;
	}

	public void reset() {
		if (columnsSort == null) {
			columnsSort = new ArrayList<ColumnSort>();
		}
		columnsSort.clear();
	}
}
