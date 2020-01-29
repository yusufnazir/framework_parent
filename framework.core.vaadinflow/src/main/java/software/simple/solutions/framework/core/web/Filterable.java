package software.simple.solutions.framework.core.web;

import java.io.Serializable;

import software.simple.solutions.framework.core.pojo.PagingInfo;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

/**
 * Interface implemented by filterViews. Contains just two methods. One for
 * retrieving the search criteria from the filter and the other for send a
 * criteria to the filter.
 * 
 * @author yusuf
 *
 */
public interface Filterable extends Serializable {

	/**
	 * Retrieves an extension of {@link SuperVO}
	 * 
	 * @param searchMode
	 *            {@link PagingInfo} contains the modus operandi for search.
	 *            Best to avoid setting this as null. The default behaviour for
	 *            searchMode is <font style="color:red"><b>not
	 *            advanced!</b></font>.
	 * @return an instance of the extended of {@link SuperVO}. A value object.
	 */
	public Object getCriteria();

	/**
	 * For situation where criteria needs to be sent to the filter.
	 * 
	 * @param criteria
	 *            An instance of the extended {@link SuperVO}.
	 */
	public void setCriteria(Object criteria);
}
