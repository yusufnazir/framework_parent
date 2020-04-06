package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.entities.Currency;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.CurrencyProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.CurrencyVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.CURRENCY_EDIT, layout = MainView.class)
public class CurrencyForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField codeFld;
	private CTextField nameFld;
	private CCheckBox activeFld;

	private Currency currency;

	public CurrencyForm() {
		super();
	}

	@Override
	public void executeBuild() {
		createFormGrid();
	}

	private CFormLayout createFormGrid() {
		Panel formCard = new Panel();
		formCard.setHeaderKey(SystemProperty.SYSTEM_PANEL_BASIC_INFORMATION);
		add(formCard);
		formGrid = new CFormLayout();
		formCard.add(formGrid);
		formCard.setMinWidth("600px");

		codeFld = formGrid.add(CTextField.class, CurrencyProperty.CODE);
		nameFld = formGrid.add(CTextField.class, CurrencyProperty.NAME);
		activeFld = formGrid.add(CCheckBox.class, CurrencyProperty.ACTIVE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Currency setFormValues(Object entity) throws FrameworkException {
		currency = (Currency) entity;
		codeFld.setValue(currency.getCode());
		nameFld.setValue(currency.getName());
		activeFld.setValue(currency.getActive());

		return currency;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		codeFld.setRequiredIndicatorVisible(true);
		nameFld.setRequiredIndicatorVisible(true);
		activeFld.setValue(true);
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		CurrencyVO vo = new CurrencyVO();

		vo.setId(currency == null ? null : currency.getId());
		vo.setCode(codeFld.getValue());
		vo.setName(nameFld.getValue());
		vo.setActive(activeFld.getValue());

		return vo;
	}

}
