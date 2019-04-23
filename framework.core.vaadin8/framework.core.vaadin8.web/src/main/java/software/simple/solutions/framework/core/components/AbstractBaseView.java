package software.simple.solutions.framework.core.components;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import io.reactivex.subjects.BehaviorSubject;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public abstract class AbstractBaseView extends VerticalLayout implements BaseView {

	private static final long serialVersionUID = 5131684482879011389L;

	private ViewDetail viewDetail;
	private SessionHolder sessionHolder;
	private ConcurrentMap<String, Object> referenceKeys;
	private String entityReferenceKey;
	private Object selectedEntity;
	private Object parentEntity;
	private Window popUpWindow;
	private Object popUpEntity;
	private boolean popUpMode = false;
	private boolean forwardToSearch = false;
	private Object forwardToSearchEntity;
	private BehaviorSubject<Object> updateObserver;
	private Boolean viewContentUpdated = false;

	public AbstractBaseView() {
		super();
		viewDetail = new ViewDetail();
		sessionHolder = (SessionHolder) UI.getCurrent().getData();
		referenceKeys = new ConcurrentHashMap<String, Object>();
		updateObserver = BehaviorSubject.create();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public Class<? extends View> getViewClass() {
		return getClass();
	}

	@Override
	public String getViewName() {
		return null;
	}

	public ViewDetail getViewDetail() {
		return viewDetail;
	}

	public void setViewDetail(ViewDetail viewDetail) {
		this.viewDetail = viewDetail;
	}

	public SessionHolder getSessionHolder() {
		return sessionHolder;
	}

	public void setSessionHolder(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	public void executeTab() {
		return;
	}

	public boolean validate() throws FrameworkException {
		return true;
	}

	public Object formUpdate() throws FrameworkException {
		return null;
	}

	public ConcurrentMap<String, Object> getReferenceKeys() {
		return referenceKeys;
	}

	public void setReferenceKeys(ConcurrentMap<String, Object> referenceKeys) {
		this.referenceKeys = referenceKeys;
	}

	public void addReferenceKey(String key, Object value) {
		referenceKeys.put(key, value);
	}

	public boolean isPopUpMode() {
		return popUpMode;
	}

	public void setPopUpMode(boolean popUpMode) {
		this.popUpMode = popUpMode;
	}

	public void setToPopUpMode() {
		popUpMode = true;
	}

	public void setParentEntity(Object parentEntity) {
		this.parentEntity = parentEntity;
	}

	public Window getPopUpWindow() {
		return popUpWindow;
	}

	public void setPopUpWindow(Window popUpWindow) {
		this.popUpWindow = popUpWindow;
	}

	public Object getPopUpEntity() {
		return popUpEntity;
	}

	public void setPopUpEntity(Object popUpEntity) {
		this.popUpEntity = popUpEntity;
	}

	@SuppressWarnings("unchecked")
	public <T> T getParentEntity() {
		return (T) parentEntity;
	}

	public void executePreBuild() throws FrameworkException {
		return;
	}

	public void executePostBuild() throws FrameworkException {
		return;
	}

	public void setSearchForward(Object searchedEntity) {
		if (searchedEntity != null) {
			forwardToSearch = true;
			forwardToSearchEntity = searchedEntity;
		}
	}

	public boolean isForwardToSearch() {
		return forwardToSearch;
	}

	public void setForwardToSearch(boolean forwardToSearch) {
		this.forwardToSearch = forwardToSearch;
	}

	public Object getForwardToSearchEntity() {
		return forwardToSearchEntity;
	}

	public void setForwardToSearchEntity(Object forwardToSearchEntity) {
		this.forwardToSearchEntity = forwardToSearchEntity;
	}

	public void executeSearch() {
		return;
	}

	public <T> T getSelectedEntity() {
		return (T) selectedEntity;
	}

	public void setSelectedEntity(Object selectedEntity) {
		this.selectedEntity = selectedEntity;
	}

	public String getEntityReferenceKey() {
		return entityReferenceKey;
	}

	public void setEntityReferenceKey(String entityReferenceKey) {
		this.entityReferenceKey = entityReferenceKey;
	}

	public ConcurrentMap<String, Object> addUpdateObserverReferenceKey(String referenceKey) {
		addReferenceKey(ReferenceKey.UPDATE_OBSERVEABLE + "_" + referenceKey, updateObserver);
		return getReferenceKeys();
	}

	public Object getUpdateObserverReferenceKey(String referenceKey) {
		return getReferenceKeys().get(ReferenceKey.UPDATE_OBSERVEABLE + "_" + referenceKey);
	}

	public BehaviorSubject<Object> getUpdateObserver() {
		return updateObserver;
	}

	// public Map<String, Object> getReferenceKeys() {
	// return referenceKeys;
	// }

	// public void setReferenceKeys(ConcurrentMap<String, Object> referenceKeys)
	// {
	// this.referenceKeys = referenceKeys;
	// }

	// public void addReferenceKey(String key, Object value) {
	// referenceKeys.put(key, value);
	// }

	@SuppressWarnings("unchecked")
	public <T> T getReferenceKey(String key) {
		return (T) referenceKeys.get(key);
	}

	public Boolean getViewContentUpdated() {
		return viewContentUpdated;
	}

	public void setViewContentUpdated(Boolean viewContentUpdated) {
		this.viewContentUpdated = viewContentUpdated;
	}

}
