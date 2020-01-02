package software.simple.solutions.framework.core.service.facade;

import java.util.List;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.ApplicationUserConfiguration;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IApplicationUserConfigurationService;

public class ApplicationUserConfigurationServiceFacade extends SuperServiceFacade<IApplicationUserConfigurationService>
		implements IApplicationUserConfigurationService {

	public static final long serialVersionUID = -4842564846233236863L;

	public ApplicationUserConfigurationServiceFacade(UI ui, Class<IApplicationUserConfigurationService> s) {
		super(ui, s);
	}

	public static ApplicationUserConfigurationServiceFacade get(UI ui) {
		return new ApplicationUserConfigurationServiceFacade(ui, IApplicationUserConfigurationService.class);
	}

	@Override
	public List<ApplicationUserConfiguration> getSMTPValidateConfiguration(Long applicationUserId)
			throws FrameworkException {
		return service.getSMTPValidateConfiguration(applicationUserId);
	}

	@Override
	public ApplicationUserConfiguration getByCode(Long applicationUserId, String code) throws FrameworkException {
		return service.getByCode(applicationUserId, code);
	}
}
