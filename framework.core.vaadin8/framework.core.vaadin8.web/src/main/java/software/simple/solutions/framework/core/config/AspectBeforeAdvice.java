package software.simple.solutions.framework.core.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.util.ThreadAttributes;
import software.simple.solutions.framework.core.util.ThreadContext;

public class AspectBeforeAdvice {

	public void advice() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		// if (UI.getCurrent() != null) {
//		SessionHolder sessionHolder = (SessionHolder) UI.getCurrent().getData();
		// ApplicationUser applicationUser = sessionHolder.getApplicationUser();
//		Role role = sessionHolder.getSelectedRole();
//		if (user != null) {
//			ThreadContext.add(new ThreadAttributes(user.getUsername(), role == null ? null : role.getId(),
//					(role == null || role.getRoleCategory() == null) ? null : role.getRoleCategory().getId()));
//		}
		// }
	}

}
