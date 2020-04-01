package software.simple.solutions.framework.core.web.components;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.dom.Element;

@Tag("table")
public class Table extends Component implements HasSize {

	private static final long serialVersionUID = 1600029248937942729L;

	private Map<Integer, Map<Integer, Element>> rows;
	private int rowCount = 0;
	private int columnCount = 0;

	private Table() {
		super();
	}

	public Table(int columnCount, int rowCount) {
		this();
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		rows = new HashMap<Integer, Map<Integer, Element>>();
		build();
	}

	public void build() {
		for (int i = 0; i < rowCount; i++) {
			Element trElement = new Element("tr");
			if (!rows.containsKey(i)) {
				rows.put(Integer.valueOf(i), new HashMap<Integer, Element>());
			}
			Map<Integer, Element> columns = rows.get(i);

			for (int j = 0; j < columnCount; j++) {
				Element tdElement = new Element("td");
				trElement.appendChild(tdElement);
				if (!columns.containsKey(j)) {
					columns.put(Integer.valueOf(j), tdElement);
				}
			}
			getElement().appendChild(trElement);
		}
	}

	public void add(Component component, int column, int row) {
		rows.get(row).get(column).appendChild(component.getElement());
	}
}
