package software.simple.solutions.framework.core.web;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;

import software.simple.solutions.framework.core.constants.ActionState;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.util.PropertyResolver;
import software.simple.solutions.framework.core.web.components.CButton;

public class ActionBar1 extends MenuBar implements SearchEvent {

	private static final long serialVersionUID = -204573107078009299L;

	// private MenuBar menuBar;
	private MenuItem saveItem;
	// private CButton cancelItem;
	private MenuItem editItem;
	private MenuItem backItem;
	private MenuItem deleteItem;
	private MenuItem restoreItem;
	private MenuItem newItem;
	private MenuItem reportsItem;
	private MenuItem exportExcelItem;
	private MenuItem searchItem;
	private MenuItem eraseItem;
	private MenuItem auditItem;
	private MenuItem helpItem;
	private MenuItem toggleAdvancedSearchItem;
	private MenuItem copyButton;
	private MenuItem importButton;
	private MenuItem uploadDocumentItem;
	private MenuItem infoMenuItem;
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

	public ActionBar1() {
		buildMainLayout();
		init();
	}
	
	public void setSearchMode() {
		getItems().stream().forEach(p->p.setVisible(false));
		toggleAdvancedSearchItem.setVisible(true);
		searchItem.setVisible(true);
		eraseItem.setVisible(true);
		reportsItem.setVisible(false);
		infoMenuItem.setVisible(true);
	}

	public void setActionState(ActionState actionState) {
		if (actionState == null) {
			actionState = new ActionState(null);
		}
		this.actionState = actionState;
		deleteItem.setVisible(actionState.getDeleteEnabled());
		newItem.setVisible(actionState.getInsertEnabled());
		reportsItem.setVisible(actionState.getReportsEnabled());
		saveItem.setVisible(actionState.getUpdateEnabled() || actionState.getInsertEnabled());
		editItem.setVisible(actionState.getUpdateEnabled());
	}

	public void authorizeCopy() {
		copyButton.setVisible(true);
		if (!copyHidden) {
			copyButton.setVisible(true);
		}
	}

	public void authorizeSave() {
		Boolean saveEnabled = (actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		saveItem.setVisible(saveEnabled);
		if (saveEnabled && !saveHidden) {
			saveItem.setVisible(true);
			// cancelItem.setVisible(true);
			editItem.setVisible(false);
		}
	}

	public void authorizeSaveNew() {
		Boolean saveEnabled = (actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		saveItem.setVisible(saveEnabled);
		if (saveEnabled && !saveHidden) {
			saveItem.setVisible(true);
			// cancelItem.setVisible(false);
			editItem.setVisible(false);
		}
	}

	public void authorizeEdit() {
		Boolean saveEnabled = (actionState.getUpdateEnabled());
		if (saveEnabled && !saveHidden) {
			saveItem.setVisible(false);
			// cancelItem.setVisible(false);
			editItem.setVisible(true);
		}
	}

	public void authorizeImport() {
		importButton.setVisible(actionState.getUpdateEnabled() | actionState.getInsertEnabled());
		importButton.setVisible(true);
	}

	public void authorizeNew() {
		Boolean insertEnabled = actionState.getInsertEnabled();
		newItem.setVisible(insertEnabled);
		if (insertEnabled && !newHidden) {
			newItem.setVisible(true);
		}
	}

	public void authorizeDelete() {
		Boolean deleteEnabled = actionState.getDeleteEnabled();
		deleteItem.setVisible(deleteEnabled);
		if (deleteEnabled && !deleteHidden) {
			deleteItem.setVisible(true);
		}
	}

	public void authorizeRestore() {
		Boolean restoreEnabled = actionState.getDeleteEnabled();
		restoreItem.setVisible(restoreEnabled);
		if (restoreEnabled && !deleteHidden) {
			restoreItem.setVisible(true);
		}
	}

	public void authorizeBack() {
		backItem.setVisible(true);
		if (!cancelHidden) {
			backItem.setVisible(true);
		}
	}

	public void authorizeExportExcel() {
		exportExcelItem.setVisible(true);
		if (!exportExcelHidden) {
			exportExcelItem.setVisible(true);
		}
	}

	public void authorizeUpload() {
		uploadDocumentItem.setVisible(true);
		if (!uploadHidden) {
			uploadDocumentItem.setVisible(true);
		}
	}

	public void authorizeAudit() {
		auditItem.setVisible(true);
		if (!auditHidden) {
			auditItem.setVisible(true);
		}
	}

	public void authorizeSearch() {
		searchItem.setVisible(true);
		toggleAdvancedSearchItem.setVisible(true);
		// searchFld.setVisible(true);
		if (!searchHidden) {
			searchItem.setVisible(true);
			toggleAdvancedSearchItem.setVisible(true);
			// searchFld.setVisible(true);
		}
	}

	public void authorizeErase() {
		eraseItem.setVisible(true);
		if (!eraseHidden) {
			eraseItem.setVisible(true);
		}
	}

	public void authorizePrint() {
		reportsItem.setVisible(true);
		if (!printHidden) {
			reportsItem.setVisible(true);
		}
	}

	public void authorizeInfo() {
		infoMenuItem.setVisible(true);
		if (!infoHidden) {
			infoMenuItem.setVisible(true);
		}
	}

	public void authorizeCreatingItem() {
		// creatingItemLbl.setVisible(true);
	}

	public void setDeleteDisabled() {
		deleteItem.setVisible(false);
		deleteItem.setVisible(false);
		restoreItem.setVisible(false);
		restoreItem.setVisible(false);
	}

	public void setNewDisabled() {
		newItem.setVisible(false);
		newItem.setVisible(false);
	}

	public void setSaveDisabled() {
		saveItem.setVisible(false);
		saveItem.setVisible(false);
		// cancelItem.setVisible(false);
		editItem.setVisible(false);
	}

	public void setPrintDisabled() {
		reportsItem.setVisible(false);
		reportsItem.setVisible(false);
	}

	public void setUploadDisabled() {
		uploadDocumentItem.setVisible(false);
		uploadDocumentItem.setVisible(false);
	}

	public void setAuditDisabled() {
		auditItem.setVisible(false);
		auditItem.setVisible(false);
	}

	public void setExportDisabled() {
		exportExcelItem.setVisible(false);
		exportExcelItem.setVisible(false);
	}

	public void setEraseDisabled() {
		eraseItem.setVisible(false);
		eraseItem.setVisible(false);
	}

	public void setCopyDisabled() {
		copyButton.setVisible(false);
		copyButton.setVisible(false);
	}

	public void setHelpDisabled() {
		helpItem.setVisible(false);
		helpItem.setVisible(false);
	}

	public void setBackDisabled() {
		backItem.setVisible(false);
		backItem.setVisible(false);
	}

	public void setInfoDisabled() {
		infoMenuItem.setVisible(false);
		infoMenuItem.setVisible(false);
	}

	public void setToggleAdvancedSearchDisabled() {
		toggleAdvancedSearchItem.setVisible(false);
		toggleAdvancedSearchItem.setVisible(false);
	}

	// public void setSearchFieldDisabled() {
	// searchFld.setVisible(false);
	// searchFld.setVisible(false);
	// }

	public void setSearchDisabled() {
		setToggleAdvancedSearchDisabled();
		setEraseDisabled();
		// setSearchFieldDisabled();
		searchItem.setVisible(false);
		searchItem.setVisible(false);
	}

	public void init() {
		reportsItem.setVisible(false);
		newItem.setVisible(false);
		deleteItem.setVisible(false);
		restoreItem.setVisible(false);
		saveItem.setVisible(false);
		editItem.setVisible(false);

		backItem.setVisible(true);
		exportExcelItem.setVisible(true);

		handleActions();
	}

	private void buildMainLayout() {
		CButton toggleAdvancedSearchBtn = new CButton();
		toggleAdvancedSearchBtn.setIcon(FontAwesome.Solid.FILTER.create());
		toggleAdvancedSearchBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		toggleAdvancedSearchBtn.setTitle(PropertyResolver
				.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_FILTER, UI.getCurrent().getLocale()));
		toggleAdvancedSearchBtn.setText(
				PropertyResolver.getPropertyValueByLocale(SystemProperty.SEARCH_FILTER, UI.getCurrent().getLocale()));
		toggleAdvancedSearchItem = addItem(toggleAdvancedSearchBtn);

		CButton searchBtn = new CButton();
		searchBtn.setIcon(FontAwesome.Solid.SEARCH.create());
		searchBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		searchBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SEARCH,
				UI.getCurrent().getLocale()));
		searchBtn
				.setText(PropertyResolver.getPropertyValueByLocale(SystemProperty.SEARCH, UI.getCurrent().getLocale()));
		searchItem = addItem(searchBtn);

		CButton eraseBtn = new CButton();
		eraseBtn.setIcon(FontAwesome.Solid.ERASER.create());
		eraseBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		eraseBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_CLEAR_FILTER,
				UI.getCurrent().getLocale()));
		eraseBtn.setText(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_CLEAR,
				UI.getCurrent().getLocale()));
		eraseItem = addItem(eraseBtn);

		CButton backBtn = new CButton();
		backBtn.setIcon(FontAwesome.Solid.ARROW_CIRCLE_LEFT.create());
		backBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		backBtn.setText(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_CANCEL,
				UI.getCurrent().getLocale()));
		backItem = addItem(backBtn);

		CButton newBtn = new CButton();
		newBtn.setIcon(FontAwesome.Solid.PLUS_SQUARE.create());
		newBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		newBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_ADD,
				UI.getCurrent().getLocale()));
		newBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_ADD,
				UI.getCurrent().getLocale()));
		newItem = addItem(newBtn);

		CButton deleteBtn = new CButton();
		deleteBtn.setIcon(FontAwesome.Solid.TRASH.create());
		deleteBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		deleteBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_DELETE,
				UI.getCurrent().getLocale()));
		deleteItem = addItem(deleteBtn);
		// deleteBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_DELETE));

		CButton restoreBtn = new CButton();
		restoreBtn.setIcon(FontAwesome.Solid.REDO.create());
		restoreBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		restoreItem = addItem(restoreBtn);
		// restoreBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_RESTORE));

		CButton saveBtn = new CButton();
		saveBtn.setIcon(FontAwesome.Solid.SAVE.create());
		saveBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		saveBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_SAVE,
				UI.getCurrent().getLocale()));
		saveItem = addItem(saveBtn);
		// saveBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_SAVE));

		CButton editBtn = new CButton();
		editBtn.setIcon(FontAwesome.Solid.EDIT.create());
		editBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		editBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_EDIT,
				UI.getCurrent().getLocale()));
		editItem = addItem(editBtn);
		// editBtn.setDescription(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_EDIT));
		
		CButton infoMenuBtn = new CButton();
		infoMenuBtn.setIcon(FontAwesome.Solid.ELLIPSIS_V.create());
		infoMenuBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		infoMenuBtn.setMaxWidth("20px");
		infoMenuItem = addItem(infoMenuBtn);
		// infoMenuBtn.setDescription(
		// PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_MORE_ACTIONS));

		CButton reportsBtn = new CButton();
		reportsBtn.setIcon(FontAwesome.Solid.PRINT.create());
		reportsBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		reportsBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_REPORTS,
				UI.getCurrent().getLocale()));
		reportsItem = infoMenuItem.getSubMenu().addItem(reportsBtn);

		CButton exportExcelBtn = new CButton();
		exportExcelBtn.setIcon(FontAwesome.Solid.FILE_EXPORT.create());
		exportExcelBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		exportExcelBtn.setTitle(PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_BUTTON_EXPORT,
				UI.getCurrent().getLocale()));
		exportExcelItem = infoMenuItem.getSubMenu().addItem(exportExcelBtn);
		// exportExcelBtn.setDescription(
		// PropertyResolver.getPropertyValueByLocale(SystemProperty.SYSTEM_DESCRIPTION_EXPORT_EXCEL));

		CButton auditBtn = new CButton();
		auditBtn.setIcon(FontAwesome.Solid.HISTORY.create());
		auditBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		auditItem = infoMenuItem.getSubMenu().addItem(auditBtn);

		CButton helpBtn = new CButton();
		helpBtn.setIcon(FontAwesome.Solid.QUESTION.create());
		helpBtn.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
		helpItem = infoMenuItem.getSubMenu().addItem(helpBtn);

	}

	public void hidePrint() {
		reportsItem.setVisible(false);
		printHidden = true;
	}

	public void hideSave() {
		saveItem.setVisible(false);
		editItem.setVisible(false);
		saveHidden = true;
	}

	public void hideImport() {
		importButton.setVisible(false);
	}

	public void hideNew() {
		newItem.setVisible(false);
		newHidden = true;
	}

	public void hideCancel() {
		backItem.setVisible(false);
		cancelHidden = true;
	}

	public void hideDelete() {
		deleteItem.setVisible(false);
		deleteHidden = true;
	}

	public void hideSearch() {
		searchItem.setVisible(false);
		toggleAdvancedSearchItem.setVisible(false);
		searchHidden = true;
	}

	public void hideExportExcel() {
		exportExcelItem.setVisible(false);
		exportExcelHidden = true;
	}

	public void hideAudit() {
		auditItem.setVisible(false);
		auditHidden = true;
	}

	public void hideUpload() {
		uploadDocumentItem.setVisible(false);
		uploadHidden = true;
	}

	public void hideErase() {
		eraseItem.setVisible(false);
		eraseHidden = true;
	}

	public void hideCopy() {
		copyButton.setVisible(false);
		copyHidden = true;
	}

	public void hideInfo() {
		infoMenuItem.setVisible(false);
		infoHidden = true;
	}

	public void hideCreatingItem() {
		// creatingItemLbl.setVisible(false);
	}

	private void handleActions() {
		eraseItem.addClickListener(new ComponentEventListener<ClickEvent<MenuItem>>() {

			private static final long serialVersionUID = -3649912140844988617L;

			@Override
			public void onComponentEvent(ClickEvent<MenuItem> event) {
				if (actionErase != null) {
					actionErase.handleErase();
				}
			}
		});
		searchItem.addClickListener(new ComponentEventListener<ClickEvent<MenuItem>>() {

			private static final long serialVersionUID = -8157981147211957920L;

			@Override
			public void onComponentEvent(ClickEvent<MenuItem> event) {
				handleSearchClick();
			}
		});

	}

	@Override
	public void handleSearchClick() {
	}

	public void handleToggleAdvancedSearchClick() {
	}

	public void setActionErase(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.eraseItem.addClickListener(listener);
	}

	public void setActionSearch(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.searchItem.addClickListener(listener);
	}

	public void setActionToggleAdvancedSearch(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.toggleAdvancedSearchItem.addClickListener(listener);
	}

	public void setActionBack(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.backItem.addClickListener(listener);
	}

	public void setActionNew(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.newItem.addClickListener(listener);
	}

	public void setActionSave(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.saveItem.addClickListener(listener);
	}

	public void setActionDelete(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.deleteItem.addClickListener(listener);
	}

	public void setActionRestore(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.restoreItem.addClickListener(listener);
	}

	public void setActionExportToExcel(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.exportExcelItem.addClickListener(listener);
	}

	public void setActionAudit(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.auditItem.addClickListener(listener);
	}

	public void setActionEdit(ComponentEventListener<ClickEvent<MenuItem>> listener) {
		this.editItem.addClickListener(listener);
	}

}
