package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RelationTypeProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.RelationTypeVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.RELATION_TYPE_EDIT, layout = MainView.class)
public class RelationTypeForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField codeFld;
	private CTextField nameFld;
	private CCheckBox activeFld;

	private RelationType relationType;

	public RelationTypeForm() {
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
		formCard.setMinWidth("400px");

		codeFld = formGrid.add(CTextField.class, RelationTypeProperty.CODE);
		codeFld.setRequiredIndicatorVisible(true);
		nameFld = formGrid.add(CTextField.class, RelationTypeProperty.NAME);
		nameFld.setRequiredIndicatorVisible(true);
		activeFld = formGrid.add(CCheckBox.class, RelationTypeProperty.ACTIVE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RelationType setFormValues(Object entity) throws FrameworkException {
		relationType = (RelationType) entity;
		codeFld.setValue(relationType.getCode());
		nameFld.setValue(relationType.getName());
		activeFld.setValue(relationType.getActive());

		return relationType;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		activeFld.setValue(true);
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		RelationTypeVO vo = new RelationTypeVO();

		vo.setId(relationType == null ? null : relationType.getId());

		vo.setCode(codeFld.getValue());
		vo.setName(nameFld.getValue());
		vo.setActive(activeFld.getValue());
		return vo;
	}
}
