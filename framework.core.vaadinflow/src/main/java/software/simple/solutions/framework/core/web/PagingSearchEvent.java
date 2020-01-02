package software.simple.solutions.framework.core.web;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface PagingSearchEvent {

	public void handleSearch(int currentPage, int maxResult);

	public Long count() throws FrameworkException;
}
