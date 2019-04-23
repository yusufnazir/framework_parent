package software.simple.solutions.framework.core.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.icons.CxodeIcons;

public class SoftDeleteCheckBox extends CustomField<Boolean> {

	private static final long serialVersionUID = 4509100564680775372L;
	private CCheckBox checkFld;
	private CButton deletedFld;
	private HorizontalLayout horizontalLayout;

	public SoftDeleteCheckBox() {
		super();
		initContent();
		setWidth("-1px");
	}

	@Override
	public Boolean getValue() {
		return checkFld.getValue();
	}

	@Override
	protected Component initContent() {
		if (horizontalLayout == null) {
			horizontalLayout = new HorizontalLayout();
			horizontalLayout.setMargin(false);
			horizontalLayout.setSpacing(false);
			checkFld = new CCheckBox();

			deletedFld = new CButton();
			deletedFld.setVisible(false);
			deletedFld.addStyleName(Style.RESIZED_ICON);
			deletedFld.addStyleName(ValoTheme.BUTTON_BORDERLESS);

			horizontalLayout.addComponents(checkFld, deletedFld);
			horizontalLayout.setComponentAlignment(checkFld, Alignment.MIDDLE_LEFT);
		}

		return horizontalLayout;
	}

	@Override
	protected void doSetValue(Boolean value) {
		checkFld.setValue(value);
	}

	public void showSoftDeleteIndicationFld(Boolean softDeleted) {
		SessionHolder sessionHolder = (SessionHolder) UI.getCurrent().getData();
		Long selectedRoleId = sessionHolder.getSelectedRole().getId();
		if (selectedRoleId.compareTo(1L) == 0) {
			deletedFld.setVisible(true);
			if (softDeleted == null || !softDeleted) {
				deletedFld.setIcon(CxodeIcons.GREEN_DOT);
			} else {
				deletedFld.setIcon(CxodeIcons.RED_DOT);
			}
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		checkFld.setReadOnly(readOnly);
	}

	@Override
	public void addStyleName(String style) {
		checkFld.addStyleName(style);
	}

}
