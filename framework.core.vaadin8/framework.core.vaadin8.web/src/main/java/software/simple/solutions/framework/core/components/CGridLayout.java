package software.simple.solutions.framework.core.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;

import software.simple.solutions.framework.core.components.filter.CDateIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDateTimeIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDecimalNumberIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CDiscreetNumberIntervalLayout;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;

public class CGridLayout extends GridLayout {

	private static final long serialVersionUID = 7606410497467535135L;

	private static final Logger logger = LogManager.getLogger(CGridLayout.class);

	@Override
	public void addComponent(Component component, int column, int row) throws OverlapsException, OutOfBoundsException {
		if (getColumns() < (column + 1)) {
			setColumns(column + 1);
		}
		if (getRows() < (row + 1)) {
			setRows(row + 1);
		}
		super.addComponent(component, column, row);
	}

	@Override
	public void addComponent(Component component, int column1, int row1, int column2, int row2)
			throws OverlapsException, OutOfBoundsException {
		if (getColumns() < (column1 + 1)) {
			setColumns(column1 + 1);
		}
		if (getRows() < (row1 + 1)) {
			setRows(row1 + 1);
		}
		if (getColumns() < (column2 + 1)) {
			setColumns(column2 + 1);
		}
		if (getRows() < (row2 + 1)) {
			setRows(row2 + 1);
		}
		super.addComponent(component, column1, row1, column2, row2);
	}

	public <T extends Component> T addField(Class<?> componentClass, int column, int row) {
		return addField(componentClass, null, column, row);
	}

	public <T extends Component> T addField(Class<?> componentClass, String key, int column, int row, int columnEnd,
			int rowEnd) {
		Component component = null;
		try {
			component = (Component) componentClass.newInstance();

			int newColumn = column * 2;
			int newColumnEnd = columnEnd * 2;

			if (key != null) {
				CaptionLabel captionLabel = addCaptionLabel(key, newColumn, row);
				if (component instanceof Captionable) {
					((Captionable) component).setLabel(captionLabel);
				}
			}
			setUpComponent(component);

			addComponent((Component) component, newColumn + 1, row, newColumnEnd + 1, rowEnd);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}

		return (T) component;
	}

	public <T extends Component> T addField(Class<?> componentClass, int column, int row, int columnEnd, int rowEnd) {
		return addField(componentClass, null, column, row, columnEnd, rowEnd);
	}

	public <T extends Component> T addField(Class<?> componentClass, String key, int column, int row) {
		Component component = null;
		try {
			component = (Component) componentClass.newInstance();

			int newColumn = column * 2;

			if (key != null) {
				CaptionLabel label = addCaptionLabel(key, newColumn, row);
				if (component instanceof Captionable) {
					((Captionable) component).setLabel(label);
				}
			}
			setUpComponent(component);

			addComponent((Component) component, newColumn + 1, row);
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}

		return (T) component;
	}

	private void setUpComponent(Component component) {
		if (component instanceof LookUpField) {
			((AbstractComponent) component).setWidth("300px");
		} else if (component instanceof CTextField) {
			((AbstractComponent) component).setWidth("300px");
		} else if (component instanceof CTextArea) {
			((AbstractComponent) component).setWidth("300px");
			((CTextArea) component).setRows(1);
		} else if (component instanceof CPasswordField) {
			((AbstractComponent) component).setWidth("300px");
		} else if (component instanceof CDiscreetNumberField) {
			((AbstractComponent) component).setWidth("300px");
		} else if (component instanceof CComboBox) {
			((AbstractComponent) component).setWidth("300px");
		} else if (component instanceof CPopupDateField || component instanceof CDateIntervalLayout
				|| component instanceof CDateTimeIntervalLayout || component instanceof CDecimalNumberIntervalLayout
				|| component instanceof CStringIntervalLayout || component instanceof CDiscreetNumberIntervalLayout) {
			((AbstractComponent) component).setWidth("300px");
		} else if (component instanceof ActiveSelect) {
			((AbstractComponent) component).setWidth("150px");
		} else if (component instanceof CCheckBox) {
			((AbstractComponent) component).setWidth("-1px");
		} else {
			((AbstractComponent) component).setWidth("-1px");
		}
	}

	private CaptionLabel addCaptionLabel(String key, int column, int row) {
		CaptionLabel captionLbl = new CaptionLabel(key);
		addComponent(captionLbl, column, row);
		return captionLbl;
	}

}
