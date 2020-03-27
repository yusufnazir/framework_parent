package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.components.select.MenuTypeSelect;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.MenuProperty;
import software.simple.solutions.framework.core.service.facade.MenuServiceFacade;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.valueobjects.MenuVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.lookup.MenuLookUpField;
import software.simple.solutions.framework.core.web.lookup.ViewLookUpField;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.MenuForm;

@Route(value = Routes.MENU, layout = MainView.class)
public class MenuView extends BasicTemplate<Menu> {

	private static final long serialVersionUID = 6503015064562511801L;

	public MenuView() {
		setEntityClass(Menu.class);
		setServiceClass(MenuServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(MenuForm.class);
		setParentReferenceKey(ReferenceKey.MENU);
		setEditRoute(Routes.MENU_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Menu::getCode, MenuProperty.CODE);
		addContainerProperty(Menu::getName, MenuProperty.NAME);
		addContainerProperty(new ValueProvider<Menu, String>() {

			private static final long serialVersionUID = 1545795120968614647L;

			@Override
			public String apply(Menu source) {
				return source.getView() == null ? null : source.getView().getCaption();
			}
		}, MenuProperty.VIEW);
		addContainerProperty(new ValueProvider<Menu, String>() {

			private static final long serialVersionUID = -638179271001395425L;

			@Override
			public String apply(Menu source) {
				return source.getParentMenu() == null ? null : source.getParentMenu().getCaption();
			}
		}, MenuProperty.PARENT_MENU);
		addContainerProperty(new ValueProvider<Menu, String>() {

			private static final long serialVersionUID = 8588191037441066866L;

			@Override
			public String apply(Menu source) {
				if (source.getType() == null) {
					return null;
				}
				return PropertyResolver.getPropertyValueByLocale(MenuProperty.TYPE + "." + source.getType(),
						UI.getCurrent().getLocale());
			}
		}, MenuProperty.TYPE);
		addContainerProperty(Menu::getIndex, MenuProperty.INDEX);
	}

	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout codeFld;
		private CStringIntervalLayout nameFld;
		private ViewLookUpField viewLookUpField;
		private MenuLookUpField menuLookUpField;
		private ActiveSelect activeFld;
		private CComboBox typeFld;
		// private List<ComboItem> viewListing;

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, MenuProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, MenuProperty.NAME, 0, 1);

			viewLookUpField = addField(ViewLookUpField.class, MenuProperty.VIEW, 1, 0);
			menuLookUpField = addField(MenuLookUpField.class, MenuProperty.PARENT_MENU, 1, 1);

			typeFld = addField(MenuTypeSelect.class, MenuProperty.TYPE, 2, 1);
			activeFld = addField(ActiveSelect.class, MenuProperty.ACTIVE, 2, 0);

			Menu menu = getIfParentEntity(Menu.class);
			if (menu != null) {
				menuLookUpField.setValue(menu);
				menuLookUpField.disableForParent();
			}
			View view = getIfParentEntity(View.class);
			if (view != null) {
				viewLookUpField.setValue(view);
				viewLookUpField.disableForParent();
			}
		}

		@Override
		public Object getCriteria() {
			MenuVO vo = new MenuVO();
			vo.setActive(activeFld.getItemId());
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			vo.setViewId(viewLookUpField.getItemId());
			vo.setParentMenuId(menuLookUpField.getItemId());
			vo.setType(typeFld.getItemId());
			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
}
