package software.simple.solutions.framework.core.components;

import java.util.Map;

public interface Referenceable {

	public Map<String, Object> getReferenceKeys();

	public void setReferenceKeys(Map<String, Object> referenceKeys);

	public void addReferenceKey(String key, Object value);

	public <T> T getReferenceKey(String key);
}
