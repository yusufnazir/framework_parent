package software.simple.solutions.framework.core.util;

import com.vaadin.server.StreamResource.StreamSource;

/**
 * Provide both the {@link StreamSource} and the filename in an on-demand way.
 */
public interface OnDemandStreamResource extends StreamSource {
	String getFilename();
}
