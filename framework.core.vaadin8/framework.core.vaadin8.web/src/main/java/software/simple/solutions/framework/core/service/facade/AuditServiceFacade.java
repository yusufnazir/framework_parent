package software.simple.solutions.framework.core.service.facade;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.service.IAuditService;
import software.simple.solutions.framework.core.service.ISuperService;

public class AuditServiceFacade extends SuperServiceFacade<IAuditService> implements IAuditService {

	public static final long serialVersionUID = 4407032095801540279L;

	public AuditServiceFacade(UI ui, Class<IAuditService> s) {
		super(ui, s);
	}

	public static AuditServiceFacade get(UI ui) {
		return new AuditServiceFacade(ui, IAuditService.class);
	}

	@Override
	public <T, A> PagingResult<A> createAuditQuery(Class<T> cl, Long id, PagingSetting pagingSetting) {
		return service.createAuditQuery(cl, id, pagingSetting);
	}
}
