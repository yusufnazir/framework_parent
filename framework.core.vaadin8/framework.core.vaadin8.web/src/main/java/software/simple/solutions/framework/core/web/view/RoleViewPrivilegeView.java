package software.simple.solutions.framework.core.web.view;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.CTwinColSelect;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.constants.Privileges;
import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.RoleViewPrivilegeProperty;
import software.simple.solutions.framework.core.service.facade.PrivilegeServiceFacade;
import software.simple.solutions.framework.core.service.facade.RoleViewPrivilegeServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class RoleViewPrivilegeView extends AbstractBaseView {

	private static final long serialVersionUID = 8224544427761613941L;

	private CTwinColSelect privilegesFld = new CTwinColSelect();

	private software.simple.solutions.framework.core.entities.RoleView roleView;

	@Override
	public void executeBuild() throws FrameworkException {
		privilegesFld.setWidth("400px");
		privilegesFld.setHeight("300px");
		privilegesFld.setCaption(PropertyResolver.getPropertyValueByLocale(RoleViewPrivilegeProperty.PRIVILEGES));
		addComponent(privilegesFld);

		initializePrivileges();
	}

	private void initializePrivileges() throws FrameworkException {
		String viewClassName = roleView.getView().getViewClassName();
		List<String> privilegeCodes = Privileges.getPrivilegeCodes(viewClassName);
		List<Privilege> privileges = PrivilegeServiceFacade.get(UI.getCurrent()).getPrivileges(privilegeCodes);
		privilegesFld.setValues(privileges);
		privilegesFld.setItemCaptionGenerator(new ItemCaptionGenerator<ComboItem>() {

			private static final long serialVersionUID = 2764744403473604050L;

			@Override
			public String apply(ComboItem item) {
				return item.getName();
			}
		});

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
			new MessageWindowHandler(e);
		}
	}

}
