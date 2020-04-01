package software.simple.solutions.framework.core.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PagingBar extends HorizontalLayout {

	private static final long serialVersionUID = 4593670323640828886L;
	private static final Logger logger = LogManager.getLogger(PagingBar.class);

	public ComboBox<Integer> itemsPerPageSelect;
	private Label currentPageLbl;
	private Label countLbl;
	public Button firstBtn;
	public Button previousBtn;
	public Button nextBtn;
	public Button lastBtn;
	private Integer currentPage = 1;
	private Integer pages;
	private PagingSearchEvent pagingSearchEvent;
	private Long totalRows;
	private Integer maxResults;
	private MaxResultChangeEvent maxResultChangeEvent;
	private boolean maxResultsFetched = false;
	private boolean preSet = false;

	public PagingBar() {
		itemsPerPageSelect = new ComboBox();
		itemsPerPageSelect.getElement().setAttribute("theme", "small");
		List<Integer> items = new ArrayList<Integer>();
		items.add(5);
		items.add(10);
		items.add(25);
		items.add(50);
		items.add(100);
		items.add(200);
		items.add(400);
		itemsPerPageSelect.setItems(items);
		itemsPerPageSelect.setWidth("50px");
		itemsPerPageSelect.setValue(25);
		itemsPerPageSelect.setPreventInvalidInput(true);
		currentPageLbl = new Label("1");
		countLbl = new Label("0");

		// HorizontalLayout controlBar = new HorizontalLayout();
		firstBtn = new Button();
		firstBtn.setIcon(FontAwesome.Solid.BACKWARD.create());
		firstBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		previousBtn = new Button();
		previousBtn.setIcon(FontAwesome.Solid.STEP_BACKWARD.create());
		previousBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		nextBtn = new Button();
		nextBtn.setIcon(FontAwesome.Solid.STEP_FORWARD.create());
		nextBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);
		lastBtn = new Button();
		lastBtn.setIcon(FontAwesome.Solid.FORWARD.create());
		lastBtn.addThemeVariants(ButtonVariant.LUMO_SMALL);

		add(itemsPerPageSelect);
		add(firstBtn);
		add(previousBtn);
		add(currentPageLbl);
		add(nextBtn);
		add(lastBtn);
		add(countLbl);
		setAlignItems(Alignment.CENTER);
		// add(new Label("&nbsp;&nbsp;", ContentMode.HTML));
		setWidth("-1px");
		registerListeners();
	}

	public void setItemsPerPage(Integer itemsPerPage) {
		itemsPerPageSelect.setValue(itemsPerPage);
	}

	private void registerListeners() {
		itemsPerPageSelect.addValueChangeListener(new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				if (!preSet) {
					setTotalPages();
					currentPage = 1;
					if (maxResultChangeEvent != null) {
						maxResultChangeEvent.handleChange(maxResults);
					}
					if (pagingSearchEvent != null) {
						pagingSearchEvent.handleSearch(currentPage, maxResults);
					}
					setCurrentPage(currentPage);
				}
			}
		});
		nextBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (currentPage < pages) {
					currentPage++;
					if (pagingSearchEvent != null) {
						pagingSearchEvent.handleSearch(currentPage, maxResults);
					}
					setCurrentPage(currentPage);
				}
			}
		});
		firstBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			private static final long serialVersionUID = 6018668376382774882L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (currentPage > 1) {
					currentPage = 1;
					if (pagingSearchEvent != null) {
						pagingSearchEvent.handleSearch(currentPage, maxResults);
					}
					setCurrentPage(currentPage);
				}
			}
		});
		previousBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			private static final long serialVersionUID = 6018668376382774882L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (currentPage != 1) {
					currentPage--;
					if (pagingSearchEvent != null) {
						pagingSearchEvent.handleSearch(currentPage, maxResults);
					}
					setCurrentPage(currentPage);
				}
			}
		});
		lastBtn.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
			private static final long serialVersionUID = 6018668376382774882L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				if (currentPage < pages) {
					currentPage = pages;
					if (pagingSearchEvent != null) {
						pagingSearchEvent.handleSearch(currentPage, maxResults);
					}
					setCurrentPage(currentPage);
				}
			}
		});
	}

	public Integer getMaxResult() {
		return itemsPerPageSelect.getValue();
	}

	public void setTotalPages() {
		maxResults = itemsPerPageSelect.getValue();
		if (maxResults != 0 && totalRows != null) {
			pages = (int) Math.ceil(totalRows / new Double(maxResults));
		}
		setCurrentPage(1);
	}

	public void addPagingSearchEvent(PagingSearchEvent pagingSearchEvent) {
		this.pagingSearchEvent = pagingSearchEvent;

	}

	public long getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Long totalRows) {
		this.totalRows = totalRows;
		setTotalPages();
		countLbl.setText(totalRows == null ? "0" : totalRows.toString());
	}

	public void setMaxResultChange(MaxResultChangeEvent maxResultChangeEvent) {
		this.maxResultChangeEvent = maxResultChangeEvent;
	}

	public interface MaxResultChangeEvent {
		public void handleChange(int maxResults);
	}

	public void setCurrentPage(int i) {
		currentPage = i;
		currentPageLbl.setText(currentPage + "/" + (pages == null ? 1 : pages));
	}

	public Integer getStartPosition() {
		return (currentPage - 1) * (Integer) itemsPerPageSelect.getValue();
	}

	public boolean isMaxResultsFetched() {
		return maxResultsFetched;
	}

	public void setMaxResultsFetched(boolean maxResultsFetched) {
		this.maxResultsFetched = maxResultsFetched;
	}

	public void reset() {
		currentPage = 1;
		setTotalRows(0L);
	}

}
