package software.simple.solutions.framework.core.components.filter;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.pojo.LongInterval;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.Listing;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.CDiscreetNumberField;

public class CDiscreetNumberIntervalLayout extends CustomField<LongInterval> implements Interval<Long> {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private CDiscreetNumberField toFld;
	private CDiscreetNumberField fromFld;
	private CComboBox operatorSelect;

	public CDiscreetNumberIntervalLayout() {
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
		toFld.setValue(NumberUtil.getInteger(value));
	}

	public void setFrom(Long value) {
		fromFld.setValue(NumberUtil.getInteger(value));
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

		this.fromFld = new CDiscreetNumberField();
		this.fromFld.setWidth("-1px");
		this.fromFld.setHeight("-1px");
		this.mainLayout.add(this.fromFld);
		// mainLayout.setComponentAlignment(fromFld, Alignment.MIDDLE_LEFT);

		this.toFld = new CDiscreetNumberField();
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
		return toFld.getLongValue();
	}

	@Override
	public Long getFrom() {
		return fromFld.getLongValue();
	}

	public CDiscreetNumberField getFromNumberFld() {
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
		fromFld.setValue(NumberUtil.getInteger(from));
		toFld.setValue(NumberUtil.getInteger(to));
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setValue(LongInterval interval) {
		fromFld.setValue(NumberUtil.getInteger(interval.getFrom()));
		toFld.setValue(NumberUtil.getInteger(interval.getTo()));
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
