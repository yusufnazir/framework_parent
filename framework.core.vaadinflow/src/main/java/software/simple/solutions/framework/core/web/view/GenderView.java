package software.simple.solutions.framework.core.web.view;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Gender;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.GenderProperty;
import software.simple.solutions.framework.core.service.facade.GenderServiceFacade;
import software.simple.solutions.framework.core.valueobjects.GenderVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.GenderForm;

@Route(value = Routes.GENDER, layout = MainView.class)
public class GenderView extends BasicTemplate<Gender> {

	private static final long serialVersionUID = 6503015064562511801L;

	public GenderView() {
		setEntityClass(Gender.class);
		setServiceClass(GenderServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(GenderForm.class);
		setParentReferenceKey(ReferenceKey.GENDER);
		setEditRoute(Routes.GENDER_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Gender::getName, GenderProperty.NAME);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		private CStringIntervalLayout nameFld;

		@Override
		public void executeBuild() throws FrameworkException {
			nameFld = addField(CStringIntervalLayout.class, GenderProperty.NAME, 0, 0);
		}

		@Override
		public Object getCriteria() {
			GenderVO vo = new GenderVO();
			vo.setNameInterval(nameFld.getValue());
			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
