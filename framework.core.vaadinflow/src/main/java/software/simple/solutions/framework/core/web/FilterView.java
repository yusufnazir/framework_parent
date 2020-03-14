package software.simple.solutions.framework.core.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.appreciated.css.grid.GridLayoutComponent;
import com.github.appreciated.css.grid.GridLayoutComponent.RowAlign;
import com.github.appreciated.css.grid.sizes.Flex;
import com.github.appreciated.css.grid.sizes.Length;
import com.github.appreciated.css.grid.sizes.MinMax;
import com.github.appreciated.css.grid.sizes.Repeat.RepeatMode;
import com.github.appreciated.layout.FlexibleGridLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;

import software.simple.solutions.framework.core.components.filter.CDateIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDateTimeIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDecimalNumberIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDiscreetNumberIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.util.SessionHolder;
import software.simple.solutions.framework.core.web.components.CCheckBox;
import software.simple.solutions.framework.core.web.components.CComboBox;
import software.simple.solutions.framework.core.web.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.web.components.CPasswordField;
import software.simple.solutions.framework.core.web.components.CPopupDateField;
import software.simple.solutions.framework.core.web.components.CTextArea;
import software.simple.solutions.framework.core.web.components.CTextField;

public abstract class FilterView extends HorizontalLayout implements Filterable, Eraseable, Referenceable, IView {

	private static final long serialVersionUID = -1682665002918497932L;
	private static final Logger logger = LogManager.getLogger(FilterView.class);

	private Map<String, Object> referenceKeys;
	protected SessionHolder sessionHolder;
	private ViewDetail viewDetail;
	private Object parentEntity;
	private FlexibleGridLayout layout;

	public FilterView() {
		referenceKeys = new HashMap<String, Object>();
		sessionHolder = (SessionHolder) VaadinSession.getCurrent().getAttribute(Constants.SESSION_HOLDER);
		setSpacing(true);
		// layout = new FluentGridLayout()
		// .withTemplateColumns(new Flex(1),new Flex(1),new Flex(1))
		// .withPadding(true).withSpacing(true)
		// .withOverflow(GridLayoutComponent.Overflow.AUTO);

		layout = new FlexibleGridLayout()
				.withColumns(RepeatMode.AUTO_FILL, new MinMax(new Length("250px"), new Flex(1)))
				.withAutoRows(new Length("-1px")).withPadding(true).withSpacing(true)
				.withAutoFlow(GridLayoutComponent.AutoFlow.ROW_DENSE).withOverflow(GridLayoutComponent.Overflow.AUTO);

		layout.setSizeFull();
		layout.setSpacing(true);
		setSizeFull();
		add(layout);
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
			throw new FrameworkException(SystemMessageProperty.COULD_NOT_CREATE_FILTER, e);
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

			int newColumn = column * 2;

			if (key != null) {
				// CaptionLabel label = addCaptionLabel(key, newColumn, row);
				// if (component instanceof Captionable) {
				// ((Captionable) component).setLabel(label);
				// }
			}
			setUpComponent(component, key);
			layout.add(component);
			// layout.setColumnAlign(component, ColumnAlign.START);
			layout.setRowAlign(component, RowAlign.STRETCH);

			// add((Component) component, newColumn + 1, row);
			// setComponentAlignment(component, Alignment.MIDDLE_LEFT);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}

		return (T) component;
	}

	private void setUpComponent(Component component, String key) {
		if (component instanceof LookUpField) {
			((HasSize) component).setWidth("-1px");
			((LookUpField) component).setCaptionByKey(key);
		} else if (component instanceof CTextField) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CTextArea) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CPasswordField) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CDiscreetNumberField) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CComboBox) {
			// ((HasSize) component).setWidth("300px");
			((CComboBox) component)
					.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
		} else if (component instanceof CPopupDateField || component instanceof CDateIntervalLayout
				|| component instanceof CDateTimeIntervalLayout || component instanceof CDecimalNumberIntervalLayout
				|| component instanceof CDiscreetNumberIntervalLayout) {
			((HasSize) component).setWidth("300px");
		} else if (component instanceof CStringIntervalLayout) {
			// ((HasSize) component).setWidth("300px");
			((CStringIntervalLayout) component)
					.setLabel(PropertyResolver.getPropertyValueByLocale(key, UI.getCurrent().getLocale()));
		} else if (component instanceof ActiveSelect) {
			((HasSize) component).setWidth("150px");
		} else if (component instanceof CCheckBox) {
			((HasSize) component).setWidth("-1px");
		} else {
			((HasSize) component).setWidth("-1px");
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getParentEntity() {
		return (T) parentEntity;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

	public class FlexibleGridLayoutExample extends HorizontalLayout {
		public FlexibleGridLayoutExample() {
			FlexibleGridLayout layout = new FlexibleGridLayout().withColumns(RepeatMode.AUTO_FILL, new Flex(1))
					// .withAutoRows(new Flex(1))
					// .withItems(
					// new ExampleCard(), new ExampleCard(), new ExampleCard(),
					// new ExampleCard(), new ExampleCard(),
					// new ExampleCard(), new ExampleCard(), new ExampleCard(),
					// new ExampleCard(), new ExampleCard(),
					// new ExampleCard(), new ExampleCard(), new ExampleCard(),
					// new ExampleCard(), new ExampleCard(), new ExampleCard(),
					// new ExampleCard(), new ExampleCard()
					// )
					.withPadding(true).withSpacing(true).withAutoFlow(GridLayoutComponent.AutoFlow.ROW_DENSE)
					.withOverflow(GridLayoutComponent.Overflow.AUTO);
			layout.setSizeFull();
			setSizeFull();
			add(layout);
		}
	}

}
