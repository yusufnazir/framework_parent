package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.IHolidayService;

public class HolidayServiceFacade extends SuperServiceFacade<IHolidayService> implements IHolidayService {

	public static final long serialVersionUID = 3660929213971273211L;

	public HolidayServiceFacade(UI ui, Class<IHolidayService> s) {
		super(ui, s);
	}

	public static HolidayServiceFacade get(UI ui) {
		return new HolidayServiceFacade(ui, IHolidayService.class);
	}

}
