package software.simple.solutions.framework.core.components.filter;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.DateTimeInterval;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.Listing;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.CPopupDateTimeField;

public class CDateTimeIntervalLayout extends CustomField<DateTimeInterval> implements Interval<LocalTime> {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private CPopupDateTimeField toDateFld;
	private CPopupDateTimeField fromDateFld;
	private CComboBox operatorSelect;

	public CDateTimeIntervalLayout() {
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
	// operatorSelect.addValueChangeListener(new
	// ValueChangeListener<ComboItem>() {
	//
	// private static final long serialVersionUID = -3130632054347002584L;
	//
	// @Override
	// public void valueChange(ValueChangeEvent<ComboItem> event) {
	// String op = CDateTimeIntervalLayout.this.operatorSelect.getItemCaption();
	// if ((op != null) && (Operator.BE.equalsIgnoreCase(op))) {
	// CDateTimeIntervalLayout.this.toDateFld.setVisible(true);
	// } else {
	// CDateTimeIntervalLayout.this.toDateFld.setVisible(false);
	// CDateTimeIntervalLayout.this.toDateFld.setValue(null);
	// }
	//
	// }
	// });
	// }

	private void initOperatorSelect() {
		Listing.getDateOperatorSelect(this.operatorSelect);
		this.operatorSelect.setValue(new ComboItem(Operator.EQ));
	}

	public void setFrom(LocalTime date) {
		fromDateFld.setValue(date);
	}

	public void setTo(LocalTime date) {
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

		this.fromDateFld = new CPopupDateTimeField();
		this.mainLayout.add(this.fromDateFld);

		this.toDateFld = new CPopupDateTimeField();
		this.mainLayout.add(this.toDateFld);

		return this.mainLayout;
	}

	@Override
	public String getOperator() {
		return operatorSelect.getItemId();
	}

	@Override
	public LocalTime getTo() {
		return this.toDateFld.getValue();
	}

	@Override
	public LocalTime getFrom() {
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

	public void setCaptionByKey(String caption) {
		super.setLabel(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	public void setValue(LocalDateTime from, LocalDateTime to, String operator) {
		// fromDateFld.setValue(from);
		// toDateFld.setValue(to);
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setValue(DateTimeInterval dateTimeInterval) {
		// fromDateFld.setValue(dateTimeInterval.getFrom());
		// toDateFld.setValue(dateTimeInterval.getTo());
		operatorSelect.setValue(new ComboItem(dateTimeInterval.getOperator()));
	}

	@Override
	protected DateTimeInterval generateModelValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setPresentationValue(DateTimeInterval newPresentationValue) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public DateTimeInterval getValue() {
	// return new DateTimeInterval(getFrom(), getTo(), getOperator());
	// }

}
