package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.service.IRelationTypeService;

public class RelationTypeServiceFacade extends SuperServiceFacade<IRelationTypeService>
		implements IRelationTypeService {

	public static final long serialVersionUID = -4397029434092379871L;

	public RelationTypeServiceFacade(UI ui, Class<IRelationTypeService> s) {
		super(ui, s);
	}

	public static RelationTypeServiceFacade get(UI ui) {
		return new RelationTypeServiceFacade(ui, IRelationTypeService.class);
	}

}
