package software.simple.solutions.framework.core.web.view.forms;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.constants.Privileges;
import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.RoleView;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.RoleViewPrivilegeProperty;
import software.simple.solutions.framework.core.properties.RoleViewProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.PrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.RoleViewVO;
import software.simple.solutions.framework.core.web.DetailsWindow;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.components.PrivilegeTwinColSelect;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.lookup.RoleLookUpField;
import software.simple.solutions.framework.core.web.lookup.ViewLookUpField;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.ROLE_VIEW_EDIT, layout = MainView.class)
public class RoleViewForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private ViewLookUpField viewLookUpFld;
	private RoleLookUpField roleLookUpFld;
	private PrivilegeTwinColSelect privilegesFld;

	private software.simple.solutions.framework.core.entities.RoleView roleView;

	public RoleViewForm() {
		super();
	}

	@Override
	public void executeBuild() throws FrameworkException {
		createFormGrid();
	}

	private void createFormGrid() throws FrameworkException {
		Panel formCard = new Panel();
		formCard.setHeaderKey(SystemProperty.SYSTEM_PANEL_BASIC_INFORMATION);
		add(formCard);
		formGrid = new CFormLayout();
		formCard.add(formGrid);
		formCard.setMaxWidth("600px");

		// HorizontalLayout mainLayout = new HorizontalLayout();
		// mainLayout.setSpacing(true);
		// mainLayout.setMargin(false);
		// addComponent(mainLayout);

		// formGrid = ComponentUtil.createGrid();
		// mainLayout.addComponent(formGrid);
		// formGrid.setSpacing(true);
		// formGrid.setCaption(PropertyResolver.getPropertyValueByLocale(RoleViewProperty.BASIC_INFORMATION));

		viewLookUpFld = formGrid.add(ViewLookUpField.class, RoleViewProperty.VIEW);
		viewLookUpFld.setMaxWidth("400px");

		roleLookUpFld = formGrid.add(RoleLookUpField.class, RoleViewProperty.ROLE);
		roleLookUpFld.setMaxWidth("400px");

		privilegesFld = new PrivilegeTwinColSelect();
		privilegesFld.setLabel(PropertyResolver.getPropertyValueByLocale(RoleViewPrivilegeProperty.PRIVILEGES, UI.getCurrent().getLocale()));
//		privilegesFld = formGrid.add(PrivilegeTwinColSelect.class, RoleViewPrivilegeProperty.PRIVILEGES);
		formCard.add(privilegesFld);
		privilegesFld.setWidth("100%");
		privilegesFld.setHeight("300px");
	}

	private void initializePrivileges(List<String> privilegeCodes) throws FrameworkException {
		List<Privilege> privileges = PrivilegeServiceFacade.get(UI.getCurrent()).getPrivileges(privilegeCodes);
		privilegesFld.setValues(privileges);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RoleView setFormValues(Object entity) throws FrameworkException {
		roleView = (RoleView) entity;
		viewLookUpFld.setValue(roleView.getView());
		roleLookUpFld.setValue(roleView.getRole());

		View view = getIfParentEntity(View.class);
		if (view != null) {
			viewLookUpFld.disableForParent();
		}

		Role role = getIfParentEntity(Role.class);
		if (role != null) {
			roleLookUpFld.disableForParent();
		}

		String viewClassName = roleView.getView().getViewClassName();
		List<String> privilegeCodes = Privileges.getPrivilegeCodes(viewClassName);
		initializePrivileges(privilegeCodes);
		setPrivilegeValues();

		privilegesFld.addValueChangeListener(new ValueChangeListener<ValueChangeEvent<Set<ComboItem>>>() {

			private static final long serialVersionUID = -5367827262973425084L;

			@Override
			public void valueChanged(ValueChangeEvent<Set<ComboItem>> event) {
				Set<ComboItem> values = event.getValue();
				List<Long> privilegeIds = values.parallelStream().map(ComboItem::getLongId)
						.collect(Collectors.toList());
				try {
					RoleViewPrivilegeServiceFacade.get(UI.getCurrent()).updateRoleViewPrivileges(roleView.getId(),
							privilegeIds);
				} catch (FrameworkException e) {
					DetailsWindow.build(e);
				}
			}
		});

		return roleView;
	}

	private void setPrivilegeValues() {
		try {
			List<Privilege> privileges = RoleViewPrivilegeServiceFacade.get(UI.getCurrent())
					.getPrivilegesByRoleView(roleView.getId());
			if (privileges != null) {
				Set<Long> privilegeIds = privileges.parallelStream().map(Privilege::getId).collect(Collectors.toSet());
				privilegesFld.setLongValues(privilegeIds);
			}
		} catch (FrameworkException e) {
			DetailsWindow.build(e);
		}
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		privilegesFld.setVisible(false);

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
	public Object getFormValues() throws FrameworkException {
		RoleViewVO vo = new RoleViewVO();

		vo.setId(roleView == null ? null : roleView.getId());

		vo.setViewId(viewLookUpFld.getItemId());
		vo.setRoleId(roleLookUpFld.getItemId());
		return vo;
	}

}
