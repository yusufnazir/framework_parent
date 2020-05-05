package software.simple.solutions.framework.core.components.filter;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.pojo.LongInterval;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.Listing;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.LongField;

public class LongIntervalField extends CustomField<LongInterval> implements Interval<Long> {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private LongField toFld;
	private LongField fromFld;
	private CComboBox operatorSelect;

	public LongIntervalField() {
		buildMainLayout();
		add(this.mainLayout);
		setStaticProperties();
	}

	private void setStaticProperties() {
		fromFld.setWidth("100px");
		toFld.setWidth("100px");
		initOperatorSelect();
		registerListeners();
	}

	private void registerListeners() {
		registerOperatorSelectListeners();
	}

	private void registerOperatorSelectListeners() {
		// this.operatorSelect.addValueChangeListener(new
		// ValueChangeListener<ComboItem>() {
		// private static final long serialVersionUID = 6673313240019183079L;
		//
		// public void valueChange(ValueChangeEvent<ComboItem> event) {
		// String op = operatorSelect.getItemCaption();
		// if ((op != null) && (Operator.BE.equalsIgnoreCase(op))) {
		// CDiscreetNumberIntervalLayout.this.toFld.setVisible(true);
		// } else {
		// CDiscreetNumberIntervalLayout.this.toFld.setVisible(false);
		// CDiscreetNumberIntervalLayout.this.toFld.setLongValue(null);
		// }
		// }
		// });

	}

	private void initOperatorSelect() {
		Listing.getNumberOperatorSelect(this.operatorSelect);
		this.operatorSelect.setValue(new ComboItem(Operator.EQ));
		// operatorSelect.setEmptySelectionAllowed(false);
	}

	public void setTo(Long value) {
		toFld.setValue(value);
	}

	public void setFrom(Long value) {
		fromFld.setValue(value);
	}

	public void setOperator(String operator) {
		operatorSelect.setValue(new ComboItem(operator));
	}

	private HorizontalLayout buildMainLayout() {
		this.mainLayout = new HorizontalLayout();
		this.mainLayout.setWidth("-1px");
		this.mainLayout.setHeight("-1px");
		this.mainLayout.setMargin(false);
		this.mainLayout.setSpacing(false);

		setWidth("-1px");
		setHeight("-1px");

		this.operatorSelect = new CComboBox();
		this.operatorSelect.setWidth("50px");
		this.operatorSelect.setHeight("-1px");
		this.mainLayout.add(this.operatorSelect);
		// mainLayout.setComponentAlignment(operatorSelect,
		// Alignment.MIDDLE_LEFT);

		this.fromFld = new LongField();
		this.fromFld.setWidth("-1px");
		this.fromFld.setHeight("-1px");
		this.mainLayout.add(this.fromFld);
		// mainLayout.setComponentAlignment(fromFld, Alignment.MIDDLE_LEFT);

		this.toFld = new LongField();
		this.toFld.setWidth("-1px");
		this.toFld.setHeight("-1px");
		this.mainLayout.add(this.toFld);
		// mainLayout.setComponentAlignment(toFld, Alignment.MIDDLE_LEFT);

		return this.mainLayout;
	}

	@Override
	public String getOperator() {
		return operatorSelect.getItemCaption();
	}

	@Override
	public Long getTo() {
		return toFld.getValue();
	}

	@Override
	public Long getFrom() {
		return fromFld.getValue();
	}

	public LongField getFromNumberFld() {
		return fromFld;
	}

	public void setCaptionByKey(String caption) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, getLocale()));
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		fromFld.setReadOnly(readOnly);
		toFld.setReadOnly(readOnly);
		operatorSelect.setReadOnly(readOnly);
	}

	public void setValue(Long from, Long to, String operator) {
		fromFld.setValue(from);
		toFld.setValue(to);
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setValue(LongInterval interval) {
		fromFld.setValue(interval.getFrom());
		toFld.setValue(interval.getTo());
		operatorSelect.setValue(new ComboItem(interval.getOperator()));
	}

	@Override
	public LongInterval getValue() {
		return new LongInterval(getFrom(), getTo(), getOperator());
	}

	@Override
	protected LongInterval generateModelValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setPresentationValue(LongInterval newPresentationValue) {
		// TODO Auto-generated method stub

	}

}
