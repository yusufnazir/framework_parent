package software.simple.solutions.framework.core.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.constants.Style;
import software.simple.solutions.framework.core.icons.CxodeIcons;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class ActionBar extends MenuBar implements SearchEvent {

	private static final long serialVersionUID = -204573107078009299L;

	private MenuItem saveBtn;
	private MenuItem backBtn;
	private MenuItem deleteBtn;
	private MenuItem restoreBtn;
	private MenuItem newBtn;
	private MenuItem printBtn;
	private MenuItem exportExcelBtn;
	private MenuItem searchBtn;
	private MenuItem eraseBtn;
	private MenuItem auditBtn;
	private MenuItem helpBtn;
	private MenuItem toggleAdvancedSearchBtn;
	private MenuItem copyButton;
	private MenuItem importButton;
	private MenuItem uploadDocumentBtn;
	private MenuItem infoMenuBtn;
	private ActionState actionState;

	private boolean saveHidden = false;
	private boolean cancelHidden = false;
	private boolean newHidden = false;
	private boolean deleteHidden = false;
	private boolean searchHidden = false;
	private boolean exportExcelHidden = false;
	private boolean processHidden = false;
	private boolean auditHidden = false;
	private boolean uploadHidden = false;
	private boolean eraseHidden = false;
	private boolean printHidden = false;
	private boolean copyHidden = false;
	private boolean infoHidden = false;

	private ActionErase actionErase;
	private ActionSearch actionSearch;

	public ActionBar() {
		buildMainLayout();
		init();
	}

	public void setActionState(ActionState actionState) {
		if (actionState == null) {
			actionState = new ActionState(null);
		}
		this.actionState = actionState;
		if (actionState != null) {
			deleteBtn.setEnabled(actionState.getDeleteEnabled());
			newBtn.setEnabled(actionState.getInsertEnabled());
			printBtn.setEnabled(actionState.getPrintEnabled());
			saveBtn.setEnabled(actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		}
	}

	public void authorizeCopy() {
		copyButton.setEnabled(true);
		if (!copyHidden) {
			copyButton.setVisible(true);
		}
	}

	public void authorizeSave() {
		Boolean saveEnabled = (actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		saveBtn.setEnabled(saveEnabled);
		if (saveEnabled && !saveHidden) {
			saveBtn.setVisible(true);
		}
	}

	public void authorizeImport() {
		importButton.setEnabled(actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		importButton.setVisible(true);
	}

	public void authorizeNew() {
		Boolean insertEnabled = actionState.getInsertEnabled();
		newBtn.setEnabled(insertEnabled);
		if (insertEnabled && !newHidden) {
			newBtn.setVisible(true);
		}
	}

	public void authorizeDelete() {
		Boolean deleteEnabled = actionState.getDeleteEnabled();
		deleteBtn.setEnabled(deleteEnabled);
		if (deleteEnabled && !deleteHidden) {
			deleteBtn.setVisible(true);
		}
	}

	public void authorizeRestore() {
		Boolean restoreEnabled = actionState.getDeleteEnabled();
		restoreBtn.setEnabled(restoreEnabled);
		if (restoreEnabled && !deleteHidden) {
			restoreBtn.setVisible(true);
		}
	}

	public void authorizeBack() {
		backBtn.setEnabled(true);
		if (!cancelHidden) {
			backBtn.setVisible(true);
		}
	}

	public void authorizeExportExcel() {
		exportExcelBtn.setEnabled(true);
		if (!exportExcelHidden) {
			exportExcelBtn.setVisible(true);
		}
	}

	public void authorizeUpload() {
		uploadDocumentBtn.setEnabled(true);
		if (!uploadHidden) {
			uploadDocumentBtn.setVisible(true);
		}
	}

	public void authorizeAudit() {
		auditBtn.setEnabled(true);
		if (!auditHidden) {
			auditBtn.setVisible(true);
		}
	}

	public void authorizeSearch() {
		searchBtn.setEnabled(true);
		toggleAdvancedSearchBtn.setEnabled(true);
		// searchFld.setEnabled(true);
		if (!searchHidden) {
			searchBtn.setVisible(true);
			toggleAdvancedSearchBtn.setVisible(true);
			// searchFld.setVisible(true);
		}
	}

	public void authorizeErase() {
		eraseBtn.setEnabled(true);
		if (!eraseHidden) {
			eraseBtn.setVisible(true);
		}
	}

	public void authorizePrint() {
		printBtn.setEnabled(true);
		if (!printHidden) {
			printBtn.setVisible(true);
		}
	}

	public void authorizeInfo() {
		infoMenuBtn.setEnabled(true);
		if (!infoHidden) {
			infoMenuBtn.setVisible(true);
		}
	}

	public void setDeleteDisabled() {
		deleteBtn.setEnabled(false);
		deleteBtn.setVisible(false);
		restoreBtn.setEnabled(false);
		restoreBtn.setVisible(false);
	}

	public void setNewDisabled() {
		newBtn.setEnabled(false);
		newBtn.setVisible(false);
	}

	public void setSaveDisabled() {
		saveBtn.setEnabled(false);
		saveBtn.setVisible(false);
	}

	public void setCancelDisabled() {
		backBtn.setEnabled(false);
		backBtn.setVisible(false);
	}

	public void setPrintDisabled() {
		printBtn.setEnabled(false);
		printBtn.setVisible(false);
	}

	public void setUploadDisabled() {
		uploadDocumentBtn.setEnabled(false);
		uploadDocumentBtn.setVisible(false);
	}

	public void setAuditDisabled() {
		auditBtn.setEnabled(false);
		auditBtn.setVisible(false);
	}

	public void setExportDisabled() {
		exportExcelBtn.setEnabled(false);
		exportExcelBtn.setVisible(false);
	}

	public void setEraseDisabled() {
		eraseBtn.setEnabled(false);
		eraseBtn.setVisible(false);
	}

	public void setCopyDisabled() {
		copyButton.setEnabled(false);
		copyButton.setVisible(false);
	}

	public void setHelpDisabled() {
		helpBtn.setEnabled(false);
		helpBtn.setVisible(false);
	}

	public void setBackDisabled() {
		backBtn.setEnabled(false);
		backBtn.setVisible(false);
	}

	public void setInfoDisabled() {
		infoMenuBtn.setEnabled(false);
		infoMenuBtn.setVisible(false);
	}

	public void setToggleAdvancedSearchDisabled() {
		toggleAdvancedSearchBtn.setEnabled(false);
		toggleAdvancedSearchBtn.setVisible(false);
	}

	// public void setSearchFieldDisabled() {
	// searchFld.setEnabled(false);
	// searchFld.setVisible(false);
	// }

	public void setSearchDisabled() {
		setToggleAdvancedSearchDisabled();
		setEraseDisabled();
		// setSearchFieldDisabled();
		searchBtn.setEnabled(false);
		searchBtn.setVisible(false);
	}

	public void init() {
		printBtn.setEnabled(false);
		newBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
		restoreBtn.setEnabled(false);
		saveBtn.setEnabled(false);

		backBtn.setEnabled(true);
		exportExcelBtn.setEnabled(true);

		handleActions();
	}

	private void buildMainLayout() {

		addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		toggleAdvancedSearchBtn = addItem("");
		toggleAdvancedSearchBtn
				.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_FILTER));
		// toggleAdvancedSearchBtn.setIcon(FontAwesome.FILTER);
		toggleAdvancedSearchBtn.setIcon(CxodeIcons.FILTER);
		toggleAdvancedSearchBtn.setStyleName(Style.ACTION_BAR_MORE);

		searchBtn = addItem("");
		searchBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SEARCH));
		// searchBtn.setIcon(VaadinIcons.SEARCH);
		searchBtn.setIcon(CxodeIcons.SEARCH);
		searchBtn.setStyleName(Style.ACTION_BAR_MORE);

		eraseBtn = addItem("");
		eraseBtn.setDescription(
				PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_CLEAR_FILTER));
		// eraseBtn.setIcon(VaadinIcons.ERASER);
		eraseBtn.setIcon(CxodeIcons.CLEAR);
		eraseBtn.setStyleName(Style.ACTION_BAR_MORE);

		backBtn = addItem("");
		backBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_BACK));
		// backBtn.setIcon(VaadinIcons.ARROW_CIRCLE_LEFT);
		backBtn.setIcon(CxodeIcons.BACK);
		backBtn.setStyleName(Style.ACTION_BAR_MORE);

		newBtn = addItem("");
		newBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_ADD));
		// newBtn.setIcon(VaadinIcons.PLUS);
		newBtn.setIcon(CxodeIcons.ADD);
		newBtn.setStyleName(Style.ACTION_BAR_MORE);

		deleteBtn = addItem("");
		deleteBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_DELETE));
		// deleteBtn.setIcon(VaadinIcons.TRASH);
		deleteBtn.setIcon(CxodeIcons.DELETE);
		deleteBtn.setStyleName(Style.ACTION_BAR_MORE);
		
		restoreBtn = addItem("");
		restoreBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_RESTORE));
		restoreBtn.setIcon(CxodeIcons.RESTORE);
		restoreBtn.setStyleName(Style.ACTION_BAR_MORE);

		saveBtn = addItem("");
		saveBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SAVE));
		// saveBtn.setIcon(FontAwesome.SAVE);
		saveBtn.setIcon(CxodeIcons.SAVE);
		saveBtn.setStyleName(Style.ACTION_BAR_MORE);

		printBtn = addItem("");
		// printBtn.setIcon(VaadinIcons.PRINT);
		printBtn.setIcon(CxodeIcons.REPORT);
		printBtn.setStyleName(Style.ACTION_BAR_MORE);

		exportExcelBtn = addItem("");
		exportExcelBtn.setDescription(
				PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_EXPORT_EXCEL));
		// exportExcelBtn.setIcon(FontAwesome.FILE_EXCEL_O);
		exportExcelBtn.setIcon(CxodeIcons.EXCEL);
		exportExcelBtn.setStyleName(Style.ACTION_BAR_MORE);

		infoMenuBtn = addItem("");
		infoMenuBtn.setDescription(
				PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_MORE_ACTIONS));
		infoMenuBtn.setIcon(CxodeIcons.MORE);
		infoMenuBtn.setStyleName(Style.ACTION_BAR_MORE);
		
		auditBtn = infoMenuBtn.addItem("");
		auditBtn.setCheckable(false);
		auditBtn.setIcon(CxodeIcons.AUDIT);
		auditBtn.setStyleName(Style.ACTION_BAR_MORE);

		helpBtn = addItem("");
		helpBtn.setIcon(VaadinIcons.QUESTION_CIRCLE);
		helpBtn.setStyleName(Style.ACTION_BAR_MORE);
	}

	public MenuItem getSaveBtn() {
		return saveBtn;
	}

	public MenuItem getBackBtn() {
		return backBtn;
	}

	public MenuItem getDeleteBtn() {
		return deleteBtn;
	}

	public MenuItem getNewBtn() {
		return newBtn;
	}

	public MenuItem getPrintBtn() {
		return printBtn;
	}

	public MenuItem getExportExcelBtn() {
		return exportExcelBtn;
	}

	public MenuItem getSearchBtn() {
		return searchBtn;
	}

	public MenuItem getEraseBtn() {
		return eraseBtn;
	}

	public MenuItem getInfoBtn() {
		return infoMenuBtn;
	}

	public MenuItem getAuditBtn() {
		return auditBtn;
	}

	public MenuItem getHelpBtn() {
		return helpBtn;
	}

	public MenuItem getToggleAdvancedSearchBtn() {
		return toggleAdvancedSearchBtn;
	}

	public void hidePrint() {
		printBtn.setVisible(false);
		printHidden = true;
	}

	public void hideSave() {
		saveBtn.setVisible(false);
		saveHidden = true;
	}

	public void hideImport() {
		importButton.setVisible(false);
	}

	public void hideNew() {
		newBtn.setVisible(false);
		newHidden = true;
	}

	public void hideCancel() {
		backBtn.setVisible(false);
		cancelHidden = true;
	}

	public void hideDelete() {
		deleteBtn.setVisible(false);
		deleteHidden = true;
	}

	public void hideSearch() {
		searchBtn.setVisible(false);
		toggleAdvancedSearchBtn.setVisible(false);
		searchHidden = true;
	}

	public void hideExportExcel() {
		exportExcelBtn.setVisible(false);
		exportExcelHidden = true;
	}

	public void hideAudit() {
		auditBtn.setVisible(false);
		auditHidden = true;
	}

	public void hideUpload() {
		uploadDocumentBtn.setVisible(false);
		uploadHidden = true;
	}

	public void hideErase() {
		eraseBtn.setVisible(false);
		eraseHidden = true;
	}

	public void hideCopy() {
		copyButton.setVisible(false);
		copyHidden = true;
	}

	public void hideInfo() {
		infoMenuBtn.setVisible(false);
		infoHidden = true;
	}

	private void handleActions() {
		eraseBtn.setCommand(new Command() {

			private static final long serialVersionUID = 9133620185749394044L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				if (actionErase != null) {
					actionErase.handleErase();
				}
			}
		});
		searchBtn.setCommand(new Command() {

			private static final long serialVersionUID = -8157981147211957920L;

			@Override
			public void menuSelected(MenuItem selectedItem) {
				handleSearchClick();
			}
		});

	}

	@Override
	public void handleSearchClick() {
		searchBtn.getCommand().menuSelected(searchBtn);
	}

	public void handleToggleAdvancedSearchClick() {
		toggleAdvancedSearchBtn.getCommand().menuSelected(toggleAdvancedSearchBtn);
	}

	public void setActionErase(Command command) {
		this.eraseBtn.setCommand(command);
	}

	public void setActionSearch(Command command) {
		this.searchBtn.setCommand(command);
	}

	public void setActionToggleAdvancedSearch(Command command) {
		this.toggleAdvancedSearchBtn.setCommand(command);
	}

	public void setActionBack(Command command) {
		this.backBtn.setCommand(command);
	}

	public void setActionNew(Command command) {
		this.newBtn.setCommand(command);
	}

	public void setActionSave(Command command) {
		this.saveBtn.setCommand(command);
	}

	public void setActionDelete(Command command) {
		this.deleteBtn.setCommand(command);
	}
	
	public void setActionRestore(Command command) {
		this.restoreBtn.setCommand(command);
	}

	public void setActionExportToExcel(Command command) {
		this.exportExcelBtn.setCommand(command);
	}
	
	public void setActionAudit(Command command) {
		this.auditBtn.setCommand(command);
	}

}
