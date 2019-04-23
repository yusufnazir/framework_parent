package software.simple.solutions.framework.core.pojo;

import java.util.List;

public class PagingResult<T> {

	private List<T> result;
	private Long count;
	private PagingSetting pagingSetting;

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
		if (pagingSetting != null) {
			pagingSetting.setCount(count);
		}
	}

	public PagingSetting getPagingSetting() {
		return pagingSetting;
	}

	public void setPagingSetting(PagingSetting pagingSetting) {
		this.pagingSetting = pagingSetting;
		if (pagingSetting != null) {
			count = pagingSetting.getCount();
		}
	}

}
