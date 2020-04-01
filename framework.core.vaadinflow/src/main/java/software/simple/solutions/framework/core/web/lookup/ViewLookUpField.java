package software.simple.solutions.framework.core.web.lookup;

import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.web.components.LookUpField;
import software.simple.solutions.framework.core.web.view.ViewView;

public class ViewLookUpField extends LookUpField<Long, View> {

	private static final long serialVersionUID = 994848491488378790L;

	public ViewLookUpField() {
		super();
		setEntityClass(View.class);
		setViewClass(ViewView.class);
	}

}
