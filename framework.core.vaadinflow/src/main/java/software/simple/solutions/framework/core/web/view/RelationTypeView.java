package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.RelationType;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.RelationTypeProperty;
import software.simple.solutions.framework.core.service.facade.RelationTypeServiceFacade;
import software.simple.solutions.framework.core.valueobjects.RelationTypeVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.RelationTypeForm;

@Route(value = Routes.RELATION_TYPE, layout = MainView.class)
public class RelationTypeView extends BasicTemplate<RelationType> {

	private static final long serialVersionUID = 6503015064562511801L;

	public RelationTypeView() {
		setEntityClass(RelationType.class);
		setServiceClass(RelationTypeServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(RelationTypeForm.class);
		setParentReferenceKey(ReferenceKey.RELATION_TYPE_);
		setEditRoute(Routes.RELATION_TYPE_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(RelationType::getCode, RelationTypeProperty.CODE);
		addContainerProperty(RelationType::getName, RelationTypeProperty.NAME);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout codeFld;
		private CStringIntervalLayout nameFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			codeFld = addField(CStringIntervalLayout.class, RelationTypeProperty.CODE, 0, 0);
			nameFld = addField(CStringIntervalLayout.class, RelationTypeProperty.NAME, 0, 1);
			activeFld = addField(ActiveSelect.class, RelationTypeProperty.ACTIVE, 1, 0);
		}

		@Override
		public Object getCriteria() {
			RelationTypeVO vo = new RelationTypeVO();
			vo.setCodeInterval(codeFld.getValue());
			vo.setNameInterval(nameFld.getValue());
			vo.setActive(activeFld.getItemId());
			return vo;
		}

	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
