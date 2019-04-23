package software.simple.solutions.framework.core.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IRoleViewPrivilegeService;

public class PrivilegeUtil {

	public static final String NONE = "NONE";

	public static List<String> getPrivileges(String privilege) {
		Gson gson = new Gson();
		List<String> privileges = gson.fromJson(privilege, new TypeToken<List<String>>() {
		}.getType());
		return privileges;
	}

	public static String getStringRepresentation(String privilege) {
		List<String> privileges = getPrivileges(privilege);
		return StringUtils.join(privileges, ",");
	}

	public static List<Privilege> createActionState(Long viewId, Long roleId) throws FrameworkException {
		IRoleViewPrivilegeService roleViewPrivilegeService = ContextProvider.getBean(IRoleViewPrivilegeService.class);
		return roleViewPrivilegeService.getRoleViewPrivilege(viewId, roleId);
	}

}
