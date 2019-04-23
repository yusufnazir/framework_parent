package software.simple.solutions.framework.core.paging;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.data.HasValue.ValueChangeEvent;
import com.vaadin.data.HasValue.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.components.CComboBox;
import software.simple.solutions.framework.core.pojo.ComboItem;

public class PagingBar extends HorizontalLayout {

	private static final long serialVersionUID = 4593670323640828886L;
	private static final Logger logger = LogManager.getLogger(PagingBar.class);

	public CComboBox itemsPerPageSelect;
	private Label currentPageLbl;
	private Label countLbl;
	public CButton firstBtn;
	public CButton previousBtn;
	public CButton nextBtn;
	public CButton lastBtn;
	private Integer currentPage = 1;
	private Integer pages;
	private PagingSearchEvent pagingSearchEvent;
	private Long totalRows;
	private Integer maxResults;
	private MaxResultChangeEvent maxResultChangeEvent;
	private boolean maxResultsFetched = false;
	private boolean preSet = false;

	public PagingBar() {
		itemsPerPageSelect = new CComboBox();
		List<ComboItem> items = new ArrayList<ComboItem>();
		items.add(new ComboItem(5));
		items.add(new ComboItem(10));
		items.add(new ComboItem(25));
		items.add(new ComboItem(50));
		items.add(new ComboItem(100));
		items.add(new ComboItem(200));
		items.add(new ComboItem(400));
		itemsPerPageSelect.setItems(items);
		itemsPerPageSelect.setWidth("50px");
		itemsPerPageSelect.setSelectedItem(new ComboItem(25));
		itemsPerPageSelect.setEmptySelectionAllowed(false);
		currentPageLbl = new Label("1", ContentMode.HTML);
		countLbl = new Label("0", ContentMode.HTML);

		// HorizontalLayout controlBar = new HorizontalLayout();
		firstBtn = new CButton();
		firstBtn.setIcon(FontAwesome.BACKWARD);
		previousBtn = new CButton();
		previousBtn.setIcon(FontAwesome.STEP_BACKWARD);
		nextBtn = new CButton();
		nextBtn.setIcon(FontAwesome.STEP_FORWARD);
		lastBtn = new CButton();
		lastBtn.setIcon(FontAwesome.FORWARD);
		firstBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		previousBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		nextBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		lastBtn.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);

		addComponent(itemsPerPageSelect);
		setComponentAlignment(itemsPerPageSelect, Alignment.MIDDLE_LEFT);
		addComponent(firstBtn);
		addComponent(previousBtn);
		addComponent(currentPageLbl);
		addComponent(nextBtn);
		addComponent(lastBtn);
		addComponent(countLbl);
		setComponentAlignment(firstBtn, Alignment.MIDDLE_LEFT);
		setComponentAlignment(previousBtn, Alignment.MIDDLE_LEFT);
		setComponentAlignment(currentPageLbl, Alignment.MIDDLE_LEFT);
		setComponentAlignment(countLbl, Alignment.MIDDLE_LEFT);
		setComponentAlignment(nextBtn, Alignment.MIDDLE_LEFT);
		setComponentAlignment(lastBtn, Alignment.MIDDLE_LEFT);
		addComponent(new Label("&nbsp;&nbsp;", ContentMode.HTML));
		setWidth("-1px");
		registerListeners();
	}

	private void registerListeners() {
		itemsPerPageSelect.addValueChangeListener(new ValueChangeListener<ComboItem>() {

			private static final long serialVersionUID = 5228831165820301233L;

			@Override
			public void valueChange(ValueChangeEvent<ComboItem> event) {
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
		nextBtn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6018668376382774882L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (currentPage < pages) {
					currentPage++;
					if (pagingSearchEvent != null) {
						pagingSearchEvent.handleSearch(currentPage, maxResults);
					}
					setCurrentPage(currentPage);
				}
			}
		});
		firstBtn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6018668376382774882L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (currentPage > 1) {
					currentPage = 1;
					if (pagingSearchEvent != null) {
						pagingSearchEvent.handleSearch(currentPage, maxResults);
					}
					setCurrentPage(currentPage);
				}
			}
		});
		previousBtn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6018668376382774882L;

			@Override
			public void buttonClick(ClickEvent event) {
				if (currentPage != 1) {
					currentPage--;
					if (pagingSearchEvent != null) {
						pagingSearchEvent.handleSearch(currentPage, maxResults);
					}
					setCurrentPage(currentPage);
				}
			}
		});
		lastBtn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 6018668376382774882L;

			@Override
			public void buttonClick(ClickEvent event) {
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
		return itemsPerPageSelect.getItemId();
	}

	public void setTotalPages() {
		maxResults = itemsPerPageSelect.getItemId();
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
		countLbl.setValue(totalRows == null ? "0" : totalRows.toString());
	}

	public void setMaxResultChange(MaxResultChangeEvent maxResultChangeEvent) {
		this.maxResultChangeEvent = maxResultChangeEvent;
	}

	public interface MaxResultChangeEvent {
		public void handleChange(int maxResults);
	}

	public void setCurrentPage(int i) {
		currentPage = i;
		currentPageLbl.setValue(currentPage + "/" + (pages == null ? 1 : pages));
	}

	public Integer getStartPosition() {
		return (currentPage - 1) * (Integer) itemsPerPageSelect.getItemId();
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
