package software.simple.solutions.framework.core.components;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

import liquibase.util.file.FilenameUtils;

public class IconManager {

	public static Resource getIconByFormat(String fileName) {
		String extension = FilenameUtils.getExtension(fileName);
		if ("pdf".equalsIgnoreCase(extension)) {
			return FontAwesome.FILE_PDF_O;
		}
		if ("doc".equalsIgnoreCase(extension) || "docx".equalsIgnoreCase(extension)) {
			return FontAwesome.FILE_WORD_O;
		}
		if (("xls".equalsIgnoreCase(extension))
				|| (".xlsx".equalsIgnoreCase(extension) || "xlsx".equalsIgnoreCase(extension))
				|| (".xlsx".equalsIgnoreCase(extension))) {
			return FontAwesome.FILE_EXCEL_O;
		}
		if ("zip".equalsIgnoreCase(extension)) {
			return FontAwesome.FILE_ZIP_O;
		}
		if ("csv".equalsIgnoreCase(extension) || "txt".equalsIgnoreCase(extension) || "rtf".equalsIgnoreCase(extension)
				|| "log".equalsIgnoreCase(extension)) {
			return FontAwesome.FILE_TEXT_O;
		}
		if (("png".equalsIgnoreCase(extension)) || ("jpeg".equalsIgnoreCase(extension))
				|| ("jpg".equalsIgnoreCase(extension)) || ("gif".equalsIgnoreCase(extension))
				|| ("bmp".equalsIgnoreCase(extension))) {
			return FontAwesome.FILE_IMAGE_O;
		}
		return FontAwesome.FILE;
	}
}
