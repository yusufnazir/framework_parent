package software.simple.solutions.framework.core.web;

import java.util.List;

import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public class ViewActionStateUtil {

	private ViewActionStateUtil() {
		super();
	}

	public static ActionState createActionState(List<String> privileges, Long viewId, Long roleId)
			throws FrameworkException {
		// List<String> privileges =
		// roleViewPrivilegeService.getPrivilegesByViewIdAndRoleId(viewId,
		// roleId);
		// if (privileges != null && supportedPrivileges != null) {
		// privileges =
		// supportedPrivileges.stream().filter(privileges::contains).collect(Collectors.toList());
		// }
		return new ActionState(privileges);
	}
}
