package software.simple.solutions.framework.core.pojo;

public class PagingSetting {

	private int startPosition;
	private int maxResult;
	private Long count;
	private String query;
	private boolean doCount = false;

	public PagingSetting() {
		super();
	}

	public PagingSetting(int startPosition, int maxResult) {
		super();
		setStartPosition(startPosition);
		setMaxResult(maxResult);
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		resetDoCount();
		this.startPosition = startPosition;
		if (startPosition == 0) {
			doCount = true;
		} else if (this.startPosition != startPosition) {
			doCount = true;
		}
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
		if (this.maxResult != maxResult) {
			doCount = true;
		} else {
			resetDoCount();
		}
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public boolean isDoCount() {
		return doCount;
	}

	private void resetDoCount() {
		doCount = false;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		resetDoCount();
		if ((this.query == null && query != null) || (this.query != null && !this.query.equals(query))) {
			doCount = true;
		}
		this.query = query;
	}

}
