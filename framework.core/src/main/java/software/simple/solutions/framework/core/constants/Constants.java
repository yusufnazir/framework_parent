package software.simple.solutions.framework.core.constants;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Constants {

	public static final String FILE_SEPARATOR = System.getProperty("file.separator");
	public static final String CATALINA_HOME = System.getProperty("catalina.home");
	public static final String CORE_APPLICATION_FOLDER = CATALINA_HOME + FILE_SEPARATOR + ".core";
	public static final String LOGIC_JAR_FOLDER = CORE_APPLICATION_FOLDER + FILE_SEPARATOR + "logic";
	public static final String MESSAGE_FOLDER = CORE_APPLICATION_FOLDER + FILE_SEPARATOR + "message";
	public static final String IMAGES_HOME = CORE_APPLICATION_FOLDER + FILE_SEPARATOR + "images";

	public static final DecimalFormat TIME_FORMAT = new DecimalFormat("00");
	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	public static final DecimalFormat LONG_FORMAT = new DecimalFormat("#");
	public static final DecimalFormat DF = new DecimalFormat("#####0.00");

	public static final String[] IMAGE_EXTENSIONS = { "png", "jpeg", "jpg", "gif", "bmp" };
	
	public static File APPLICATION_HOME;
}
