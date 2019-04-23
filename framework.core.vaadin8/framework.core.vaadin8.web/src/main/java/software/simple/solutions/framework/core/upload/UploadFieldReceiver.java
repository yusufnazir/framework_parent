package software.simple.solutions.framework.core.upload;

import java.io.InputStream;

import com.vaadin.ui.Upload.Receiver;

public abstract interface UploadFieldReceiver extends Receiver {

	public abstract InputStream getContentAsStream();

	public abstract boolean isEmpty();

	public abstract long getLastFileSize();

	public abstract String getLastMimeType();

	public abstract String getLastFileName();
}
