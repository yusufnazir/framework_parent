package software.simple.solutions.framework.core.components.filter;

import java.time.LocalDateTime;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.CPopupDateTimeField;
import software.simple.solutions.framework.core.components.IField;
import software.simple.solutions.framework.core.components.Listing;
import software.simple.solutions.framework.core.constants.DateConstant;
import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.DateTimeInterval;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CDateTimeIntervalLayout extends CustomComponent implements Interval<LocalDateTime>, IField {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private CPopupDateTimeField toDateFld;
	private CPopupDateTimeField fromDateFld;
	private CComboBox operatorSelect;
	private boolean isThisRequired = false;

	public CDateTimeIntervalLayout() {
		buildMainLayout();
		setCompositionRoot(this.mainLayout);
		setStaticProperties();
	}

	private void setStaticProperties() {
		fromDateFld.setDateFormat(DateConstant.SIMPLE_DATE_FORMAT.toPattern());
		fromDateFld.addStyleName(ValoTheme.DATEFIELD_TINY);
		fromDateFld.setWidth("100px");
		toDateFld.setDateFormat(DateConstant.SIMPLE_DATE_FORMAT.toPattern());
		toDateFld.addStyleName(ValoTheme.DATEFIELD_TINY);
		toDateFld.setWidth("100px");
		operatorSelect.addStyleName(ValoTheme.COMBOBOX_TINY);
		initOperatorSelect();
		this.toDateFld.setVisible(false);
		registerListeners();
	}

	private void registerListeners() {
		registerOperatorSelectListeners();
	}

	private void registerOperatorSelectListeners() {
		operatorSelect.addValueChangeListener(new ValueChangeListener<ComboItem>() {

			private static final long serialVersionUID = -3130632054347002584L;

			@Override
			public void valueChange(ValueChangeEvent<ComboItem> event) {
				String op = CDateTimeIntervalLayout.this.operatorSelect.getItemCaption();
				if ((op != null) && (Operator.BE.equalsIgnoreCase(op))) {
					CDateTimeIntervalLayout.this.toDateFld.setVisible(true);
				} else {
					CDateTimeIntervalLayout.this.toDateFld.setVisible(false);
					CDateTimeIntervalLayout.this.toDateFld.setValue(null);
				}

			}
		});
	}

	private void initOperatorSelect() {
		Listing.getDateOperatorSelect(this.operatorSelect);
		this.operatorSelect.setValue(new ComboItem(Operator.EQ));
		operatorSelect.setEmptySelectionAllowed(false);
	}

	public void setFrom(LocalDateTime date) {
		fromDateFld.setValue(date);
	}

	public void setTo(LocalDateTime date) {
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
		this.mainLayout.addComponent(this.operatorSelect);
		mainLayout.setComponentAlignment(operatorSelect, Alignment.MIDDLE_LEFT);

		this.fromDateFld = new CPopupDateTimeField();
		this.fromDateFld.setWidth("-1px");
		this.fromDateFld.setHeight("-1px");
		this.mainLayout.addComponent(this.fromDateFld);
		mainLayout.setComponentAlignment(fromDateFld, Alignment.MIDDLE_LEFT);

		this.toDateFld = new CPopupDateTimeField();
		this.toDateFld.setWidth("-1px");
		this.toDateFld.setHeight("-1px");
		this.mainLayout.addComponent(this.toDateFld);
		mainLayout.setComponentAlignment(toDateFld, Alignment.MIDDLE_LEFT);

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
	public LocalDateTime getTo() {
		return this.toDateFld.getValue();
	}

	@Override
	public LocalDateTime getFrom() {
		return this.fromDateFld.getValue();
	}

	public void setOperator(String operator) {
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setOperatorReadonly(boolean readonly) {
		operatorSelect.setReadOnly(readonly);
	}

	public void clear() {
		this.operatorSelect.setValue(new ComboItem(Operator.EQ));
		this.fromDateFld.setValue(null);
		this.toDateFld.setValue(null);
	}

	@Override
	public void setCaptionByKey(String caption) {
		super.setCaption(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	public void setValue(LocalDateTime from, LocalDateTime to, String operator) {
		fromDateFld.setValue(from);
		toDateFld.setValue(to);
		operatorSelect.setValue(new ComboItem(operator));
	}

	public void setValue(DateTimeInterval dateTimeInterval) {
		fromDateFld.setValue(dateTimeInterval.getFrom());
		toDateFld.setValue(dateTimeInterval.getTo());
		operatorSelect.setValue(new ComboItem(dateTimeInterval.getOperator()));
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
	public DateTimeInterval getValue() {
		return new DateTimeInterval(getFrom(), getTo(), getOperator());
	}

}
