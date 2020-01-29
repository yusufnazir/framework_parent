package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IMessagePerLocaleService;

public class MessagePerLocaleServiceFacade extends SuperServiceFacade<IMessagePerLocaleService>
		implements IMessagePerLocaleService {

	public static final long serialVersionUID = 2488720107522367027L;

	public MessagePerLocaleServiceFacade(UI ui, Class<IMessagePerLocaleService> s) {
		super(ui, s);
	}

	public static MessagePerLocaleServiceFacade get(UI ui) {
		return new MessagePerLocaleServiceFacade(ui, IMessagePerLocaleService.class);
	}

	@Override
	public <T> T getByMessageAndLocale(String messageKey, String language) throws FrameworkException {
		return service.getByMessageAndLocale(messageKey, language);
	}
}
