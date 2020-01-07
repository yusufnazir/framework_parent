package software.simple.solutions.framework.core.components.filter;

import java.time.LocalDate;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import software.simple.solutions.framework.core.constants.DateConstant;
import software.simple.solutions.framework.core.constants.DateResolution;
import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.DateInterval;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.IField;
import software.simple.solutions.framework.core.web.Listing;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.CPopupDateField;

public class CDateIntervalLayout extends CustomField<DateInterval> implements Interval<LocalDate>, IField {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private CPopupDateField toDateFld;
	private CPopupDateField fromDateFld;
	private CComboBox operatorSelect;
	private boolean isThisRequired = false;

	public CDateIntervalLayout() {
		buildMainLayout();
		add(this.mainLayout);
		setStaticProperties();
	}

	private void setStaticProperties() {
		fromDateFld.setWidth("100px");
		toDateFld.setWidth("100px");
		initOperatorSelect();
		this.toDateFld.setVisible(false);
		registerListeners();
	}

	private void registerListeners() {
		registerOperatorSelectListeners();
	}

	private void registerOperatorSelectListeners() {
//		operatorSelect.addValueChangeListener(new ValueChangeListener<ComboItem>() {
//
//			private static final long serialVersionUID = -3130632054347002584L;
//
//			@Override
//			public void valueChange(ValueChangeEvent<ComboItem> event) {
//				String op = CDateIntervalLayout.this.operatorSelect.getItemCaption();
//				if ((op != null) && (Operator.BE.equalsIgnoreCase(op))) {
//					CDateIntervalLayout.this.toDateFld.setVisible(true);
//				} else {
//					CDateIntervalLayout.this.toDateFld.setVisible(false);
//					CDateIntervalLayout.this.toDateFld.setValue(null);
//				}
//
//			}
//		});
	}

	private void initOperatorSelect() {
		Listing.getDateOperatorSelect(this.operatorSelect);
		this.operatorSelect.setValue(new ComboItem(Operator.EQ));
	}

	public void setFrom(LocalDate date) {
		fromDateFld.setValue(date);
	}

	public void setTo(LocalDate date) {
		toDateFld.setValue(date);
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

		this.fromDateFld = new CPopupDateField();
		this.fromDateFld.setWidth("-1px");
		this.fromDateFld.setHeight("-1px");
		this.mainLayout.add(this.fromDateFld);

		this.toDateFld = new CPopupDateField();
		this.toDateFld.setWidth("-1px");
		this.toDateFld.setHeight("-1px");
		this.mainLayout.add(this.toDateFld);

		return this.mainLayout;
	}

	public void setDefault() {
		fromDateFld.setDefault();
		toDateFld.setDefault();
		operatorSelect.setDefault();
		isThisRequired = false;
	}

	@Override
	public String getOperator() {
		return operatorSelect.getItemId();
	}

	@Override
	public LocalDate getTo() {
		return this.toDateFld.getValue();
	}

	@Override
	public LocalDate getFrom() {
		return this.fromDateFld.getValue();
	}

	public void setOperator(String operator) {
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setOperatorReadonly(boolean readonly) {
		operatorSelect.setReadOnly(readonly);
	}

	public void clear() {
		if (!operatorSelect.isReadOnly()) {
			this.operatorSelect.setValue(new ComboItem(Operator.EQ));
			this.fromDateFld.setValue(null);
			this.toDateFld.setValue(null);
		}
	}

	@Override
	public void setCaptionByKey(String caption) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	public void setValue(LocalDate from, LocalDate to, String operator) {
		fromDateFld.setValue(from);
		toDateFld.setValue(to);
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setValue(DateInterval dateInterval) {
		fromDateFld.setValue(dateInterval.getFrom());
		toDateFld.setValue(dateInterval.getTo());
		operatorSelect.setValue(new ComboItem(dateInterval.getOperator()));
	}

	@Override
	public boolean isThisRequired() {
		return isThisRequired;
	}

	@Override
	public void setRequired() {
		operatorSelect.setRequired();
		fromDateFld.setRequired();
		toDateFld.setRequired();
		isThisRequired = true;
	}

	@Override
	public DateInterval getValue() {
		return new DateInterval(getFrom(), getTo(), getOperator());
	}

	@Override
	protected DateInterval generateModelValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setPresentationValue(DateInterval newPresentationValue) {
		// TODO Auto-generated method stub
		
	}

}
