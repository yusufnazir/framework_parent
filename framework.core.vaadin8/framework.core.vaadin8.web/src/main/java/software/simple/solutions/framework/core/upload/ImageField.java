package software.simple.solutions.framework.core.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import software.simple.solutions.framework.core.components.CButton;
import software.simple.solutions.framework.core.icons.CxodeIcons;

public class ImageField extends VerticalLayout implements Upload.SucceededListener {

	private static final long serialVersionUID = -952114850561015782L;

	private Image image;
	private Upload upload;
	private UploadFieldReceiver receiver;
	private UploadImage uploadImage;

	public interface UploadImage {

		public void upload(InputStream inputStream);

	}

	public ImageField() {
		setMargin(false);
		setWidth("-1px");
		setHeight("-1px");
		image = new Image();
		image.setHeight("120px");
		addComponent(image);

		CssLayout cssLayout = new CssLayout();
		cssLayout.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		addComponent(cssLayout);
		setComponentAlignment(cssLayout, Alignment.MIDDLE_CENTER);

		receiver = new FileBuffer();

		upload = new Upload();
		upload.setImmediateMode(true);
		upload.setReceiver(receiver);
		upload.addSucceededListener(this);
		cssLayout.addComponent(upload);

		CButton deleteBtn = new CButton();
		deleteBtn.addStyleName(ValoTheme.BUTTON_DANGER);
		deleteBtn.addStyleName(ValoTheme.BUTTON_SMALL);
		deleteBtn.setIcon(FontAwesome.TRASH);
		cssLayout.addComponent(deleteBtn);
		deleteBtn.addClickListener(new DeleteButtonClickListener());

		image.setSource(CxodeIcons.PROFILE_IMAGE);
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		Resource resource = new StreamResource(new StreamSource() {

			private static final long serialVersionUID = 7941811283553249939L;

			@Override
			public InputStream getStream() {
				if (uploadImage != null) {
					uploadImage.upload(receiver.getContentAsStream());
				}
				return receiver.getContentAsStream();
			}
		}, UUID.randomUUID().toString());
		image.setSource(resource);
	}

	private class DeleteButtonClickListener implements ClickListener {

		private static final long serialVersionUID = -4097543088277199580L;

		@Override
		public void buttonClick(ClickEvent event) {
			ThemeResource resource = new ThemeResource("img/profile-pic-300px.jpg");
			image.setSource(resource);
			handleImageDelete();
		}
	}

	public void handleImageDelete() {
		return;
	}

	public void setImageHeight(String height) {
		image.setHeight(height);
	}

	public void setImageWidth(String height) {
		image.setWidth(height);
	}

	public UploadFieldReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(UploadFieldReceiver receiver) {
		this.receiver = receiver;
	}

	public byte[] getBytes() {
		byte[] bytes;
		try {
			bytes = IOUtils.toByteArray(receiver.getContentAsStream());
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	public void setSource(Resource source) {
		image.setSource(source);
	}

	public void setUploadHandler(UploadImage uploadImage) {
		this.uploadImage = uploadImage;
	}

}
