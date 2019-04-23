package software.simple.solutions.framework.core.pojo;

import java.io.Serializable;

public class PagingInfo implements Serializable {

	private static final long serialVersionUID = 3528604496711064250L;

	private int startPosition;
	private int maxResult;

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

}
