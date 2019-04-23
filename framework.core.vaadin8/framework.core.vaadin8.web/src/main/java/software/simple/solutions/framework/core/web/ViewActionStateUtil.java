package software.simple.solutions.framework.core.web;

import java.util.List;
import java.util.stream.Collectors;

import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IRoleViewPrivilegeService;
import software.simple.solutions.framework.core.util.ContextProvider;

public class ViewActionStateUtil {

	private ViewActionStateUtil() {
		super();
	}

	public static ActionState createActionState(List<String> supportedPrivileges, Long viewId, Long roleId)
			throws FrameworkException {
		IRoleViewPrivilegeService roleViewPrivilegeService = ContextProvider.getBean(IRoleViewPrivilegeService.class);
		List<String> privileges = roleViewPrivilegeService.getPrivilegesByViewIdAndRoleId(viewId, roleId);
		if (privileges != null && supportedPrivileges != null) {
			privileges = supportedPrivileges.stream().filter(privileges::contains).collect(Collectors.toList());
		}
		return new ActionState(privileges);
	}
}
