package software.simple.solutions.framework.core.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.appreciated.css.grid.interfaces.TemplateRowsAndColsUnit;
import com.github.appreciated.css.grid.sizes.Auto;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.components.filter.CDateIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDateTimeIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDecimalNumberIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.filter.LongIntervalField;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.web.components.CPasswordField;
import software.simple.solutions.framework.core.web.components.CPopupDateField;
import software.simple.solutions.framework.core.web.components.CSplitDateField;
import software.simple.solutions.framework.core.web.components.CTextArea;
import software.simple.solutions.framework.core.web.components.CTextField;
import software.simple.solutions.framework.core.web.components.LookUpField;
import software.simple.solutions.framework.core.web.components.Table;

public abstract class FilterView extends HorizontalLayout implements Filterable, Eraseable, Referenceable, IView {

	private static final long serialVersionUID = -1682665002918497932L;
	private static final Logger logger = LogManager.getLogger(FilterView.class);

	private Map<String, Object> referenceKeys;
	protected SessionHolder sessionHolder;
	private ViewDetail viewDetail;
	private Object parentEntity;
	// private FlexibleGridLayout layout;
	// private GridLayout gridLayout;
	private Table gridLayout;

	public FilterView() {
		getElement().getStyle().set("overflow", "auto");
		referenceKeys = new HashMap<String, Object>();
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		setSpacing(true);
		// layout = new FluentGridLayout()
		// .withTemplateColumns(new Flex(1),new Flex(1),new Flex(1))
		// .withPadding(true).withSpacing(true)
		// .withOverflow(GridLayoutComponent.Overflow.AUTO);

		gridLayout = new Table(4, 4);
		// gridLayout = new GridLayout();

		// layout = new FlexibleGridLayout()
		// .withColumns(RepeatMode.AUTO_FILL, new MinMax(new Length("250px"),
		// new Flex(1)))
		// .withAutoRows(new Length("-1px")).withPadding(true).withSpacing(true)
		// .withAutoFlow(GridLayoutComponent.AutoFlow.ROW_DENSE).withOverflow(GridLayoutComponent.Overflow.AUTO);

		gridLayout.setSizeUndefined();
		// gridLayout.setSpacing(true);
		// gridLayout.setMargin(true);
		setSizeFull();
		add(gridLayout);
	}

	@Override
	public void erase() {
		// Iterator<Component> iterator = iterator();
		// while (iterator.hasNext()) {
		// Component component = iterator.next();
		// if (component instanceof IField) {
		// ((IField) component).clear();
		// }
		// }
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
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.COULD_NOT_CREATE_FILTER, e);
		}
	}

	public void executeBuild() throws FrameworkException {
	}

	@SuppressWarnings("unchecked")
	public <T> T createField(Class<T> cl, String key) {
		return null;
		// IField field = null;
		// try {
		// field = (IField) cl.newInstance();
		// field.setCaptionByKey(key);
		// ((AbstractComponent) field).setWidth("200px");
		// addComponent((Component) field);
		// } catch (InstantiationException | IllegalAccessException e) {
		// logger.error(e.getMessage(), e);
		// }
		// return (T) field;
	}

	@SuppressWarnings("unchecked")
	public <T> T createField(Class<T> cl, String key, int column, int row) {
		return null;
		// IField field = null;
		// try {
		// field = (IField) cl.newInstance();
		// field.setCaptionByKey(key);
		// ((AbstractComponent) field).setWidth("200px");
		// addComponent((Component) field, column, row);
		// } catch (InstantiationException | IllegalAccessException e) {
		// logger.error(e.getMessage(), e);
		// }
		// return (T) field;
	}

	public <T extends Component> T addField(Class<?> componentClass, String key, int column, int row) {
		Component component = null;
		try {
			component = (Component) componentClass.newInstance();
			setUpComponent(component, key);
			gridLayout.add(component, column, row);
			// gridLayout.setRowAlign(component, RowAlign.STRETCH);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}

		return (T) component;
	}

	private void setUpComponent(Component component, String key) {
		if (component instanceof LookUpField) {
			((HasSize) component).setWidth("300px");
			((LookUpField) component).setCaptionByKey(key);
			((LookUpField) component).setReferenceKeys(referenceKeys);
		} else if (component instanceof CTextField) {
			// ((HasSize) component).setWidth("300px");
			((CTextField) component).setCaptionByKey(key);
		} else if (component instanceof CTextArea) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CPasswordField) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CDiscreetNumberField) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CComboBox) {
			((HasSize) component).setWidth("300px");
			((CComboBox) component)
					.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
		} else if (component instanceof LongIntervalField) {
			((HasSize) component).setWidth("300px");
			((LongIntervalField) component).setCaptionByKey(key);
		} else if (component instanceof CDateTimeIntervalLayout) {
			((HasSize) component).setWidth("300px");
			((CDateTimeIntervalLayout) component).setCaptionByKey(key);
		} else if (component instanceof CDateIntervalLayout) {
			((HasSize) component).setWidth("300px");
			((CDateIntervalLayout) component).setCaptionByKey(key);
		} else if (component instanceof CPopupDateField || component instanceof CDecimalNumberIntervalLayout) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CStringIntervalLayout) {
			((HasSize) component).setWidth("300px");
			((CStringIntervalLayout) component).setCaptionByKey(key);
		} else if (component instanceof ActiveSelect) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CCheckBox) {
			((HasSize) component).setWidth("-1px");
		} else if (component instanceof CSplitDateField) {
			((CSplitDateField) component)
					.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
		} else {
			((HasSize) component).setWidth("-1px");
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getParentEntity() {
		return (T) parentEntity;
	}

	@SuppressWarnings("unchecked")
	public <T> T getIfParentEntity(Class<?> cl) {
		if (parentEntity != null && cl.isAssignableFrom(parentEntity.getClass())) {
			return (T) parentEntity;
		} else {
			return null;
		}
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

	public void setColumns(int columns) {
		List<TemplateRowsAndColsUnit> lengths = new ArrayList<TemplateRowsAndColsUnit>();
		for (int i = 0; i < columns; i++) {
			// lengths.add(new Length("250px"));
			lengths.add(new Auto());
		}
		// gridLayout.setTemplateColumns(lengths.toArray(new
		// TemplateRowsAndColsUnit[lengths.size()]));
	}

}
