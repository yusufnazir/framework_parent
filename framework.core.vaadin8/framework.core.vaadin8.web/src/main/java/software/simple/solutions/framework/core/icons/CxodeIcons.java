package software.simple.solutions.framework.core.icons;

import com.vaadin.server.ThemeResource;

public class CxodeIcons extends ThemeResource {

	private static final long serialVersionUID = 2529526937329167076L;

	private static final String ICONS_PREFIX = "../cxode/icons/";
	private static final String IMG_PREFIX = "../cxode/img/";

	public static final CxodeIcons ADD = getIcon("add");
	public static final CxodeIcons AUDIT = getIcon("audit");
	public static final CxodeIcons BACK = getIcon("back");
	public static final CxodeIcons CANCEL = getIcon("cancel");
	public static final CxodeIcons CHECK_BOX = getIcon("check-box");
	public static final CxodeIcons CLEAR = getIcon("clear");
	public static final CxodeIcons DELETE = getIcon("delete");
	public static final CxodeIcons EDIT = getIcon("edit");
	public static final CxodeIcons EXCEL = getIcon("excel");
	public static final CxodeIcons FAIL = getIcon("fail");
	public static final CxodeIcons FILTER = getIcon("filter");
	public static final CxodeIcons GEARS = getIcon("gears");
	public static final CxodeIcons GREEN_DOT = getIcon("green-dot");
	public static final CxodeIcons MORE = getIcon("more");
	public static final CxodeIcons OPEN_EXTERNAL = getIcon("open-external");
	public static final CxodeIcons OK = getIcon("ok");
	public static final CxodeIcons PIN = getIcon("pin");
	public static final CxodeIcons SAVE = getIcon("save");
	public static final CxodeIcons SEARCH = getIcon("search");
	public static final CxodeIcons SUCCESS = getIcon("success");
	public static final CxodeIcons UNPIN = getIcon("unpin");
	public static final CxodeIcons RED_DOT = getIcon("red-dot");
	public static final CxodeIcons REPORT = getIcon("report");
	public static final CxodeIcons RESTORE = getIcon("restore");
	public static final CxodeIcons RESIZE = getIcon("resize");

	public static final CxodeIcons PROFILE_IMAGE = getImg("profile-pic-300px.jpg");

	private CxodeIcons(String resourceId) {
		super(resourceId);
	}

	private static CxodeIcons getIcon(String name) {
		return new CxodeIcons(ICONS_PREFIX + name + ".svg");
	}

	private static CxodeIcons getImg(String name) {
		return new CxodeIcons(IMG_PREFIX + name);
	}

}
