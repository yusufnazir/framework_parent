package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.service.IMessageService;
import software.simple.solutions.framework.core.service.ISuperService;

public class MessageServiceFacade extends SuperServiceFacade<IMessageService> implements IMessageService {

	public static final long serialVersionUID = -7199843548061320463L;

	public MessageServiceFacade(UI ui, Class<IMessageService> s) {
		super(ui, s);
	}

	public static MessageServiceFacade get(UI ui) {
		return new MessageServiceFacade(ui, IMessageService.class);
	}
}
