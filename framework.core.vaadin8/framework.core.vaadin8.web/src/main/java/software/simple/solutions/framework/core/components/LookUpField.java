package software.simple.solutions.framework.core.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.entities.IMappedSuperClass;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.SimpleSolutionsMenuItem;
import software.simple.solutions.framework.core.web.ViewUtil;

public class LookUpField extends CustomField<Object> implements IField, IParentEntity {

	private static final long serialVersionUID = -6953072556831688132L;

	private static final Logger logger = LogManager.getLogger(LookUpField.class);

	private Window window;
	private VerticalLayout layout;
	private Menu menu;
	private CButton lookUpBtn;
	private CButton clearUpBtn;
	private CTextField valueFld;
	private AbstractBaseView view;
	private Object entity;
	private CssLayout cssLayout;
	private Class<?> viewClass;
	private SessionHolder sessionHolder;
	private Object parentEntity;
	private Class<?> entityClass;

	public LookUpField() {
		super();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		initContent();
	}

	@Override
	protected Component initContent() {
		if (cssLayout == null) {
			cssLayout = new CssLayout();
			cssLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
			cssLayout.addStyleName(Style.LOOKUP_VALUE_FIELD);
			cssLayout.setWidth("100%");
			lookUpBtn = new CButton();
			lookUpBtn.setCaption("...");
			lookUpBtn.setWidth("30px");
			lookUpBtn.addClickListener(new ClickListener() {

				private static final long serialVersionUID = -279867470227678611L;

				@Override
				public void buttonClick(ClickEvent event) {
					try {
						IMenuService menuService = ContextProvider.getBean(IMenuService.class);
						menu = menuService.getLookUpByViewClass(sessionHolder.getSelectedRole().getId(),
								viewClass.getName());
						// SimpleSolutionsEventBus.post(new
						// SimpleSolutionsEvent.LookupMenuSelectedEvent(menu));
						window = new Window();
						window.setWidth("90%");
						window.setHeight("90%");
						window.setClosable(true);
						window.setModal(true);
						window.center();
						layout = new VerticalLayout();
						layout.setHeight("100%");
						layout.setMargin(true);
						window.setContent(layout);

						createContent();
						UI.getCurrent().addWindow(window);
						window.focus();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						new MessageWindowHandler(e);
					}
				}
			});
			valueFld = new CTextField();
			valueFld.setWidth("100%");
			valueFld.setReadOnly(true);
			clearUpBtn = new CButton();
			clearUpBtn.setWidth("-1px");
			clearUpBtn.setIcon(VaadinIcons.CLOSE);
			clearUpBtn.setVisible(false);
			clearUpBtn.addClickListener(new ClickListener() {

				private static final long serialVersionUID = 4909404114126188608L;

				@Override
				public void buttonClick(ClickEvent event) {
					valueFld.setValue(null);
					setValue(null);
					clearUpBtn.setVisible(false);
					if (view != null) {
						view.setPopUpEntity(null);
					}
				}
			});

			cssLayout.addComponent(valueFld);
			cssLayout.addComponent(clearUpBtn);
			cssLayout.addComponent(lookUpBtn);
		}

		return cssLayout;
	}

	private void createContent() throws FrameworkException {
		if (viewClass != null) {

			SimpleSolutionsMenuItem simpleSolutionsMenuItem = new SimpleSolutionsMenuItem(menu);
			simpleSolutionsMenuItem.setParentEntity(getParentEntity());
			view = ViewUtil.initView(simpleSolutionsMenuItem, true, sessionHolder.getSelectedRole().getId(),
					sessionHolder.getApplicationUser().getId());
			view.setPopUpWindow(window);
			view.setPopUpEntity(entity);
			layout.addComponent(view);
			window.setCaption(view.getViewDetail().getMenu().getName());
			window.addCloseListener(new CloseListener() {

				private static final long serialVersionUID = -6641666551842097904L;

				@Override
				public void windowClose(CloseEvent e) {
					setValue(view.getPopUpEntity());
				}
			});
		}
	}

	public void setSelectedValue() {
		if (entity == null) {
			valueFld.setValue(null);
			clearUpBtn.setVisible(false);
		} else {
			valueFld.setValue(((IMappedSuperClass) entity).getCaption());
			clearUpBtn.setVisible(true);
		}
	}

	public void setViewClass(Class<?> viewClass) {
		this.viewClass = viewClass;
	}

	@Override
	public Object getValue() {
		return entity;
	}

	@Override
	protected void doSetValue(Object value) {
		this.entity = value;
		setSelectedValue();
	}

	public <T> T getItemId() {
		if (entity == null) {
			return null;
		}
		return (T) ((IMappedSuperClass) entity).getId();
	}

	public <T> T getParentEntity() {
		return (T) parentEntity;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

	public void handleForParentEntity(Object parentEntity) {
		if (parentEntity != null && parentEntity.getClass().isAssignableFrom(entityClass)) {
			setValue(parentEntity);
			setEnabled(false);
		}
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public boolean isThisRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRequired() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefault() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCaptionByKey(String key) {
		super.setCaption(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	@Override
	public void clear() {
		if (entity == null) {
			setValue(null);
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		clearUpBtn.setEnabled(enabled);
		lookUpBtn.setEnabled(enabled);
	}

}
