package software.simple.solutions.framework.core.service;

import java.io.Serializable;
import java.util.List;

import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

public interface ISuperService extends Serializable {

	public <T> T get(Class<T> cl, Long id) throws FrameworkException;

	public <T> T getById(Class<T> cl, Long id) throws FrameworkException;

	public <T> T getByCode(Class<T> cl, String code) throws FrameworkException;

	public <T, R> List<R> getForListing(Class<T> cl, List<Long> ids, Boolean active) throws FrameworkException;

	/**
	 * Retrieve a collection of data containing and id and a description.
	 * 
	 * @param active
	 * @return
	 * @throws FrameworkException
	 */
	public <T, R> List<R> getForListing(Class<T> cl, Boolean active) throws FrameworkException;

	public <T, R> List<R> getForListing(Class<T> cl) throws FrameworkException;

	/**
	 * Method required to retrieve data for the view.
	 * 
	 * @param startPosition
	 *            TODO
	 * @param maxResult
	 *            TODO
	 * 
	 * @param o:
	 *            ValueObject containing search criteria.
	 * @return List of search results.
	 * @throws FrameworkException
	 */
	public <T> PagingResult<T> findBySearch(Object o, PagingSetting pagingSetting) throws FrameworkException;

	/**
	 * Method for retrieving data without paging.
	 * 
	 * @param o
	 * @return
	 * @throws FrameworkException
	 */
	// public <T> List<T> findBySearch(Object o) throws FrameworkException;

	/**
	 * Checks to see if the given code is unique.
	 * 
	 * @param code
	 * @return
	 * @throws FrameworkException
	 */
	public <T> Boolean isCodeUnique(Class<T> cl, String code) throws FrameworkException;

	/**
	 * Checks to see if the given code is unique for every other record
	 * except @param id.
	 * 
	 * @param code
	 * @return
	 * @throws FrameworkException
	 */
	public <T> Boolean isCodeUnique(Class<T> cl, String code, Long id) throws FrameworkException;

	/**
	 * Method used for persisting a single object.
	 * 
	 * @param valueObject
	 * @return
	 * @throws FrameworkException
	 */
	public <T, R extends SuperVO> T updateSingle(R entityVO) throws FrameworkException;

	/**
	 * Method used for persisting data, wether an insert or update. Basic method
	 * called by templates.
	 * 
	 * @param valueObjects
	 * @return
	 * @throws FrameworkException
	 */
	public <T, R extends SuperVO> List<T> update(List<R> entityVOs) throws FrameworkException;

	/**
	 * Handles deleting objects. Basic method called from templates.
	 * 
	 * @param userId
	 *            TODO
	 * @param ids
	 * 
	 * @return
	 * @throws FrameworkException
	 */
	public <T> Integer delete(Class<T> cl, List<Long> entities, Long userId) throws FrameworkException;

	public <T> Integer delete(Class<T> cl, Long id, Long userId) throws FrameworkException;

	public <T> Integer delete(List<T> entities, Long userId) throws FrameworkException;

	public interface ContentUpdate {

		public Object executeAll() throws FrameworkException;
	}

	public Object updateContent(ContentUpdate contentUpdate) throws FrameworkException;

	<T> T saveOrUpdate(T entity, boolean newEntity) throws FrameworkException;

	public <T> T restore(Class<T> cl, Long id) throws FrameworkException;

}
