package software.simple.solutions.framework.core.upload;

import java.io.File;

public interface FileFactory {
	public File createFile(String fileName, String mimeType);
}
