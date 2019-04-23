package software.simple.solutions.framework.core.components;

import java.io.Serializable;

public interface IDetail extends Serializable  {

	public void setViewDetail(ViewDetail viewDetail);
	
	public ViewDetail getViewDetail();

}
