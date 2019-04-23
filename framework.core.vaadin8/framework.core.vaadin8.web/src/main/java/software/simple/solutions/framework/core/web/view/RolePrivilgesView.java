package software.simple.solutions.framework.core.web.view;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.ItemCaptionGenerator;

import software.simple.solutions.framework.core.components.AbstractBaseView;
import software.simple.solutions.framework.core.components.CTwinColSelect;
import software.simple.solutions.framework.core.components.MessageWindowHandler;
import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.RoleViewPrivilegeProperty;
import software.simple.solutions.framework.core.service.IPrivilegeService;
import software.simple.solutions.framework.core.service.IRolePrivilegeService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class RolePrivilgesView extends AbstractBaseView {

	private static final long serialVersionUID = 8224544427761613941L;

	private CTwinColSelect privilegesFld = new CTwinColSelect();

	private Role role;

	@Override
	public void executeBuild() throws FrameworkException {
		role = getParentEntity();

		privilegesFld.setWidth("400px");
		privilegesFld.setHeight("300px");
		privilegesFld.setCaption(PropertyResolver.getPropertyValueByLocale(RoleViewPrivilegeProperty.PRIVILEGES));
		addComponent(privilegesFld);

		initializePrivileges();
	}

	private void initializePrivileges() throws FrameworkException {
		IPrivilegeService privilegeService = ContextProvider.getBean(IPrivilegeService.class);
		List<Privilege> privileges = privilegeService.getPrivileges();
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
				IRolePrivilegeService rolePrivilegeService = ContextProvider
						.getBean(IRolePrivilegeService.class);
				try {
					rolePrivilegeService.updateRolePrivileges(role.getId(), privilegeIds);
				} catch (FrameworkException e) {
					new MessageWindowHandler(e);
				}
			}
		});
	}

	private void setPrivilegeValues() {
		IRolePrivilegeService rolePrivilegeService = ContextProvider.getBean(IRolePrivilegeService.class);
		try {
			List<Privilege> privileges = rolePrivilegeService.getPrivilegesByRole(role.getId());
			if (privileges != null) {
				Set<Long> privilegeIds = privileges.parallelStream().map(Privilege::getId)
						.collect(Collectors.toSet());
				privilegesFld.setLongValues(privilegeIds);
			}
		} catch (FrameworkException e) {
			new MessageWindowHandler(e);
		}
	}

}
