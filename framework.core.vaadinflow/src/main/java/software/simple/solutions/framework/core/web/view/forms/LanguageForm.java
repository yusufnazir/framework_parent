package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.entities.Language;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.LanguageProperty;
import software.simple.solutions.framework.core.valueobjects.LanguageVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextArea;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.LANGUAGE_EDIT, layout = MainView.class)
public class LanguageForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField codeFld;
	private CTextField nameFld;
	private CTextArea descriptionFld;
	private CCheckBox activeFld;

	private Language language;

	public LanguageForm() {
		super();
	}

	@Override
	public void executeBuild() {
		createFormGrid();
	}

	private CFormLayout createFormGrid() {
		Panel formCard = new Panel();
		add(formCard);
		formGrid = new CFormLayout();
		formCard.add(formGrid);
		formCard.setMaxWidth("600px");

		codeFld = formGrid.add(CTextField.class, LanguageProperty.CODE);

		nameFld = formGrid.add(CTextField.class, LanguageProperty.NAME);

		descriptionFld = formGrid.add(CTextArea.class, LanguageProperty.DESCRIPTION);

		activeFld = formGrid.add(CCheckBox.class, LanguageProperty.ACTIVE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Language setFormValues(Object entity) throws FrameworkException {
		language = (Language) entity;
		activeFld.setValue(language.getActive());
		codeFld.setValue(language.getCode());
		nameFld.setValue(language.getName());
		descriptionFld.setValue(language.getDescription());

		return language;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		nameFld.setRequired();
		codeFld.setRequired();
		activeFld.setValue(true);
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		LanguageVO vo = new LanguageVO();

		vo.setId(language == null ? null : language.getId());

		vo.setActive(activeFld.getValue());
		vo.setCode(codeFld.getValue());
		vo.setName(nameFld.getValue());
		vo.setDescription(descriptionFld.getValue());

		return vo;
	}

}
