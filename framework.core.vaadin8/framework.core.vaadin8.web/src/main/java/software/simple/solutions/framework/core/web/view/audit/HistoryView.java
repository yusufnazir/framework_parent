package software.simple.solutions.framework.core.web.view.audit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.ItemClick;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.ItemClickListener;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.constants.Constants;
import software.simple.solutions.framework.core.entities.UserRevEntity;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.pojo.RevisionPojo;
import software.simple.solutions.framework.core.properties.AuditProperty;
import software.simple.solutions.framework.core.properties.SystemProperty;
import software.simple.solutions.framework.core.service.facade.AuditServiceFacade;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.util.PropertyResolver;

public class HistoryView extends Window {

	private static final long serialVersionUID = 3543219430134598576L;
	private Class<?> cl;
	private Long id;
	private Grid<RevisionPojo> grid;
	private VerticalLayout revisionViewLayout;
	private RevisionViewGenerator revisionViewGenerator;
	private UI ui;

	public HistoryView(Class<?> cl, Long id) {
		ui = UI.getCurrent();
		this.cl = cl;
		this.id = id;
		setCaption(PropertyResolver.getPropertyValueByLocale(AuditProperty.AUDIT, UI.getCurrent().getLocale()));
		setModal(true);
		center();
		setWidth("80%");
		setHeight("80%");
		UI.getCurrent().addWindow(this);
		Panel windowLayout = createWindowLayout();
		setContent(windowLayout);
	}

	private Panel createWindowLayout() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setMargin(true);
		horizontalLayout.setWidth("100%");
		horizontalLayout.setHeight("100%");
		panel.setContent(horizontalLayout);

		if (id != null) {
			grid = createHistoryGrid();
			horizontalLayout.addComponent(grid);
			populateHistoryGrid();

			revisionViewLayout = new VerticalLayout();
			revisionViewLayout.setWidth("100%");
			horizontalLayout.addComponent(revisionViewLayout);
			horizontalLayout.setExpandRatio(revisionViewLayout, 1);
		} else {
			Label messageLbl = new Label();
			messageLbl.setValue(PropertyResolver.getPropertyValueByLocale(SystemProperty.TOTAL_RECORDS_FOUND,
					UI.getCurrent().getLocale(), new Object[] { 0 }));
			messageLbl.addStyleName(ValoTheme.LABEL_H1);
			messageLbl.addStyleName(ValoTheme.LABEL_FAILURE);
			horizontalLayout.addComponent(messageLbl);
		}

		return panel;
	}

	private Grid createHistoryGrid() {
		grid = new Grid<RevisionPojo>();
		grid.setHeight("100%");
		// grid.setHeightMode(HeightMode.UNDEFINED);
		Column<RevisionPojo, Long> revisionColumn = grid.addColumn(RevisionPojo::getRevision);
		revisionColumn.setCaption(PropertyResolver.getPropertyValueByLocale(AuditProperty.REVISION, ui.getLocale()));
		revisionColumn.setWidth(75);
		Column<RevisionPojo, String> revisionTypeColumn = grid.addColumn(RevisionPojo::getRevisionType);
		revisionTypeColumn
				.setCaption(PropertyResolver.getPropertyValueByLocale(AuditProperty.REVISION_TYPE, ui.getLocale()));
		revisionTypeColumn.setWidth(75);
		Column<RevisionPojo, String> revisionDateColumn = grid.addColumn(RevisionPojo::getRevisionDate);
		revisionDateColumn
				.setCaption(PropertyResolver.getPropertyValueByLocale(AuditProperty.REVISION_DATE, ui.getLocale()));
		Column<RevisionPojo, String> revisionUserColumn = grid.addColumn(RevisionPojo::getUsername);
		revisionUserColumn
				.setCaption(PropertyResolver.getPropertyValueByLocale(AuditProperty.REVISION_USERNAME, ui.getLocale()));
		revisionUserColumn.setWidth(100);
		grid.setWidth("400px");

		grid.addItemClickListener(new ItemClickListener<RevisionPojo>() {

			private static final long serialVersionUID = -9160628321590955354L;

			@Override
			public void itemClick(ItemClick<RevisionPojo> event) {
				setRevisionViewLayout(event.getItem());
			}
		});

		return grid;
	}

	private void populateHistoryGrid() {
		PagingSetting pagingSetting = new PagingSetting(0, 0);
		PagingResult<Object[]> pagingResult = AuditServiceFacade.get(UI.getCurrent()).createAuditQuery(cl, id,
				pagingSetting);
		List<RevisionPojo> revisionPojos = new ArrayList<RevisionPojo>();
		if (pagingResult != null) {
			List<Object[]> result = pagingResult.getResult();
			for (Object[] oArray : result) {
				RevisionPojo revisionPojo = new RevisionPojo();
				UserRevEntity userRevEntity = (UserRevEntity) oArray[1];
				Long revision = NumberUtil.getLong(userRevEntity.getId());
				revisionPojo.setRevision(revision);

				String revisionType = oArray[2].toString();
				revisionPojo.setRevisionType(revisionType);

				Long timestamp = userRevEntity.getTimestamp();
				String dateFormatted = null;
				if (timestamp != null) {
					LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp),
							ZoneId.systemDefault());
					dateFormatted = date
							.format(DateTimeFormatter.ofPattern(Constants.SIMPLE_DATE_TIME_FORMAT.toPattern()));
				}
				revisionPojo.setRevisionDate(dateFormatted);

				revisionPojo.setUsername(userRevEntity.getUsername());

				revisionPojo.setEntity(oArray[0]);

				revisionPojos.add(revisionPojo);
			}
		}
		grid.setItems(revisionPojos);
	}

	public RevisionViewGenerator getRevisionViewGenerator() {
		return revisionViewGenerator;
	}

	public void setRevisionViewGenerator(RevisionViewGenerator revisionViewGenerator) {
		this.revisionViewGenerator = revisionViewGenerator;
	}

	public void setRevisionViewLayout(RevisionPojo revisionPojo) {
		if (revisionViewGenerator != null) {
			Component revisionView = revisionViewGenerator.getRevisionView(revisionPojo);
			revisionViewLayout.removeAllComponents();
			revisionViewLayout.addComponent(revisionView);
		}
	}

}
