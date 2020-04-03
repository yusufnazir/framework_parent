package software.simple.solutions.framework.core.web.view;

import java.time.LocalDate;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Holiday;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.SplitDate;
import software.simple.solutions.framework.core.properties.HolidayProperty;
import software.simple.solutions.framework.core.service.facade.HolidayServiceFacade;
import software.simple.solutions.framework.core.valueobjects.HolidayVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.components.CSplitDateField;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.HolidayForm;

@Route(value = Routes.HOLIDAY, layout = MainView.class)
public class HolidayView extends BasicTemplate<Holiday> {

	private static final long serialVersionUID = 6503015064562511801L;

	public HolidayView() {
		setEntityClass(Holiday.class);
		setServiceClass(HolidayServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(HolidayForm.class);
		setParentReferenceKey(ReferenceKey.HOLIDAY);
		setEditRoute(Routes.HOLIDAY_EDIT);
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(Holiday::getName, HolidayProperty.NAME);
		addContainerProperty(new ValueProvider<Holiday, LocalDate>() {

			private static final long serialVersionUID = 1750322039011341685L;

			@Override
			public LocalDate apply(Holiday source) {
				return LocalDate.of(source.getYear().intValue(), source.getMonth().intValue(),
						source.getDay().intValue());
			}
		}, HolidayProperty.DATE);
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
		private CSplitDateField dateFld;

		@Override
		public void executeBuild() throws FrameworkException {
			nameFld = addField(CStringIntervalLayout.class, HolidayProperty.NAME, 0, 0);
			dateFld = addField(CSplitDateField.class, HolidayProperty.DATE, 1, 0);
		}

		@Override
		public Object getCriteria() {
			HolidayVO vo = new HolidayVO();
			vo.setNameInterval(nameFld.getValue());
			SplitDate splitDate = dateFld.getValue();
			vo.setYear(splitDate.getYear());
			vo.setMonth(splitDate.getMonth());
			vo.setDay(splitDate.getDay());
			return vo;
		}
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
