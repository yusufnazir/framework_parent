package software.simple.solutions.framework.core.web.components;

import org.vaadin.pekka.WysiwygE;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CRichTextEditor extends CustomField<String> {

	private static final long serialVersionUID = 804566892812455070L;

	private VerticalLayout layout;
	private WysiwygE valueFld;

	public CRichTextEditor() {
		layout = new VerticalLayout();
		add(layout);
		layout.setSizeFull();
		valueFld = new WysiwygE();
		layout.add(valueFld);
//		valueFld.setSizeFull();
	}

	@Override
	protected String generateModelValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setPresentationValue(String newPresentationValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		// valueFld.setWidth(width);
	}

}
