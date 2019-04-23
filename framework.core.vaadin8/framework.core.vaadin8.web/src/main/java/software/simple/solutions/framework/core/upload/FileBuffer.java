package software.simple.solutions.framework.core.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@SuppressWarnings("serial")
public class FileBuffer implements UploadFieldReceiver {

	private String mimeType;
	private String fileName;
	private File file;

	public FileBuffer() {
		super();
	}

	/**
	 * @see com.vaadin.ui.Upload.Receiver#receiveUpload(String, String)
	 */
	@Override
	public OutputStream receiveUpload(String filename, String MIMEType) {
		fileName = filename;
		mimeType = MIMEType;
		try {
			if (file != null && file.exists()) {
				file.delete();
			}

			TempFileFactory tempFileFactory = new TempFileFactory();
			file = tempFileFactory.createFile(filename, mimeType);

			return new FileOutputStream(file);
		} catch (final FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public InputStream getContentAsStream() {
		if (file != null) {
			try {
				return new FileInputStream(getFile());
			} catch (final FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else {
			return null;
		}
	}

	@Override
	public boolean isEmpty() {
		return file == null || !file.exists();
	}

	@Override
	public long getLastFileSize() {
		return file == null ? 0 : file.length();
	}

	@Override
	public String getLastMimeType() {
		return mimeType;
	}

	@Override
	public String getLastFileName() {
		return fileName;
	}

	public File getFile() {
		return file;
	}
}
