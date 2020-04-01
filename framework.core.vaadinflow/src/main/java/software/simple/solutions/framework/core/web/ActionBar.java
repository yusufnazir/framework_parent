package software.simple.solutions.framework.core.web;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.components.CButton;

public class ActionBar extends HorizontalLayout implements SearchEvent {

	private static final long serialVersionUID = -204573107078009299L;

	private CButton saveBtn;
//	private CButton cancelBtn;
	private CButton editBtn;
	private CButton backBtn;
	private CButton deleteBtn;
	private CButton restoreBtn;
	private CButton newBtn;
	private CButton printBtn;
	private CButton exportExcelBtn;
	private CButton searchBtn;
	private CButton eraseBtn;
	private CButton auditBtn;
	private CButton helpBtn;
	private CButton toggleAdvancedSearchBtn;
	private CButton copyButton;
	private CButton importButton;
	private CButton uploadDocumentBtn;
	private CButton infoMenuBtn;
	// private Button creatingItemLbl;
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
		deleteBtn.setVisible(actionState.getDeleteEnabled());
		newBtn.setVisible(actionState.getInsertEnabled());
		printBtn.setVisible(actionState.getReportsEnabled());
		saveBtn.setVisible(actionState.getUpdateEnabled() || actionState.getInsertEnabled());
		editBtn.setVisible(actionState.getUpdateEnabled());
	}

	public void authorizeCopy() {
		copyButton.setVisible(true);
		if (!copyHidden) {
			copyButton.setVisible(true);
		}
	}

	public void authorizeSave() {
		Boolean saveEnabled = (actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		saveBtn.setVisible(saveEnabled);
		if (saveEnabled && !saveHidden) {
			saveBtn.setVisible(true);
//			cancelBtn.setVisible(true);
			editBtn.setVisible(false);
		}
	}

	public void authorizeSaveNew() {
		Boolean saveEnabled = (actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		saveBtn.setVisible(saveEnabled);
		if (saveEnabled && !saveHidden) {
			saveBtn.setVisible(true);
//			cancelBtn.setVisible(false);
			editBtn.setVisible(false);
		}
	}

	public void authorizeEdit() {
		Boolean saveEnabled = (actionState.getUpdateEnabled());
		if (saveEnabled && !saveHidden) {
			saveBtn.setVisible(false);
//			cancelBtn.setVisible(false);
			editBtn.setVisible(true);
		}
	}

	public void authorizeImport() {
		importButton.setVisible(actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		importButton.setVisible(true);
	}

	public void authorizeNew() {
		Boolean insertEnabled = actionState.getInsertEnabled();
		newBtn.setVisible(insertEnabled);
		if (insertEnabled && !newHidden) {
			newBtn.setVisible(true);
		}
	}

	public void authorizeDelete() {
		Boolean deleteEnabled = actionState.getDeleteEnabled();
		deleteBtn.setVisible(deleteEnabled);
		if (deleteEnabled && !deleteHidden) {
			deleteBtn.setVisible(true);
		}
	}

	public void authorizeRestore() {
		Boolean restoreEnabled = actionState.getDeleteEnabled();
		restoreBtn.setVisible(restoreEnabled);
		if (restoreEnabled && !deleteHidden) {
			restoreBtn.setVisible(true);
		}
	}

	public void authorizeBack() {
		backBtn.setVisible(true);
		if (!cancelHidden) {
			backBtn.setVisible(true);
		}
	}

	public void authorizeExportExcel() {
		exportExcelBtn.setVisible(true);
		if (!exportExcelHidden) {
			exportExcelBtn.setVisible(true);
		}
	}

	public void authorizeUpload() {
		uploadDocumentBtn.setVisible(true);
		if (!uploadHidden) {
			uploadDocumentBtn.setVisible(true);
		}
	}

	public void authorizeAudit() {
		auditBtn.setVisible(true);
		if (!auditHidden) {
			auditBtn.setVisible(true);
		}
	}

	public void authorizeSearch() {
		searchBtn.setVisible(true);
		toggleAdvancedSearchBtn.setVisible(true);
		// searchFld.setVisible(true);
		if (!searchHidden) {
			searchBtn.setVisible(true);
			toggleAdvancedSearchBtn.setVisible(true);
			// searchFld.setVisible(true);
		}
	}

	public void authorizeErase() {
		eraseBtn.setVisible(true);
		if (!eraseHidden) {
			eraseBtn.setVisible(true);
		}
	}

	public void authorizePrint() {
		printBtn.setVisible(true);
		if (!printHidden) {
			printBtn.setVisible(true);
		}
	}

	public void authorizeInfo() {
		infoMenuBtn.setVisible(true);
		if (!infoHidden) {
			infoMenuBtn.setVisible(true);
		}
	}

	public void authorizeCreatingItem() {
		// creatingItemLbl.setVisible(true);
	}

	public void setDeleteDisabled() {
		deleteBtn.setVisible(false);
		deleteBtn.setVisible(false);
		restoreBtn.setVisible(false);
		restoreBtn.setVisible(false);
	}

	public void setNewDisabled() {
		newBtn.setVisible(false);
		newBtn.setVisible(false);
	}

	public void setSaveDisabled() {
		saveBtn.setVisible(false);
		saveBtn.setVisible(false);
//		cancelBtn.setVisible(false);
		editBtn.setVisible(false);
	}

	public void setPrintDisabled() {
		printBtn.setVisible(false);
		printBtn.setVisible(false);
	}

	public void setUploadDisabled() {
		uploadDocumentBtn.setVisible(false);
		uploadDocumentBtn.setVisible(false);
	}

	public void setAuditDisabled() {
		auditBtn.setVisible(false);
		auditBtn.setVisible(false);
	}

	public void setExportDisabled() {
		exportExcelBtn.setVisible(false);
		exportExcelBtn.setVisible(false);
	}

	public void setEraseDisabled() {
		eraseBtn.setVisible(false);
		eraseBtn.setVisible(false);
	}

	public void setCopyDisabled() {
		copyButton.setVisible(false);
		copyButton.setVisible(false);
	}

	public void setHelpDisabled() {
		helpBtn.setVisible(false);
		helpBtn.setVisible(false);
	}

	public void setBackDisabled() {
		backBtn.setVisible(false);
		backBtn.setVisible(false);
	}

//	public void setCancelDisabled() {
//		cancelBtn.setVisible(false);
//		cancelBtn.setVisible(false);
//	}

	public void setInfoDisabled() {
		infoMenuBtn.setVisible(false);
		infoMenuBtn.setVisible(false);
	}

	public void setToggleAdvancedSearchDisabled() {
		toggleAdvancedSearchBtn.setVisible(false);
		toggleAdvancedSearchBtn.setVisible(false);
	}

	// public void setSearchFieldDisabled() {
	// searchFld.setVisible(false);
	// searchFld.setVisible(false);
	// }

	public void setSearchDisabled() {
		setToggleAdvancedSearchDisabled();
		setEraseDisabled();
		// setSearchFieldDisabled();
		searchBtn.setVisible(false);
		searchBtn.setVisible(false);
	}

	public void init() {
		printBtn.setVisible(false);
		newBtn.setVisible(false);
		deleteBtn.setVisible(false);
		restoreBtn.setVisible(false);
		saveBtn.setVisible(false);
//		cancelBtn.setVisible(false);
		editBtn.setVisible(false);

		backBtn.setVisible(true);
		exportExcelBtn.setVisible(true);

		handleActions();
	}

	private void buildMainLayout() {

		toggleAdvancedSearchBtn = new CButton();
		toggleAdvancedSearchBtn.setIcon(FontAwesome.Solid.FILTER.create());
		toggleAdvancedSearchBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		toggleAdvancedSearchBtn.setTitle(PropertyResolver
				.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_FILTER, UI.getCurrent().getLocale()));
		add(toggleAdvancedSearchBtn);
		// toggleAdvancedSearchBtn
		// .setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_FILTER,
		// UI.getCurrent().getLocale()));

		searchBtn = new CButton();
		searchBtn.setIcon(FontAwesome.Solid.SEARCH.create());
		searchBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(searchBtn);
		searchBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SEARCH,
				UI.getCurrent().getLocale()));

		eraseBtn = new CButton();
		eraseBtn.setIcon(FontAwesome.Solid.ERASER.create());
		eraseBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(eraseBtn);
		eraseBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_CLEAR_FILTER,
				UI.getCurrent().getLocale()));

		backBtn = new CButton();
		backBtn.setIcon(FontAwesome.Solid.ARROW_CIRCLE_LEFT.create());
		backBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(backBtn);
		// backBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_BACK));

		newBtn = new CButton();
		newBtn.setIcon(FontAwesome.Solid.PLUS_SQUARE.create());
		newBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(newBtn);
		newBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_ADD,
				UI.getCurrent().getLocale()));

		deleteBtn = new CButton();
		deleteBtn.setIcon(FontAwesome.Solid.TRASH.create());
		deleteBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(deleteBtn);
		// deleteBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_DELETE));

		restoreBtn = new CButton();
		restoreBtn.setIcon(FontAwesome.Solid.REDO.create());
		restoreBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(restoreBtn);
		// restoreBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_RESTORE));

		saveBtn = new CButton();
		saveBtn.setIcon(FontAwesome.Solid.SAVE.create());
		saveBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(saveBtn);
		// saveBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SAVE));

//		cancelBtn = new CButton();
//		cancelBtn.setIcon(FontAwesome.Solid.TIMES_CIRCLE.create());
//		cancelBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
//		add(cancelBtn);
		// cancelBtn.setDescription(
		// PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_CANCEL_EDIT));

		editBtn = new CButton();
		editBtn.setIcon(FontAwesome.Solid.EDIT.create());
		editBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(editBtn);
		// editBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_EDIT));

		printBtn = new CButton();
		printBtn.setIcon(FontAwesome.Solid.PRINT.create());
		printBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(printBtn);

		exportExcelBtn = new CButton();
		exportExcelBtn.setIcon(FontAwesome.Solid.FILE_EXPORT.create());
		exportExcelBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(exportExcelBtn);
		// exportExcelBtn.setDescription(
		// PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_EXPORT_EXCEL));

		infoMenuBtn = new CButton();
		infoMenuBtn.setIcon(FontAwesome.Solid.INFO.create());
		infoMenuBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(infoMenuBtn);
		// infoMenuBtn.setDescription(
		// PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_MORE_ACTIONS));

		auditBtn = new CButton();
		auditBtn.setIcon(FontAwesome.Solid.HISTORY.create());
		auditBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(auditBtn);

		helpBtn = new CButton();
		helpBtn.setIcon(FontAwesome.Solid.QUESTION.create());
		helpBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		add(helpBtn);

	}

	public Button getSaveBtn() {
		return saveBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}

	public Button getNewBtn() {
		return newBtn;
	}

	public Button getPrintBtn() {
		return printBtn;
	}

	public Button getExportExcelBtn() {
		return exportExcelBtn;
	}

	public Button getSearchBtn() {
		return searchBtn;
	}

	public Button getEraseBtn() {
		return eraseBtn;
	}

	public Button getInfoBtn() {
		return infoMenuBtn;
	}

	public Button getAuditBtn() {
		return auditBtn;
	}

	public Button getHelpBtn() {
		return helpBtn;
	}

	public Button getToggleAdvancedSearchBtn() {
		return toggleAdvancedSearchBtn;
	}

	public void hidePrint() {
		printBtn.setVisible(false);
		printHidden = true;
	}

	public void hideSave() {
		saveBtn.setVisible(false);
//		cancelBtn.setVisible(false);
		editBtn.setVisible(false);
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

	public void hideCreatingItem() {
		// creatingItemLbl.setVisible(false);
	}

	private void handleActions() {
		eraseBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (actionErase != null) {
					actionErase.handleErase();
				}
			}
		});
		searchBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -8157981147211957920L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				handleSearchClick();
			}
		});

	}

	@Override
	public void handleSearchClick() {
	}

	public void handleToggleAdvancedSearchClick() {
	}

	public void setActionErase(ComponentEventListener<ClickEvent<Button>> listener) {
		this.eraseBtn.addClickListener(listener);
	}

	public void setActionSearch(ComponentEventListener<ClickEvent<Button>> listener) {
		this.searchBtn.addClickListener(listener);
	}

	public void setActionToggleAdvancedSearch(ComponentEventListener<ClickEvent<Button>> listener) {
		this.toggleAdvancedSearchBtn.addClickListener(listener);
	}

	public void setActionBack(ComponentEventListener<ClickEvent<Button>> listener) {
		this.backBtn.addClickListener(listener);
	}

	public void setActionNew(ComponentEventListener<ClickEvent<Button>> listener) {
		this.newBtn.addClickListener(listener);
	}

	public void setActionSave(ComponentEventListener<ClickEvent<Button>> listener) {
		this.saveBtn.addClickListener(listener);
	}

	public void setActionDelete(ComponentEventListener<ClickEvent<Button>> listener) {
		this.deleteBtn.addClickListener(listener);
	}

	public void setActionRestore(ComponentEventListener<ClickEvent<Button>> listener) {
		this.restoreBtn.addClickListener(listener);
	}

	public void setActionExportToExcel(ComponentEventListener<ClickEvent<Button>> listener) {
		this.exportExcelBtn.addClickListener(listener);
	}

	public void setActionAudit(ComponentEventListener<ClickEvent<Button>> listener) {
		this.auditBtn.addClickListener(listener);
	}

//	public void setActionCancel(ComponentEventListener<ClickEvent<Button>> listener) {
//		this.cancelBtn.addClickListener(listener);
//	}

	public void setActionEdit(ComponentEventListener<ClickEvent<Button>> listener) {
		this.editBtn.addClickListener(listener);
	}
	
	public void setActionInfo(ComponentEventListener<ClickEvent<Button>> listener) {
		this.infoMenuBtn.addClickListener(listener);
	}

}
