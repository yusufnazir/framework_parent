package software.simple.solutions.framework.core.components.filter;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;
import software.simple.solutions.framework.core.web.Listing;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.CTextField;

public class CStringIntervalLayout extends CustomField<StringInterval> implements Interval<String>, IField {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private CTextField textFld;
	private CComboBox operatorSelect;

	public CStringIntervalLayout() {
		buildMainLayout();
		add(this.mainLayout);
		setStaticProperties();
	}

	private void setStaticProperties() {
		initOperatorSelect();
		registerListeners();
	}

	private void registerListeners() {
		// registerOperatorSelectListeners();
	}

	// private void registerOperatorSelectListeners() {
	// this.operatorSelect.addValueChangeListener(new
	// ValueChangeListener<ComboItem>() {
	// private static final long serialVersionUID = 6673313240019183079L;
	//
	// public void valueChange(ValueChangeEvent<ComboItem> event) {
	// }
	// });
	//
	// }

	private void initOperatorSelect() {
		Listing.getStringOperatorSelect(this.operatorSelect);
		this.operatorSelect.setValue(new ComboItem(Operator.CT));
	}

	public void setFrom(String value) {
		textFld.setValue(value);
	}

	public void setOperator(String operator) {
		operatorSelect.setValue(new ComboItem(operator));
	}

	private HorizontalLayout buildMainLayout() {
//		getElement().getStyle().set("display", "inline");
		getElement().getStyle().set("--lumo-text-field-size", "var(--lumo-size-s)");

		mainLayout = new HorizontalLayout();
		mainLayout.setWidth("300px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		mainLayout.setPadding(false);

		operatorSelect = new CComboBox();
		operatorSelect.getElement().setAttribute("theme", "small");
		operatorSelect.setWidth("50px");
		mainLayout.add(operatorSelect);
		// mainLayout.setComponentAlignment(operatorSelect,
		// Alignment.MIDDLE_LEFT);

		textFld = new CTextField();
		textFld.setWidth("100%");
		mainLayout.add(textFld);
		mainLayout.expand(textFld);
		textFld.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		// mainLayout.setComponentAlignment(textFld, Alignment.MIDDLE_LEFT);
		// mainLayout.setExpandRatio(textFld, 1);

		mainLayout.setAlignItems(Alignment.CENTER);

		return this.mainLayout;
	}

	public void setDefault() {
		operatorSelect.setValue(new ComboItem(Operator.CT));
		textFld.clear();
	}

	@Override
	public String getOperator() {
		return operatorSelect.getItemId();
	}

	@Override
	public String getTo() {
		return null;
	}

	@Override
	public String getFrom() {
		String value = this.textFld.getValue();
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		return value;
	}

	public CTextField getFromNumberFld() {
		return textFld;
	}

	public void clear() {
		if (!operatorSelect.isReadOnly()) {
			setDefault();
		}
	}

	@Override
	public void setCaptionByKey(String caption) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	public void setValue(String from, String operator) {
		textFld.setValue(from);
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setValue(StringInterval interval) {
		textFld.setValue(interval.getFrom());
		operatorSelect.setValue(new ComboItem(interval.getOperator()));
	}

	public void setValue(String value) {
		textFld.setValue(value);
		operatorSelect.setValue(new ComboItem(Operator.EQ));
	}

	@Override
	public StringInterval getValue() {
		return new StringInterval(getFrom(), getOperator());
	}

	@Override
	public boolean isThisRequired() {
		return false;
	}

	@Override
	public void setRequired() {

	}

	@Override
	protected StringInterval generateModelValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setPresentationValue(StringInterval newPresentationValue) {
		// TODO Auto-generated method stub

	}

	public void setOperatorDisabled() {
		operatorSelect.setReadOnly(true);
	}
	
	@Override
	public void setReadOnly(boolean readOnly) {
		operatorSelect.setReadOnly(readOnly);
		textFld.setReadOnly(readOnly);
	}
}
