package software.simple.solutions.framework.core.web.view.forms;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.select.LanguageSelect;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PropertyPerLocaleProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.PropertyPerLocaleVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextArea;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.PROPERTY_PER_LOCALE_EDIT, layout = MainView.class)
public class PropertyPerLocaleForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField referenceKeyFld;
	private CTextField referenceIdFld;
	private CTextArea valueFld;
	private LanguageSelect languageFld;
	private CCheckBox activeFld;

	private PropertyPerLocale propertyPerLocale;

	public PropertyPerLocaleForm() {
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
		formCard.setMaxWidth("400px");

		referenceKeyFld = formGrid.add(CTextField.class, PropertyPerLocaleProperty.REFERENCE_KEY);

		referenceIdFld = formGrid.add(CTextField.class, PropertyPerLocaleProperty.REFERENCE_ID);

		languageFld = formGrid.add(LanguageSelect.class, PropertyPerLocaleProperty.LANGUAGE);

		activeFld = formGrid.add(CCheckBox.class, PropertyPerLocaleProperty.ACTIVE);

		valueFld = formGrid.add(CTextArea.class, PropertyPerLocaleProperty.VALUE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PropertyPerLocale setFormValues(Object entity) throws FrameworkException {
		propertyPerLocale = (PropertyPerLocale) entity;
		activeFld.setValue(propertyPerLocale.getActive());
		referenceKeyFld.setValue(propertyPerLocale.getReferenceKey());
		referenceIdFld.setValue(propertyPerLocale.getReferenceId());
		valueFld.setValue(propertyPerLocale.getValue());
		languageFld.setLongValue(propertyPerLocale.getLanguage().getId());

		String referenceKey = getReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_KEY);
		if (StringUtils.isNotBlank(referenceKey)) {
			referenceKeyFld.setReadOnly(true);
		}
		String referenceId = getReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_ID);
		if (StringUtils.isNotBlank(referenceId)) {
			referenceIdFld.setReadOnly(true);
		}

		return propertyPerLocale;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		activeFld.setValue(true);

		String referenceKey = getReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_KEY);
		if (StringUtils.isNotBlank(referenceKey)) {
			referenceKeyFld.setValue(referenceKey);
			referenceKeyFld.setReadOnly(true);
		}
		String referenceId = getReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_ID);
		if (StringUtils.isNotBlank(referenceId)) {
			referenceIdFld.setValue(referenceId);
			referenceIdFld.setReadOnly(true);
		}
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		PropertyPerLocaleVO vo = new PropertyPerLocaleVO();

		vo.setId(propertyPerLocale == null ? null : propertyPerLocale.getId());

		vo.setActive(activeFld.getValue());
		// vo.setProperty(referenceKeyFld.getValue());
		vo.setReferenceId(referenceIdFld.getValue());
		vo.setReferenceKey(referenceKeyFld.getValue());
		vo.setValue(valueFld.getValue());
		vo.setLanguageId(languageFld.getItemId());

		return vo;
	}

}
