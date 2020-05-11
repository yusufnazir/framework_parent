package software.simple.solutions.framework.core.web.components;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog;
import com.vaadin.flow.component.dialog.GeneratedVaadinDialog.OpenedChangeEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.server.VaadinSession;

import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.IMappedSuperClass;
import software.simple.solutions.framework.core.entities.Menu;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PopUpMode;
import software.simple.solutions.framework.core.service.IMenuService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.web.AbstractBaseView;
import software.simple.solutions.framework.core.web.SimpleSolutionsMenuItem;
import software.simple.solutions.framework.core.web.ViewUtil;
import software.simple.solutions.framework.core.web.lookup.LookUpHolder;
import software.simple.solutions.framework.core.web.lookup.ValueHolderField;

public class LookUpField<E, T extends IMappedSuperClass> extends Composite<Div> implements HasSize
// extends CustomField<T>
{

	private static final long serialVersionUID = -6953072556831688132L;

	private static final Logger logger = LogManager.getLogger(LookUpField.class);

	private CDialog window;
	private VerticalLayout layout;
	private Menu menu;
	private CButton lookUpBtn;
	private CButton anchorBtn;
	private CButton clearUpBtn;
	private AbstractBaseView view;
	private T entity;
	private Class<?> viewClass;
	private SessionHolder sessionHolder;
	// private Object parentEntity;
	private Class<?> entityClass;
	private Button closeBtn;
	private CTextField textField;
	private Map<String, Object> referenceKeys;
	private boolean popUpMode = false;
	private Label caption;
	private ValueHolderField<T> valueHolderField;

	private HorizontalLayout mainLayout;

	public LookUpField() {
		super();
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		getElement().getStyle().set("--lumo-text-field-size", "var(--lumo-size-s)");
		getElement().getStyle().set("display", "inline");

		caption = new Label();
		caption.addClassName("my-custom-caption-label");
		valueHolderField = new ValueHolderField<>();

		mainLayout = new HorizontalLayout();
		mainLayout.setSpacing(false);

		getContent().add(caption, mainLayout);

		textField = new CTextField();
		textField.setReadOnly(true);
		textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
		textField.getStyle().set("--lumo-text-field-size", "var(--lumo-size-s)");
		mainLayout.add(textField);
		textField.setWidth("100%");

		lookUpBtn = new CButton();
		lookUpBtn.setIcon(new Icon(VaadinIcon.ELLIPSIS_DOTS_H));
		lookUpBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		lookUpBtn.getStyle().set("border", "1px solid");
		lookUpBtn.getStyle().set("--lumo-button-size", "var(--lumo-size-s)");
		mainLayout.add(lookUpBtn);
		lookUpBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 6165251315142630780L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				try {
					IMenuService menuService = ContextProvider.getBean(IMenuService.class);
					menu = menuService.getLookUpByViewClass(sessionHolder.getSelectedRole().getId(),
							viewClass.getName());
					if (popUpMode) {
						window = new CDialog();

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

					} else {
						BehaviorSubject<LookUpHolder> behaviorSubject = sessionHolder
								.getReferenceKey(ReferenceKey.LOOKUP_FIELD_SELECT_OBSERVEABLE);
						LookUpHolder lookUpHolder = new LookUpHolder();
						lookUpHolder.setViewClass(viewClass);
						lookUpHolder.setLookUpField(LookUpField.this);
						behaviorSubject.onNext(lookUpHolder);
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					// new MessageWindowHandler(e);
				}
			}
		});

		anchorBtn = new CButton();
		anchorBtn.setIcon(new Icon(VaadinIcon.LINK));
		anchorBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		anchorBtn.getStyle().set("border", "1px solid");
		anchorBtn.getStyle().set("--lumo-button-size", "var(--lumo-size-s)");
		anchorBtn.setVisible(false);
		mainLayout.add(anchorBtn);
		anchorBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 7585289128374363839L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				try {
					IMenuService menuService = ContextProvider.getBean(IMenuService.class);
					menu = menuService.getLookUpByViewClass(sessionHolder.getSelectedRole().getId(),
							viewClass.getName());
					BehaviorSubject<LookUpHolder> behaviorSubject = sessionHolder
							.getReferenceKey(ReferenceKey.LOOKUP_FIELD_LINK_OBSERVEABLE);
					LookUpHolder lookUpHolder = new LookUpHolder();
					lookUpHolder.setMenu(menu);
					lookUpHolder.setParentEntity(entity);
					behaviorSubject.onNext(lookUpHolder);
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
		clearUpBtn.getStyle().set("--lumo-button-size", "var(--lumo-size-s)");
		mainLayout.add(clearUpBtn);
		clearUpBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = 7585289128374363839L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				textField.clear();
				anchorBtn.setVisible(false);
				entity = null;
				window.setData(false);
			}
		});
	}

	private void createContent() throws FrameworkException {
		if (viewClass != null) {

			SimpleSolutionsMenuItem simpleSolutionsMenuItem = new SimpleSolutionsMenuItem(menu);
			simpleSolutionsMenuItem.setReferenceKeys(referenceKeys);
			// simpleSolutionsMenuItem.setParentEntity(getParentEntity());
			view = ViewUtil.initView(simpleSolutionsMenuItem, PopUpMode.POPUP, sessionHolder.getSelectedRole().getId(),
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
								entity = view.getPopUpEntity();
								setValue(entity);
							}
						}
					});
		}
	}

	public void setValue(T value) {
		entity = value;
		if (value == null) {
			valueHolderField.setValue(value);
			textField.setValue("...");
			clearUpBtn.setVisible(false);
			anchorBtn.setVisible(false);
		} else {
			valueHolderField.setValue(value);
			textField.setValue(((IMappedSuperClass) value).getCaption());
			clearUpBtn.setVisible(true);
			anchorBtn.setVisible(true);
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

	// public T getParentEntity() {
	// return (T) parentEntity;
	// }

	// public void setParentEntity(Object parentEntity) {
	// this.parentEntity = parentEntity;
	// }

	// public void handleForParentEntity(T parentEntity) {
	// if (parentEntity != null &&
	// parentEntity.getClass().isAssignableFrom(entityClass)) {
	// setValue(parentEntity);
	// setEnabled(false);
	// }
	// }

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	public void setCaptionByKey(String key) {
		caption.setText(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
	}

	public void clear() {
		if (entity == null) {
			setValue(null);
		}
	}

	public void setEnabled(boolean enabled) {
		clearUpBtn.setEnabled(enabled);
		lookUpBtn.setEnabled(enabled);
	}

	public void disableForParent() {
		setEnabled(false);
		anchorBtn.setEnabled(false);
	}

	public Map<String, Object> getReferenceKeys() {
		return referenceKeys;
	}

	public void setReferenceKeys(Map<String, Object> referenceKeys) {
		this.referenceKeys = referenceKeys;
	}

	public boolean isPopUpMode() {
		return popUpMode;
	}

	public void setPopUpMode(boolean popUpMode) {
		this.popUpMode = popUpMode;
	}

	public void addValueChangeListener(ValueChangeListener<ValueChangeEvent<T>> valueChangeListener) {
		valueHolderField.addValueChangeListener(valueChangeListener);
	}

	@Override
	public void setWidth(String width) {
		mainLayout.setWidth(width);
	}

	public void setRequiredIndicatorVisible(boolean b) {
		if (b) {
			caption.addClassName("my-custom-caption-label-required");
		} else {
			caption.removeClassName("my-custom-caption-label-required");
		}
	}

	// @Override
	// protected T generateModelValue() {
	// return (T) entity;
	// }

	// @Override
	// protected void setPresentationValue(T value) {
	// entity = value;
	// if (value == null) {
	// textField.setValue("...");
	// clearUpBtn.setVisible(false);
	// anchorBtn.setVisible(false);
	// } else {
	// textField.setValue(((IMappedSuperClass) value).getCaption());
	// clearUpBtn.setVisible(true);
	// anchorBtn.setVisible(true);
	// }
	// }

}
