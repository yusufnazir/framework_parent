package software.simple.solutions.framework.core.components.filter;

import java.util.Set;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.pojo.MultiStringInterval;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;
import software.simple.solutions.framework.core.web.Listing;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.CMultiComboBox;

public class CMultiIntervalLayout extends CustomField<MultiStringInterval> implements Interval<Set<String>>, IField {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private CMultiComboBox multiSelectFld;
	private CComboBox operatorSelect;

	public CMultiIntervalLayout() {
		buildMainLayout();
		add(this.mainLayout);
		setStaticProperties();
	}

	private void setStaticProperties() {
		initOperatorSelect();
		registerListeners();
	}

	private void registerListeners() {
		registerOperatorSelectListeners();
	}

	private void registerOperatorSelectListeners() {
//		this.operatorSelect.addValueChangeListener(new ValueChangeListener<ComboItem>() {
//			private static final long serialVersionUID = 6673313240019183079L;
//
//			public void valueChange(ValueChangeEvent<ComboItem> event) {
//			}
//		});

	}

	private void initOperatorSelect() {
		Listing.getMultiStringOperatorSelect(this.operatorSelect);
		this.operatorSelect.setValue(new ComboItem(Operator.IN));
//		operatorSelect.setEmptySelectionAllowed(false);
	}

	public void setFrom(Set<String> value) {
		multiSelectFld.setValue(value);
	}

	public void setOperator(String operator) {
		operatorSelect.setValue(new ComboItem(operator));
	}

	private HorizontalLayout buildMainLayout() {
		mainLayout = new HorizontalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(false);

		setWidth("100%");
		setHeight("-1px");

		operatorSelect = new CComboBox();
		operatorSelect.setWidth("50px");
		operatorSelect.setHeight("-1px");
		mainLayout.add(operatorSelect);
//		mainLayout.setComponentAlignment(operatorSelect, Alignment.MIDDLE_LEFT);

		multiSelectFld = new CMultiComboBox();
		multiSelectFld.setWidth("100%");
		multiSelectFld.setHeight("-1px");
		mainLayout.add(multiSelectFld);
//		mainLayout.setComponentAlignment(multiSelectFld, Alignment.MIDDLE_LEFT);
//		mainLayout.setExpandRatio(multiSelectFld, 1);

		return this.mainLayout;
	}

	public void setDefault() {
		operatorSelect.setValue(new ComboItem(Operator.IN));
		multiSelectFld.clear();
	}

	@Override
	public String getOperator() {
		return operatorSelect.getItemId();
	}

	@Override
	public Set<String> getTo() {
		return null;
	}

	@Override
	public Set<String> getFrom() {
		Set<String> value = this.multiSelectFld.getValue();
		if (value == null || value.isEmpty()) {
			return null;
		}
		return value;
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

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		multiSelectFld.setReadOnly(readOnly);
		operatorSelect.setReadOnly(readOnly);
	}

	public void setValue(Set<String> from, String operator) {
		multiSelectFld.setValue(from);
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setValue(MultiStringInterval interval) {
		multiSelectFld.setValue(interval.getFrom());
		operatorSelect.setValue(new ComboItem(interval.getOperator()));
	}

	@Override
	public MultiStringInterval getValue() {
		return new MultiStringInterval(getFrom(), getOperator());
	}

	@Override
	public boolean isThisRequired() {
		return false;
	}

	@Override
	public void setRequired() {

	}

	@Override
	protected MultiStringInterval generateModelValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setPresentationValue(MultiStringInterval newPresentationValue) {
		// TODO Auto-generated method stub
		
	}

}
