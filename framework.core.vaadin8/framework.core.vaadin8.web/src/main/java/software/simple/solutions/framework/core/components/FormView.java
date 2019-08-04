package software.simple.solutions.framework.core.components;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.valueobjects.SuperVO;
import software.simple.solutions.framework.core.web.SimpleSolutionsMenuItem;

public abstract class FormView extends VerticalLayout implements IView, IForm, IDetail,
		// Referenceable,
		Validateable {

	private static final long serialVersionUID = 9041981713340533644L;
	private static final Logger logger = LogManager.getLogger(FormView.class);

	private ViewDetail viewDetail;
	private ConcurrentMap<String, Object> referenceKeys;
	protected SessionHolder sessionHolder;
	private String validationMessage;
	private Object parentEntity;
	private Object selectedEntity;

	public FormView() {
		super();
		setMargin(false);
		setWidth("-1px");
		addStyleName(Style.MAIN_VIEW_HEADER_MARGINS);
		referenceKeys = new ConcurrentHashMap<String, Object>();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		validationMessage = "system.notif.validation.error";
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	/**
	 * Creates an instance of the value object extending {@link SuperVO}.
	 * <font style="color:red"><b>important!!! the {@link SuperVO#setId(Long)}
	 * set id should always be set with the unique id for the value
	 * object</b></font>
	 */
	@Override
	public Object getFormValues() throws FrameworkException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T setFormValues(Object entity) throws FrameworkException {
		return null;
	}

	@Override
	public boolean validate() throws FrameworkException {
		return true;
	}

	@Override
	public void handleNewForm() throws FrameworkException {
		return;
	}

	@Override
	public void setViewDetail(ViewDetail viewDetail) {
		this.viewDetail = viewDetail;
	}

	@Override
	public ViewDetail getViewDetail() {
		return viewDetail;
	}

	public boolean handleClose(TabSheet tabsheet, Component tabContent) {
		return false;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	@Override
	public void executeBuild() throws FrameworkException {
		return;
	}

	public static FormView instantiate(Class<? extends FormView> formClass) throws FrameworkException {
		try {
			FormView formView = formClass.newInstance();
			return formView;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_VIEW, e);
		}
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

	@SuppressWarnings("unchecked")
	public <T> T getParentEntity() {
		return (T) parentEntity;
	}

	public <T> T getSelectedEntity() {
		return (T) selectedEntity;
	}

	public void setSelectedEntity(Object selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	public void addToReferenceKey(String key, Object value) {
		if(value!=null){
			referenceKeys.put(key, value);
		}
	}

	public <T> T getReferenceKey(String key) {
		return (T) referenceKeys.get(key);
	}

	public ConcurrentMap<String, Object> getReferenceKeys() {
		return referenceKeys;
	}

	public void setReferenceKeys(ConcurrentMap<String, Object> referenceKeys) {
		this.referenceKeys = referenceKeys;
	}
}
