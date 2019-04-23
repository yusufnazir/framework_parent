package software.simple.solutions.framework.core.components.select;

import java.util.List;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.entities.RoleCategory;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.IRoleCategoryService;
import software.simple.solutions.framework.core.util.ContextProvider;

public class RoleCategorySelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public RoleCategorySelect() throws FrameworkException {
		IRoleCategoryService roleCategoryService = ContextProvider.getBean(IRoleCategoryService.class);
		List<ComboItem> items = roleCategoryService.getForListing(RoleCategory.class, true);
		setItems(items);
	}
	

}
