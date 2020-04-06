package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.customfield.CustomField;

public class CustomCheckBox extends CustomField<Boolean> {

	private static final long serialVersionUID = 6818457651591352390L;

	private CCheckBox checkBox;

	public CustomCheckBox() {
		checkBox = new CCheckBox();
		checkBox.getStyle().set("font-size", "var(--lumo-font-size-xl)");
		add(checkBox);
	}

	@Override
	protected Boolean generateModelValue() {
		return checkBox.getValue();
	}

	@Override
	protected void setPresentationValue(Boolean newPresentationValue) {
		checkBox.setValue(newPresentationValue);
	}

}
