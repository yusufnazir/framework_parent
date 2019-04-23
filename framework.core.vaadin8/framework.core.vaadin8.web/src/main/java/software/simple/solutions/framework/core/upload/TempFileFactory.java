package software.simple.solutions.framework.core.upload;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TempFileFactory implements FileFactory {

	@Override
	public File createFile(String fileName, String mimeType) {
		try {
			File file = File.createTempFile("filename", System.currentTimeMillis() + "_" + UUID.randomUUID());
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}