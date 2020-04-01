package software.simple.solutions.framework.core.web.lookup;

import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.web.components.LookUpField;
import software.simple.solutions.framework.core.web.view.RelationTypeView;

public class RelationTypeLookUpField extends LookUpField<Long, RelationType> {

	private static final long serialVersionUID = 994848491488378790L;

	public RelationTypeLookUpField() {
		super();
		setEntityClass(RelationType.class);
		setViewClass(RelationTypeView.class);
	}

}
