package software.simple.solutions.framework.core.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog.OpenedChangeEvent;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.entities.IMappedSuperClass;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.web.components.CButton;
import software.simple.solutions.framework.core.web.components.CDialog;
import software.simple.solutions.framework.core.web.components.CTextField;

public class LookUpField<E, T extends IMappedSuperClass> extends CustomField<T> implements IParentEntity<T> {

	private static final long serialVersionUID = -6953072556831688132L;

	private static final Logger logger = LogManager.getLogger(LookUpField.class);

	private CDialog window;
	private VerticalLayout layout;
	private Menu menu;
	private CButton lookUpBtn;
	private CButton clearUpBtn;
	private AbstractBaseView view;
	private Object entity;
	private Class<?> viewClass;
	private SessionHolder sessionHolder;
	private Object parentEntity;
	private Class<?> entityClass;
	private Button closeBtn;
	private CTextField textField;

	public LookUpField() {
		super();
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		getElement().getStyle().set("--lumo-text-field-size", "0");
		getElement().getStyle().set("display", "inline");
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setSpacing(false);
		add(horizontalLayout);

		textField = new CTextField();
		textField.setReadOnly(true);
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		textField.getStyle().set("--lumo-text-field-size", "1.4rem");
		horizontalLayout.add(textField);
		textField.setWidth("100%");

		lookUpBtn = new CButton();
		lookUpBtn.setIcon(new Icon(VaadinIcon.ELLIPSIS_DOTS_H));
		lookUpBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		lookUpBtn.getStyle().set("border", "1px solid");
		horizontalLayout.add(lookUpBtn);
		lookUpBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 6165251315142630780L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				window = new CDialog();

				try {
					IMenuService menuService = ContextProvider.getBean(IMenuService.class);
					menu = menuService.getLookUpByViewClass(sessionHolder.getSelectedRole().getId(),
							viewClass.getName());
					window.setHeight("calc(90vh - (2*var(--lumo-space-m)))");
					window.setWidth("calc(90vw - (4*var(--lumo-space-m)))");
					window.setCloseOnEsc(true);
					window.setCloseOnOutsideClick(false);
					layout = new VerticalLayout();
					layout.setHeight("100%");
					// layout.setMargin(true);
					HorizontalLayout headerLayout = new HorizontalLayout();
					headerLayout.setWidth("100%");
					closeBtn = new Button("X");
					closeBtn.getStyle().set("background", "red").set("color", "white");
					headerLayout.add(closeBtn);
					headerLayout.setJustifyContentMode(JustifyContentMode.END);
					layout.add(headerLayout);
					window.add(layout);

					createContent();
					window.open();
					closeBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

						private static final long serialVersionUID = 2163189088978233369L;

						@Override
						public void onComponentEvent(ClickEvent<Button> event) {
							window.close();
						}
					});

				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					// new MessageWindowHandler(e);
				}

			}
		});

		clearUpBtn = new CButton();
		clearUpBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		clearUpBtn.setIcon(new Icon(VaadinIcon.CLOSE));
		clearUpBtn.getStyle().set("border", "1px solid");
		horizontalLayout.add(clearUpBtn);
		clearUpBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 7585289128374363839L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				textField.clear();
				entity = null;
				window.setData(false);
			}
		});
	}

	private void createContent() throws FrameworkException {
		if (viewClass != null) {

			SimpleSolutionsMenuItem simpleSolutionsMenuItem = new SimpleSolutionsMenuItem(menu);
			simpleSolutionsMenuItem.setParentEntity(getParentEntity());
			view = ViewUtil.initView(simpleSolutionsMenuItem, true, sessionHolder.getSelectedRole().getId(),
					sessionHolder.getApplicationUser().getId());
			view.setPopUpEntity(entity);
			layout.add(view);
			view.executeBuild();
			view.setPopUpWindow(window);
			window.addOpenedChangeListener(
					new ComponentEventListener<GeneratedVaadinDialog.OpenedChangeEvent<Dialog>>() {

						private static final long serialVersionUID = 5633661963692930563L;

						@Override
						public void onComponentEvent(OpenedChangeEvent<Dialog> event) {
							boolean opened = event.isOpened();
							Boolean data = window.getData();
							if (!opened && data != null && data) {
								setValue(view.getPopUpEntity());
							}
						}
					});
		}
	}

	public void setViewClass(Class<?> viewClass) {
		this.viewClass = viewClass;
	}

	public E getItemId() {
		if (entity == null) {
			return null;
		}
		return (E) ((IMappedSuperClass) entity).getId();
	}

	public T getParentEntity() {
		return (T) parentEntity;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

	public void handleForParentEntity(T parentEntity) {
		if (parentEntity != null && parentEntity.getClass().isAssignableFrom(entityClass)) {
			setValue(parentEntity);
			setEnabled(false);
		}
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public void setCaptionByKey(String key) {
		setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
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

	public void setReadOnly(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T getValue() {
		return (T) entity;
	}

	@Override
	protected T generateModelValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setPresentationValue(T value) {
		if (value == null) {
			textField.setValue("...");
			clearUpBtn.setVisible(false);
		} else {
			textField.setValue(((IMappedSuperClass) value).getCaption());
			clearUpBtn.setVisible(true);
		}
	}

}
