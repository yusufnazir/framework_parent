package software.simple.solutions.framework.core.web.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import software.simple.solutions.framework.core.pojo.SplitDate;

public class CSplitDateField extends CustomField<SplitDate> {

	private static final long serialVersionUID = 804566892812455070L;

	private HorizontalLayout layout;
	private CDiscreetNumberField dayFld;
	private CDiscreetNumberField monthFld;
	private CDiscreetNumberField yearFld;

	public CSplitDateField() {
		layout = new HorizontalLayout();
		layout.setSpacing(false);
		add(layout);
		layout.setSizeFull();
		dayFld = new CDiscreetNumberField();
		dayFld.setPlaceholder("DD");
		dayFld.setWidth("35px");
		dayFld.setMax(31);
		dayFld.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		monthFld = new CDiscreetNumberField();
		monthFld.setPlaceholder("MM");
		monthFld.setWidth("35px");
		monthFld.setMax(12);
		monthFld.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		yearFld = new CDiscreetNumberField();
		yearFld.setPlaceholder("YYYY");
		yearFld.setWidth("50px");
		yearFld.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		layout.add(yearFld, new Text("-"), monthFld, new Text("-"), dayFld);
	}
	
	@Override
	public SplitDate getValue() {
		return new SplitDate(yearFld.getLongValue(), monthFld.getLongValue(), dayFld.getLongValue());
	}

	@Override
	protected SplitDate generateModelValue() {
		return new SplitDate(yearFld.getLongValue(), monthFld.getLongValue(), dayFld.getLongValue());
	}

	@Override
	protected void setPresentationValue(SplitDate newPresentationValue) {
		// TODO Auto-generated method stub

	}
}
