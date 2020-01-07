package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.IMailTemplateService;

public class MailTemplateServiceFacade extends SuperServiceFacade<IMailTemplateService>
		implements IMailTemplateService {

	public static final long serialVersionUID = 1608983372722719085L;

	public MailTemplateServiceFacade(UI ui, Class<IMailTemplateService> s) {
		super(ui, s);
	}

	public static MailTemplateServiceFacade get(UI ui) {
		return new MailTemplateServiceFacade(ui, IMailTemplateService.class);
	}

}
