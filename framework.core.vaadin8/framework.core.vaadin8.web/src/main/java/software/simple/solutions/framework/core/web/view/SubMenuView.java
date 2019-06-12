package software.simple.solutions.framework.core.web.view;

import java.util.List;

import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.SubMenu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.ComboItem;
import software.simple.solutions.framework.core.properties.SubMenuProperty;
import software.simple.solutions.framework.core.service.ISubMenuService;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.service.facade.SubMenuServiceFacade;
import software.simple.solutions.framework.core.util.ArrayUtil;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.SubMenuVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class SubMenuView extends BasicTemplate<SubMenu> {

	private static final long serialVersionUID = 6503015064562511801L;

	public SubMenuView() {
		setEntityClass(SubMenu.class);
		setServiceClass(SubMenuServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(SubMenu::getParentMenu, SubMenuProperty.PARENT_MENU);
		addContainerProperty(SubMenu::getChildMenu, SubMenuProperty.CHILD_MENU);
		addContainerProperty(SubMenu::getType, SubMenuProperty.TYPE);
		addContainerProperty(SubMenu::getIndex, SubMenuProperty.INDEX);
	}

	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CComboBox parentFld;
		private CComboBox childFld;
		private CComboBox typeFld;
		private ActiveSelect activeFld;

		private Menu parentMenu;

		@Override
		public void executeBuild() throws FrameworkException {
			parentFld = addField(CComboBox.class, SubMenuProperty.PARENT_MENU, 0, 0);
			childFld = addField(CComboBox.class, SubMenuProperty.CHILD_MENU, 0, 1);
			typeFld = addField(CComboBox.class, SubMenuProperty.TYPE, 1, 0);
			activeFld = addField(ActiveSelect.class, SubMenuProperty.ACTIVE, 1, 1);

			parentMenu = getParentEntity();

			initMenuFlds();
		}

		private void initMenuFlds() throws FrameworkException {
			List<ComboItem> menusListing = MenuServiceFacade.get(UI.getCurrent()).getForListing(Menu.class, null);

			parentFld.setItems(menusListing, parentMenu == null ? null : parentMenu.getId());
			if (parentMenu != null) {
				parentFld.setReadOnly(true);
			}
			childFld.setItems(menusListing);
		}

		@Override
		public Object getCriteria() {
			SubMenuVO vo = new SubMenuVO();
			vo.setActive(activeFld.getItemId());
			vo.setParentMenuId(parentFld.getItemId());
			vo.setChildMenuId(childFld.getItemId());
			vo.setType(typeFld.getItemId());

			return vo;
		}
	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CComboBox parentFld;
		private CComboBox childFld;
		private CComboBox typeFld;
		private CDiscreetNumberField indexFld;
		private CCheckBox activeFld;

		private Menu parentMenu;
		private SubMenu subMenu;

		public Form() {
			super();
		}

		@Override
		public void executeBuild() {
			formGrid = createFormGrid();
			addComponent(formGrid);

			parentMenu = getParentEntity();
		}

		private CGridLayout createFormGrid() {
			formGrid = ComponentUtil.createGrid();

			parentFld = formGrid.addField(CComboBox.class, SubMenuProperty.PARENT_MENU, 0, 0);

			childFld = formGrid.addField(CComboBox.class, SubMenuProperty.CHILD_MENU, 0, 1);

			typeFld = formGrid.addField(CComboBox.class, SubMenuProperty.TYPE, 1, 0);

			indexFld = formGrid.addField(CDiscreetNumberField.class, SubMenuProperty.INDEX, 1, 1);

			activeFld = formGrid.addField(CCheckBox.class, SubMenuProperty.ACTIVE, 2, 0);

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public SubMenu setFormValues(Object entity) throws FrameworkException {
			subMenu = (SubMenu) entity;
			createParentMenuListing(subMenu.getParentMenu().getId());
			createChildViewListing(subMenu.getChildMenu().getId());
			activeFld.setValue(subMenu.getActive());
			return subMenu;
		}

		private void createParentMenuListing(Long menuId) throws FrameworkException {
			List<ComboItem> listing = MenuServiceFacade.get(UI.getCurrent()).getForListing(Menu.class,
					ArrayUtil.asList(menuId), true);
			parentFld.setItems(listing, menuId);
		}

		private void createChildViewListing(Long menuId) throws FrameworkException {
			List<ComboItem> listing = MenuServiceFacade.get(UI.getCurrent()).getForListing(Menu.class,
					ArrayUtil.asList(menuId), true);
			childFld.setItems(listing, menuId);
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			parentFld.setRequired();
			childFld.setRequired();
			activeFld.setValue(true);
			createParentMenuListing(parentMenu == null ? null : parentMenu.getId());
			createChildViewListing(null);

			if (parentMenu != null) {
				parentFld.setReadOnly(true);
			}
		}

		// @Override
		// public boolean validate() throws FrameworkException {
		// if (parentFld.getItemId() == null || childFld.getItemId() == null) {
		// return false;
		// }
		//
		// return true;
		// }

		@Override
		public Object getFormValues() throws FrameworkException {
			SubMenuVO vo = new SubMenuVO();

			vo.setId(subMenu == null ? null : subMenu.getId());
			vo.setParentMenuId(parentFld.getItemId());
			vo.setChildMenuId(childFld.getItemId());
			vo.setType(typeFld.getItemId());
			vo.setIndex(indexFld.getLongValue());
			vo.setActive(activeFld.getValue());

			return vo;
		}
	}
}
