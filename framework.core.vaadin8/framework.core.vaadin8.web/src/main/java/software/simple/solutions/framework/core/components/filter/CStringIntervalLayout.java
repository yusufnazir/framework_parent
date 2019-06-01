package software.simple.solutions.framework.core.components.filter;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.IField;
import software.simple.solutions.framework.core.components.Listing;
import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.pojo.Interval;
import software.simple.solutions.framework.core.pojo.StringInterval;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class CStringIntervalLayout extends CustomComponent implements Interval<String>, IField {

	private static final long serialVersionUID = -4895709684731728860L;

	public static final String TO = "_to";
	public static final String OPERATOR = "_operator";

	private HorizontalLayout mainLayout;
	private CTextField textFld;
	private CComboBox operatorSelect;

	public CStringIntervalLayout() {
		buildMainLayout();
		setCompositionRoot(this.mainLayout);
		setStaticProperties();
	}

	private void setStaticProperties() {
		textFld.addStyleName(ValoTheme.TEXTFIELD_TINY);
		operatorSelect.addStyleName(ValoTheme.COMBOBOX_TINY);
		initOperatorSelect();
		registerListeners();
	}

	private void registerListeners() {
		registerOperatorSelectListeners();
	}

	private void registerOperatorSelectListeners() {
		this.operatorSelect.addValueChangeListener(new ValueChangeListener<ComboItem>() {
			private static final long serialVersionUID = 6673313240019183079L;

			public void valueChange(ValueChangeEvent<ComboItem> event) {
			}
		});

	}

	private void initOperatorSelect() {
		Listing.getStringOperatorSelect(this.operatorSelect);
		this.operatorSelect.setValue(new ComboItem(Operator.CT));
		operatorSelect.setEmptySelectionAllowed(false);
	}

	public void setFrom(String value) {
		textFld.setValue(value);
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
		mainLayout.addComponent(operatorSelect);
		mainLayout.setComponentAlignment(operatorSelect, Alignment.MIDDLE_LEFT);

		textFld = new CTextField();
		textFld.setWidth("100%");
		textFld.setHeight("-1px");
		mainLayout.addComponent(textFld);
		mainLayout.setComponentAlignment(textFld, Alignment.MIDDLE_LEFT);
		mainLayout.setExpandRatio(textFld, 1);

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
		super.setCaption(PropertyResolver.getPropertyValueByLocale(caption, UI.getCurrent().getLocale()));
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		textFld.setReadOnly(readOnly);
		operatorSelect.setReadOnly(readOnly);
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
}
