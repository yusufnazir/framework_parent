package software.simple.solutions.framework.core.web.view;

import com.vaadin.data.ValueProvider;

import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.components.select.MenuTypeSelect;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.MenuProperty;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.MenuVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.lookup.MenuLookUpField;
import software.simple.solutions.framework.core.web.lookup.ViewLookUpField;

public class MenuView extends BasicTemplate<Menu> {

	private static final long serialVersionUID = 6503015064562511801L;

	public MenuView() {
		setEntityClass(Menu.class);
		setServiceClass(MenuServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Menu::getCode, MenuProperty.CODE);
		addContainerProperty(Menu::getName, MenuProperty.NAME);
		addContainerProperty(new ValueProvider<Menu, String>() {

			@Override
			public String apply(Menu source) {
				return source.getView() == null ? null : source.getView().getCaption();
			}
		}, MenuProperty.VIEW);
		addContainerProperty(new ValueProvider<Menu, String>() {

			@Override
			public String apply(Menu source) {
				return source.getParentMenu() == null ? null : source.getParentMenu().getCaption();
			}
		}, MenuProperty.PARENT_MENU);
		addContainerProperty(new ValueProvider<Menu, String>() {

			@Override
			public String apply(Menu source) {
				if (source.getType() == null) {
					return null;
				}
				return PropertyResolver.getPropertyValueByLocale(MenuProperty.TYPE + "." + source.getType());
			}
		}, MenuProperty.TYPE);
		addContainerProperty(Menu::getIndex, MenuProperty.INDEX);
	}

	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout codeFld;
		private CStringIntervalLayout nameFld;
		private ViewLookUpField viewFld;
		private MenuLookUpField menuFld;
		private ActiveSelect activeFld;
		private CComboBox typeFld;
		// private List<ComboItem> viewListing;

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, MenuProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, MenuProperty.NAME, 0, 1);

			viewFld = addField(ViewLookUpField.class, MenuProperty.VIEW, 1, 0);
			menuFld = addField(MenuLookUpField.class, MenuProperty.PARENT_MENU, 1, 1);

			typeFld = addField(MenuTypeSelect.class, MenuProperty.TYPE, 2, 1);
			activeFld = addField(ActiveSelect.class, MenuProperty.ACTIVE, 2, 0);
		}

		@Override
		public Object getCriteria() {
			MenuVO vo = new MenuVO();
			vo.setActive(activeFld.getItemId());
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			vo.setViewId(viewFld.getItemId());
			vo.setParentMenuId(menuFld.getItemId());
			vo.setType(typeFld.getItemId());
			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextField codeFld;
		private CTextField nameFld;
		private CCheckBox activeFld;
		private MenuTypeSelect typeFld;
		private CDiscreetNumberField indexFld;
		private ViewLookUpField viewFld;
		private MenuLookUpField menuFld;
		private CTextField propertyFld;

		private Menu menu;

		public Form() {
			super();
		}

		@Override
		public void executeBuild() {
			formGrid = createFormGrid();
			addComponent(formGrid);
		}

		private CGridLayout createFormGrid() {
			formGrid = ComponentUtil.createGrid();

			codeFld = formGrid.addField(CTextField.class, MenuProperty.CODE, 0, 0);
			codeFld.setRequired();

			nameFld = formGrid.addField(CTextField.class, MenuProperty.NAME, 0, 1);

			viewFld = formGrid.addField(ViewLookUpField.class, MenuProperty.VIEW, 0, 2);

			menuFld = formGrid.addField(MenuLookUpField.class, MenuProperty.PARENT_MENU, 0, 3);

			typeFld = formGrid.addField(MenuTypeSelect.class, MenuProperty.TYPE, 1, 0);

			indexFld = formGrid.addField(CDiscreetNumberField.class, MenuProperty.INDEX, 1, 1);

			propertyFld = formGrid.addField(CTextField.class, MenuProperty.KEY, 1, 2);

			activeFld = formGrid.addField(CCheckBox.class, MenuProperty.ACTIVE, 1, 3);

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Menu setFormValues(Object entity) throws FrameworkException {
			menu = (Menu) entity;
			codeFld.setValue(menu.getCode());
			nameFld.setValue(menu.getName());
			activeFld.setValue(menu.getActive());
			viewFld.setValue(menu.getView());
			menuFld.setValue(menu.getParentMenu());
			indexFld.setLongValue(menu.getIndex());
			typeFld.setLongValue(menu.getType());
			propertyFld.setValue(menu.getKey());

			nameFld.setRequired();
			codeFld.setReadOnly(true);
			return menu;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			codeFld.setRequired();
			nameFld.setRequired();
			activeFld.setValue(true);

			Menu parentEntity = getParentEntity();
			if (parentEntity != null) {
				menuFld.setValue(parentEntity);
				menuFld.setEnabled(false);
			}
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			MenuVO vo = new MenuVO();

			vo.setId(menu == null ? null : menu.getId());
			vo.setCode(codeFld.getValue());
			vo.setName(nameFld.getValue());
			vo.setActive(activeFld.getValue());
			vo.setViewId(viewFld.getItemId());
			vo.setParentMenuId(menuFld.getItemId());
			vo.setType(typeFld.getItemId());
			vo.setKey(propertyFld.getValue());

			return vo;
		}
	}
}
