package software.simple.solutions.framework.core.web.view;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.data.ValueProvider;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTwinColSelect;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.constants.Privileges;
import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.RoleView;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.RoleViewPrivilegeProperty;
import software.simple.solutions.framework.core.properties.RoleViewProperty;
import software.simple.solutions.framework.core.service.IRoleViewService;
import software.simple.solutions.framework.core.service.facade.PrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.RoleViewVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.lookup.RoleLookUpField;
import software.simple.solutions.framework.core.web.lookup.ViewLookUpField;

public class RoleViewView extends BasicTemplate<software.simple.solutions.framework.core.entities.RoleView> {

	private static final long serialVersionUID = 6503015064562511801L;

	public RoleViewView() {
		setEntityClass(software.simple.solutions.framework.core.entities.RoleView.class);
		setServiceClass(RoleViewServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
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
		Object parentEntity = getParentEntity();
		if (parentEntity != null) {
			if (parentEntity instanceof View) {
				addHiddenColumnId(RoleViewProperty.VIEW);
			} else if (parentEntity instanceof Role) {
				addHiddenColumnId(RoleViewProperty.ROLE);
			}
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
		}

		@Override
		public Object getCriteria() {
			RoleViewVO vo = new RoleViewVO();
			vo.setViewId(viewLookUpFld.getItemId());
			vo.setRoleId(roleLookUpFld.getItemId());
			return vo;
		}

	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private ViewLookUpField viewLookUpFld;
		private RoleLookUpField roleLookUpFld;
		private CTwinColSelect privilegesFld;

		private software.simple.solutions.framework.core.entities.RoleView roleView;

		public Form() {
			super();
		}

		@Override
		public void executeBuild() throws FrameworkException {
			createFormGrid();
		}

		private void createFormGrid() throws FrameworkException {
			HorizontalLayout mainLayout = new HorizontalLayout();
			mainLayout.setSpacing(true);
			mainLayout.setMargin(false);
			addComponent(mainLayout);

			formGrid = ComponentUtil.createGrid();
			mainLayout.addComponent(formGrid);
			formGrid.setSpacing(true);
			formGrid.setCaption(PropertyResolver.getPropertyValueByLocale(RoleViewProperty.BASIC_INFORMATION));

			viewLookUpFld = formGrid.addField(ViewLookUpField.class, RoleViewProperty.VIEW, 0, 0);
			viewLookUpFld.handleForParentEntity(getParentEntity());

			roleLookUpFld = formGrid.addField(RoleLookUpField.class, RoleViewProperty.ROLE, 0, 1);
			roleLookUpFld.handleForParentEntity(getParentEntity());

			privilegesFld = new CTwinColSelect();
			privilegesFld.setWidth("400px");
			privilegesFld.setHeight("300px");
			privilegesFld.setCaption(PropertyResolver.getPropertyValueByLocale(RoleViewPrivilegeProperty.PRIVILEGES));
			mainLayout.addComponent(privilegesFld);
		}

		private void initializePrivileges(List<String> privilegeCodes) throws FrameworkException {
			List<Privilege> privileges = PrivilegeServiceFacade.get(UI.getCurrent()).getPrivileges(privilegeCodes);
			privilegesFld.setValues(privileges);
			privilegesFld.setItemCaptionGenerator(new ItemCaptionGenerator<ComboItem>() {

				private static final long serialVersionUID = 2764744403473604050L;

				@Override
				public String apply(ComboItem item) {
					return item.getName();
				}
			});
		}

		@SuppressWarnings("unchecked")
		@Override
		public RoleView setFormValues(Object entity) throws FrameworkException {
			roleView = (RoleView) entity;
			viewLookUpFld.setValue(roleView.getView());
			roleLookUpFld.setValue(roleView.getRole());

			String viewClassName = roleView.getView().getViewClassName();
			List<String> privilegeCodes = Privileges.getPrivilegeCodes(viewClassName);
			initializePrivileges(privilegeCodes);
			setPrivilegeValues();

			privilegesFld.addValueChangeListener(new ValueChangeListener<Set<ComboItem>>() {

				private static final long serialVersionUID = -2456888062255464291L;

				@Override
				public void valueChange(ValueChangeEvent<Set<ComboItem>> event) {
					Set<ComboItem> values = event.getValue();
					List<Long> privilegeIds = values.parallelStream().map(ComboItem::getLongId)
							.collect(Collectors.toList());
					try {
						RoleViewPrivilegeServiceFacade.get(UI.getCurrent()).updateRoleViewPrivileges(roleView.getId(),
								privilegeIds);
					} catch (FrameworkException e) {
						new MessageWindowHandler(e);
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
					Set<Long> privilegeIds = privileges.parallelStream().map(Privilege::getId)
							.collect(Collectors.toSet());
					privilegesFld.setLongValues(privilegeIds);
				}
			} catch (FrameworkException e) {
				new MessageWindowHandler(e);
			}
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			roleLookUpFld.setRequired();
			viewLookUpFld.setRequired();
			privilegesFld.setVisible(false);
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

}
