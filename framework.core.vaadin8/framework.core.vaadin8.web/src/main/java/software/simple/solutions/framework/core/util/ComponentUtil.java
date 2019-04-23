package software.simple.solutions.framework.core.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;

import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.components.CDecimalField;
import software.simple.solutions.framework.core.components.CDiscreetNumberField;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.CTextField;
import software.simple.solutions.framework.core.components.IField;

public class ComponentUtil {

	private static final Logger logger = LogManager.getLogger(ComponentUtil.class);

	public static CGridLayout createGrid() {
		CGridLayout gridLayout = new CGridLayout();
		gridLayout.setWidth("-1px");
		gridLayout.setHideEmptyRowsAndColumns(true);
		gridLayout.setSpacing(true);
		return gridLayout;
	}

	public static <T> T addGridComponent(CGridLayout gridLayout, String key, Class<T> cl, int column, int row) {
		if (gridLayout.getColumns() < (column + 1)) {
			gridLayout.setColumns(column + 1);
		}
		if (gridLayout.getRows() < (row + 1)) {
			gridLayout.setRows(row + 1);
		}

		// CaptionLabel captionLabel = new CaptionLabel();
		// captionLabel.setWidth("-1px");
		// captionLabel.setValueByKey(key);
		// gridLayout.addComponent(captionLabel, column, row);

		T component = null;
		try {
			component = cl.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		gridLayout.addComponent(((Component) component), column + 1, row);

		if (component instanceof CTextField || component instanceof CTextArea
				|| component instanceof CDiscreetNumberField || component instanceof CDecimalField
				|| component instanceof CComboBox) {
			((Component) component).setWidth("300px");
		}
		if (component instanceof CCheckBox) {
			gridLayout.setComponentAlignment(((Component) component), Alignment.BOTTOM_LEFT);
		}
		((IField) component).setCaptionByKey(key);
		return component;
	}

	public static <T> T addGridComponent(CGridLayout gridLayout, Class<T> cl, int column, int row) {
		T component = null;
		try {
			component = cl.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
		if (component instanceof CTextField || component instanceof CDiscreetNumberField
				|| component instanceof CDecimalField || component instanceof CComboBox) {
			((Component) component).setWidth("200px");
		}

		gridLayout.addComponent(((Component) component), column, row);
		return component;
	}

}
