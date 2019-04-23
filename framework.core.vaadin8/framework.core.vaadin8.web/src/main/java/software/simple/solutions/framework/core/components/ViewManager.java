package software.simple.solutions.framework.core.components;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Contains specific information pertaining to the different views open for the
 * user session.
 * 
 * @author yusuf
 *
 */
public class ViewManager implements Serializable {

	private static final long serialVersionUID = -8647216379731131499L;
	
	private ConcurrentMap<Long, ViewDetail> views;

	public ViewManager() {
		views = new ConcurrentHashMap<Long, ViewDetail>();
	}

	public ConcurrentMap<Long, ViewDetail> getViews() {
		return views;
	}

	public void setViews(ConcurrentMap<Long, ViewDetail> views) {
		this.views = views;
	}

	public void addViewDetail(Long viewId, ViewDetail viewDetail) {
		views.put(viewId, viewDetail);
	}

}
