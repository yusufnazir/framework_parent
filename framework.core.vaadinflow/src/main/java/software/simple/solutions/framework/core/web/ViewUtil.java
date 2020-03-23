package software.simple.solutions.framework.core.web;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.entities.Configuration;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PopUpMode;
import software.simple.solutions.framework.core.properties.ConfigurationProperty;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.SubMenuServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.SessionHolder;

public class ViewUtil {

	protected static final Logger logger = LogManager.getLogger(ViewUtil.class);

	public static AbstractBaseView initView(Class<?> classToBe) throws FrameworkException {
		AbstractBaseView view = null;
		try {
			view = (AbstractBaseView) classToBe.newInstance();
			ViewDetail viewDetail = view.getViewDetail();
			view.setViewDetail(viewDetail);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return view;
	}

	public static AbstractBaseView initView(String className) throws FrameworkException {
		try {
			Class<?> classToBe = Class.forName(className);
			return initView(classToBe);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static AbstractBaseView initView(SimpleSolutionsMenuItem menuItem, Long roleId, Long applicationUserId)
			throws FrameworkException {
		return initView(menuItem, PopUpMode.NONE, roleId, applicationUserId);
	}

	public static AbstractBaseView initView(SimpleSolutionsMenuItem menuItem, PopUpMode popUpMode, Long roleId,
			Long applicationUserId) throws FrameworkException {
		String viewClassName = menuItem.getMenu().getView().getViewClassName();
		AbstractBaseView view = initView(viewClassName);
		if (view != null) {
			view.setSearchForward(menuItem.getSearchedEntity());
			view.setPopUpMode(popUpMode);

			ViewDetail viewDetail = view.getViewDetail();
			IConfigurationService configurationService = ContextProvider.getBean(IConfigurationService.class);
			Configuration consolidateRoleConfiguration = configurationService
					.getByCode(ConfigurationProperty.APPLICATION_CONSOLIDATE_ROLE);
			boolean consolidateRoles = false;
			if (consolidateRoleConfiguration != null && consolidateRoleConfiguration.getBoolean()) {
				consolidateRoles = true;
			}

			if (consolidateRoles) {
				List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
						.getPrivilegesByViewIdAndUserId(menuItem.getMenu().getView().getId(), applicationUserId);
				viewDetail.setPrivileges(privileges);
			} else {
				List<String> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
						.getPrivilegesByViewIdAndRoleId(menuItem.getMenu().getView().getId(), roleId);
				viewDetail.setPrivileges(privileges);
			}

			viewDetail.setMenu(menuItem.getMenu());
			viewDetail.setView(menuItem.getMenu().getView());

			SessionHolder sessionHolder = (SessionHolder) VaadinSession.getCurrent()
					.getAttribute(Constants.SESSION_HOLDER);
			ActionState actionState = ViewActionStateUtil.createActionState(viewDetail.getPrivileges(),
					menuItem.getMenu().getView().getId(), sessionHolder.getApplicationUser().getId());
			viewDetail.setActionState(actionState);

			List<Menu> subMenus = getSubMenus(menuItem.getMenu().getId());
			viewDetail.setSubMenus(subMenus);

			view.setViewDetail(viewDetail);
			if (menuItem.getParentEntity() != null) {
				view.setParentEntity(menuItem.getParentEntity());
			}
			if(menuItem.getReferenceKeys()!=null){
				view.setReferenceKeys(menuItem.getReferenceKeys());
			}
			view.executeBuild();
			if (view instanceof BasicTemplate) {
				((BasicTemplate) view).executePostRenderActions();
			}
		}
		return view;
	}

	private static List<Menu> getSubMenus(Long parentMenuId) throws FrameworkException {
		List<Menu> subMenus = SubMenuServiceFacade.get(UI.getCurrent()).findTabMenus(parentMenuId);
		return subMenus;
	}

}
