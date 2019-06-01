package software.simple.solutions.framework.core.components;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;

public abstract class FilterView extends CGridLayout implements Filterable, Eraseable, Referenceable, IView {

	private static final long serialVersionUID = -1682665002918497932L;
	private static final Logger logger = LogManager.getLogger(FilterView.class);

	private Map<String, Object> referenceKeys;
	protected SessionHolder sessionHolder;
	private ViewDetail viewDetail;
	private Object parentEntity;

	public FilterView() {
		referenceKeys = new HashMap<String, Object>();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		setSpacing(true);
		addStyleName(Style.MAIN_VIEW_HEADER_MARGINS);
		addStyleName(Style.FILTER_VIEW_OVERFLOW);
		setHideEmptyRowsAndColumns(true);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void erase() {
		Iterator<Component> iterator = iterator();
		while (iterator.hasNext()) {
			Component component = iterator.next();
			if (component instanceof IField) {
				((IField) component).clear();
			}
		}
	}

	@Override
	public Object getCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCriteria(Object criteria) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> getReferenceKeys() {
		return referenceKeys;
	}

	@Override
	public void setReferenceKeys(Map<String, Object> referenceKeys) {
		this.referenceKeys = referenceKeys;
	}

	@Override
	public void addReferenceKey(String key, Object value) {
		referenceKeys.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getReferenceKey(String key) {
		return (T) referenceKeys.get(key);
	}

	/**
	 * @return the viewDetail
	 */
	public ViewDetail getViewDetail() {
		return viewDetail;
	}

	/**
	 * @param viewDetail
	 *            the viewDetail to set
	 */
	public void setViewDetail(ViewDetail viewDetail) {
		this.viewDetail = viewDetail;
	}

	public static FilterView instantiate(Class<? extends FilterView> filterClass) throws FrameworkException {
		try {
			FilterView filterView = filterClass.newInstance();
			return filterView;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_FILTER, e);
		}
	}

	public void executeBuild() throws FrameworkException {
	}

	@SuppressWarnings("unchecked")
	public <T> T createField(Class<T> cl, String key) {
		IField field = null;
		try {
			field = (IField) cl.newInstance();
			field.setCaptionByKey(key);
			((AbstractComponent) field).setWidth("200px");
			addComponent((Component) field);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return (T) field;
	}

	@SuppressWarnings("unchecked")
	public <T> T createField(Class<T> cl, String key, int column, int row) {
		IField field = null;
		try {
			field = (IField) cl.newInstance();
			field.setCaptionByKey(key);
			((AbstractComponent) field).setWidth("200px");
			addComponent((Component) field, column, row);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		return (T) field;
	}

	@Override
	public <T extends Component> T addField(Class<?> componentClass, String key, int column, int row) {
		Component component = super.addField(componentClass, key, column, row);
		if (component instanceof IParentEntity) {
			((IParentEntity) component).handleForParentEntity(getParentEntity());
		}
		return (T) component;
	}

	@Override
	public <T extends Component> T addField(Class<?> componentClass, int column, int row) {
		Component component = super.addField(componentClass, column, row);
		if (component instanceof LookUpField) {
			((LookUpField) component).handleForParentEntity(getParentEntity());
		}
		return (T) component;
	}

	@SuppressWarnings("unchecked")
	public <T> T getParentEntity() {
		return (T) parentEntity;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

}
