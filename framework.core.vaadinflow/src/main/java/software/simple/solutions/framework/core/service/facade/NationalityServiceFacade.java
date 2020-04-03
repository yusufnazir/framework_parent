package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.INationalityService;

public class NationalityServiceFacade extends SuperServiceFacade<INationalityService> implements INationalityService {

	public static final long serialVersionUID = 7118619650939156218L;

	public NationalityServiceFacade(UI ui, Class<INationalityService> s) {
		super(ui, s);
	}

	public static NationalityServiceFacade get(UI ui) {
		return new NationalityServiceFacade(ui, INationalityService.class);
	}
}
