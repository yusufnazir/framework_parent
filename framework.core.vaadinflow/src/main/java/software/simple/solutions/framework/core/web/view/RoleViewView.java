package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RoleViewProperty;
import software.simple.solutions.framework.core.service.facade.RoleViewServiceFacade;
import software.simple.solutions.framework.core.valueobjects.RoleViewVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.lookup.RoleLookUpField;
import software.simple.solutions.framework.core.web.lookup.ViewLookUpField;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.RoleViewForm;

@Route(value = Routes.ROLE_VIEW, layout = MainView.class)
public class RoleViewView extends BasicTemplate<software.simple.solutions.framework.core.entities.RoleView> {

	private static final long serialVersionUID = 6503015064562511801L;

	public RoleViewView() {
		setEntityClass(software.simple.solutions.framework.core.entities.RoleView.class);
		setServiceClass(RoleViewServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(RoleViewForm.class);
		setParentReferenceKey(ReferenceKey.ROLE_VIEW);
		setEditRoute(Routes.ROLE_VIEW_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(new ValueProvider<software.simple.solutions.framework.core.entities.RoleView, String>() {

			private static final long serialVersionUID = -4387596471417981225L;

			@Override
			public String apply(software.simple.solutions.framework.core.entities.RoleView source) {
				return source.getRole() == null ? null : source.getRole().getName();
			}
		}, RoleViewProperty.ROLE);
		addContainerProperty(new ValueProvider<software.simple.solutions.framework.core.entities.RoleView, String>() {

			private static final long serialVersionUID = 6274861296913350114L;

			@Override
			public String apply(software.simple.solutions.framework.core.entities.RoleView source) {
				return source == null ? null : source.getView().getCaption();
			}
		}, RoleViewProperty.VIEW);

		View view = getIfParentEntity(View.class);
		if (view != null) {
			addHiddenColumnId(RoleViewProperty.VIEW);
		}
		Role role = getIfParentEntity(Role.class);
		if (role != null) {
			addHiddenColumnId(RoleViewProperty.ROLE);
		}
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private ViewLookUpField viewLookUpFld;
		private RoleLookUpField roleLookUpFld;

		@Override
		public void executeBuild() throws FrameworkException {
			viewLookUpFld = addField(ViewLookUpField.class, RoleViewProperty.VIEW, 0, 0);
			roleLookUpFld = addField(RoleLookUpField.class, RoleViewProperty.ROLE, 1, 0);
			
			View view = getIfParentEntity(View.class);
			if (view != null) {
				viewLookUpFld.setValue(view);
				viewLookUpFld.disableForParent();
			}
			Role role = getIfParentEntity(Role.class);
			if (role != null) {
				roleLookUpFld.setValue(role);
				roleLookUpFld.disableForParent();
			}
		}

		@Override
		public Object getCriteria() {
			RoleViewVO vo = new RoleViewVO();
			vo.setViewId(viewLookUpFld.getItemId());
			vo.setRoleId(roleLookUpFld.getItemId());
			return vo;
		}

	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
