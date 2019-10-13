package software.simple.solutions.framework.core.components.select;

import java.util.List;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.service.IRoleService;
import software.simple.solutions.framework.core.util.ContextProvider;

public class RoleSelect extends CComboBox {

	private static final long serialVersionUID = 8665850289294199446L;

	public RoleSelect() throws FrameworkException {
		IRoleService roleService = ContextProvider.getBean(IRoleService.class);
		List<ComboItem> items = roleService.getForListing(Role.class, true);
		setItems(items);
	}

}
