package software.simple.solutions.framework.core.web.view.forms;

import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.select.MenuTypeSelect;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.MenuProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.valueobjects.MenuVO;
import software.simple.solutions.framework.core.web.FormView;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.web.components.CFormLayout;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.Panel;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.lookup.MenuLookUpField;
import software.simple.solutions.framework.core.web.lookup.ViewLookUpField;
import software.simple.solutions.framework.core.web.routing.Routes;

@Route(value = Routes.MENU_EDIT, layout = MainView.class)
public class MenuForm extends FormView {

	private static final long serialVersionUID = 6109727427163734676L;

	private CFormLayout formGrid;
	private CTextField codeFld;
	private CTextField nameFld;
	private CCheckBox activeFld;
	private MenuTypeSelect typeFld;
	private CDiscreetNumberField indexFld;
	private ViewLookUpField viewLookUpField;
	private MenuLookUpField menuLookUpField;

	private Menu menu;

	public MenuForm() {
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

		codeFld = formGrid.add(CTextField.class, MenuProperty.CODE);
		codeFld.setRequiredIndicatorVisible(true);

		nameFld = formGrid.add(CTextField.class, MenuProperty.NAME);

		viewLookUpField = formGrid.add(ViewLookUpField.class, MenuProperty.VIEW);

		menuLookUpField = formGrid.add(MenuLookUpField.class, MenuProperty.PARENT_MENU);

		typeFld = formGrid.add(MenuTypeSelect.class, MenuProperty.TYPE);

		indexFld = formGrid.add(CDiscreetNumberField.class, MenuProperty.INDEX);

		activeFld = formGrid.add(CCheckBox.class, MenuProperty.ACTIVE);

		return formGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Menu setFormValues(Object entity) throws FrameworkException {
		menu = (Menu) entity;
		codeFld.setValue(menu.getCode());
		nameFld.setValue(menu.getName());
		activeFld.setValue(menu.getActive());
		viewLookUpField.setValue(menu.getView());
		menuLookUpField.setValue(menu.getParentMenu());
		indexFld.setLongValue(menu.getIndex());
		typeFld.setLongValue(menu.getType());

		nameFld.setRequiredIndicatorVisible(true);
		codeFld.setReadOnly(true);

		View view = getIfParentEntity(View.class);
		if (view != null) {
			viewLookUpField.disableForParent();
		}

		Menu menu = getIfParentEntity(Menu.class);
		if (menu != null) {
			menuLookUpField.disableForParent();
		}
		return menu;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		codeFld.setRequiredIndicatorVisible(true);
		nameFld.setRequiredIndicatorVisible(true);
		activeFld.setValue(true);

		View view = getIfParentEntity(View.class);
		if (view != null) {
			viewLookUpField.setValue(view);
			viewLookUpField.disableForParent();
		}

		Menu menu = getIfParentEntity(Menu.class);
		if (menu != null) {
			menuLookUpField.setValue(menu);
			menuLookUpField.disableForParent();
		}
	}

	@Override
	public Object getFormValues() throws FrameworkException {
		MenuVO vo = new MenuVO();

		vo.setId(menu == null ? null : menu.getId());
		vo.setCode(codeFld.getValue());
		vo.setName(nameFld.getValue());
		vo.setActive(activeFld.getValue());
		vo.setViewId(viewLookUpField.getItemId());
		vo.setParentMenuId(menuLookUpField.getItemId());
		vo.setType(typeFld.getItemId());

		return vo;
	}
}
