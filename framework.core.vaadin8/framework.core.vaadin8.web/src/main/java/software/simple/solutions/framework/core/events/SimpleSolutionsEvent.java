package software.simple.solutions.framework.core.events;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.View;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.BaseView;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.entities.ApplicationUser;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IApplicationUserService;
import software.simple.solutions.framework.core.service.IUserRoleService;
import software.simple.solutions.framework.core.util.ContextProvider;

/*
 * Event bus events used in Dashboard are listed here as inner classes.
 */
public abstract class SimpleSolutionsEvent {

	private static final Logger logger = LogManager.getLogger(SimpleSolutionsEvent.class);

	public static final class UserLoginRequestedEvent {
		private final String userName, password;

		public UserLoginRequestedEvent(final String userName, final String password) {
			this.userName = userName;
			this.password = password;

			SessionHolder sessionHolder = (SessionHolder) UI.getCurrent().getData();
			IApplicationUserService applicationUserService = ContextProvider.getBean(IApplicationUserService.class);
			try {
				ApplicationUser applicationUser = applicationUserService.getByUsername(userName);
				sessionHolder.setApplicationUser(applicationUser);
				IUserRoleService userRoleService = ContextProvider.getBean(IUserRoleService.class);
				List<Role> rolesByUser = userRoleService.findRolesByUser(applicationUser.getId());
				sessionHolder.setRoles(rolesByUser);
				if (rolesByUser != null && !rolesByUser.isEmpty()) {
					sessionHolder.setSelectedRole(rolesByUser.get(0));
				}
			} catch (FrameworkException e) {
				logger.error(e.getMessage(), e);
			}
		}

		public String getUserName() {
			return userName;
		}

		public String getPassword() {
			return password;
		}
	}

	public static class BrowserResizeEvent {

	}

	public static class UserLoggedOutEvent {

	}

	public static class TabSheetViewEvent {

		private final Long menuId;
		private final Object searchedEntity;

		public TabSheetViewEvent(Long menuId, Object searchedEntity) {
			this.menuId = menuId;
			this.searchedEntity = searchedEntity;
		}

		public Long getMenuId() {
			return menuId;
		}

		public Object getSearchedEntity() {
			return searchedEntity;
		}
	}

	public static class MenuStructureEvent {

		public MenuStructureEvent() {
			super();
		}

	}

	public static class NotificationsCountUpdatedEvent {
	}

	public static final class PostViewChangeEvent {
		private final BaseView view;

		public PostViewChangeEvent(final BaseView view) {
			this.view = view;
		}

		public BaseView getView() {
			return view;
		}
	}

	public static class CloseOpenWindowsEvent {
	}

	public static class ProfileUpdatedEvent {
	}

	public static final class MenuSelectedEvent {

		private final View view;

		public MenuSelectedEvent(View view) {
			this.view = view;
		}

		public View getView() {
			return view;
		}
	}

	public static final class LookupMenuSelectedEvent {

		private final Menu menu;

		public LookupMenuSelectedEvent(Menu menu) {
			this.menu = menu;
		}

		public Menu getMenu() {
			return menu;
		}
	}

}
