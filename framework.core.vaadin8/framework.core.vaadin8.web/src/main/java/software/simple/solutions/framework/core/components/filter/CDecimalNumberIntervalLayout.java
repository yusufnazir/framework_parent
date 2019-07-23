package software.simple.solutions.framework.core.components.filter;

import java.math.BigDecimal;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.CDecimalField;
import software.simple.solutions.framework.core.components.IField;
import software.simple.solutions.framework.core.components.Listing;
import software.simple.solutions.framework.core.components.SessionHolder;
import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CDecimalNumberIntervalLayout extends CustomComponent implements Interval<BigDecimal>, IField {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private CDecimalField toNumberFld;
	private CDecimalField fromNumberFld;
	private CComboBox operatorSelect;
	private SessionHolder sessionHolder;

	public CDecimalNumberIntervalLayout(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
		buildMainLayout();
		setCompositionRoot(this.mainLayout);
		setStaticProperties();
	}

	private void setStaticProperties() {
		this.toNumberFld.setVisible(false);
		initOperatorSelect();
		registerListeners();
	}

	private void registerListeners() {
		registerOperatorSelectListeners();
	}

	private void registerOperatorSelectListeners() {
		this.operatorSelect.addValueChangeListener(new ValueChangeListener<ComboItem>() {
			private static final long serialVersionUID = 6952116114654450308L;

			public void valueChange(ValueChangeEvent<ComboItem> event) {
				String op = operatorSelect.getItemCaption();
				if ((op != null) && ("[]".equalsIgnoreCase(op))) {
					CDecimalNumberIntervalLayout.this.toNumberFld.setVisible(true);
				} else {
					CDecimalNumberIntervalLayout.this.toNumberFld.setVisible(false);
					CDecimalNumberIntervalLayout.this.toNumberFld.setBigDecimalValue(null);
				}
			}
		});
	}

	private void initOperatorSelect() {
		Listing.getNumberOperatorSelect(this.operatorSelect);
		this.operatorSelect.setValue(new ComboItem(Operator.EQ));
		operatorSelect.setEmptySelectionAllowed(false);
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
		this.mainLayout.addComponent(this.operatorSelect);
		mainLayout.setComponentAlignment(operatorSelect, Alignment.MIDDLE_LEFT);

		this.fromNumberFld = new CDecimalField();
		this.fromNumberFld.setWidth("100px");
		this.fromNumberFld.setHeight("-1px");
		this.mainLayout.addComponent(this.fromNumberFld);
		mainLayout.setComponentAlignment(fromNumberFld, Alignment.MIDDLE_LEFT);

		this.toNumberFld = new CDecimalField();
		this.toNumberFld.setWidth("100px");
		this.toNumberFld.setHeight("-1px");
		this.mainLayout.addComponent(this.toNumberFld);
		mainLayout.setComponentAlignment(toNumberFld, Alignment.MIDDLE_LEFT);

		return this.mainLayout;
	}

	public void setDefault() {
		fromNumberFld.setDefault();
		toNumberFld.setDefault();
	}

	@Override
	public String getOperator() {
		return operatorSelect.getItemId();
	}

	@Override
	public BigDecimal getTo() {
		return NumberUtil.getBigDecimal(this.toNumberFld.getValue());
	}

	@Override
	public BigDecimal getFrom() {
		return NumberUtil.getBigDecimal(this.fromNumberFld.getValue());
	}

	public void setTo(BigDecimal value) {
		toNumberFld.setBigDecimalValue(value);
	}

	public void setFrom(BigDecimal value) {
		fromNumberFld.setBigDecimalValue(value);
	}

	public void setOperator(String operator) {
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void clear() {
		if (!operatorSelect.isReadOnly()) {
			this.operatorSelect.setValue(new ComboItem(Operator.EQ));
			this.fromNumberFld.setBigDecimalValue(null);
			this.toNumberFld.setBigDecimalValue(null);
		}
	}

	@Override
	public void setCaptionByKey(String caption) {
		super.setCaption(PropertyResolver.getPropertyValueByLocale(caption, getLocale()));
	}

	@Override
	public boolean isThisRequired() {
		return false;
	}

	@Override
	public void setRequired() {
		fromNumberFld.setRequired();
		toNumberFld.setRequired();
	}

	@Override
	public Object getValue() {
		return null;
	}

	public void setValue(BigDecimal from, BigDecimal to, String operator) {
		fromNumberFld.setBigDecimalValue(from);
		toNumberFld.setBigDecimalValue(to);
		operatorSelect.setValue(new ComboItem(operator));
	}
}
