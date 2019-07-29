package software.simple.solutions.framework.core.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.pojo.PagingResult;
import software.simple.solutions.framework.core.pojo.PagingSetting;
import software.simple.solutions.framework.core.repository.IGenericRepository;
import software.simple.solutions.framework.core.service.ISuperService;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service(value = "superService")
public abstract class SuperService implements ISuperService {

	private static final long serialVersionUID = 5613775047707969778L;

	protected static final Logger serviceLogger = LogManager.getLogger(SuperService.class);

	public static final String FIND_BY_SEARCH = "findBySearch";
	public static final String COUNT_FOR_SEARCH = "countForSearch";
	public static final String GET_FOR_LISTING = "getForListing";
	public static final String GET_BY_ID = "getById";
	public static final String GET_BY_CODE = "getByCode";
	public static final String UPDATE_SINGLE = "updateSingle";
	public static final String UPDATE = "update";
	public static final String UPDATE_AFTER_VALIDATE = "updateAfterValidate";
	public static final String DELETEALL = "deleteAll";
	public static final String IS_CODE_UNIQUE = "isCodeUnique";

	private IGenericRepository genericRepository;

	@Autowired
	protected ApplicationEventPublisher applicationEventPublisher;

	public Property createKeyifNotExists(String key) throws FrameworkException {
		Property property = null;
		if (StringUtils.isNotBlank(key)) {
			property = genericRepository.getBypropertyKey(Property.class, key);
			if (property == null) {
				property = new Property();
				property.setActive(true);
				property.setKey(key);
				property = saveOrUpdate(property, true);
			}
		}
		return property;
	}

	@Override
	public <T> Integer delete(Class<T> cl, Long id) throws FrameworkException {
		Integer deleted = delete(cl, Arrays.asList(id));
		return deleted;
	}

	@Override
	public <T> Integer delete(Class<T> cl, List<Long> ids) throws FrameworkException {
		Integer deleted = getGenericRepository().deleteAll(cl, ids);
		return deleted;
	}

	@Override
	public <T> Integer delete(List<T> entities) throws FrameworkException {
		Integer deleted = getGenericRepository().deleteAll(entities);
		return deleted;
	}

	@Override
	public <T> T restore(Class<T> cl, Long id) throws FrameworkException {
		return getGenericRepository().restore(cl, id);
	}

	@Override
	public <T> PagingResult<T> findBySearch(Object o, PagingSetting pagingSetting) throws FrameworkException {
		return getGenericRepository().findBySearch(o, pagingSetting);
	}

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, List<Long> ids, Boolean active) throws FrameworkException {
		return getGenericRepository().getForListing(cl, ids, active);
	}

	@Override
	public <T, R> List<R> getForListing(Class<T> cl, Boolean active) throws FrameworkException {
		return getGenericRepository().getForListing(cl, active);
	}

	@Override
	public <T, R> List<R> getForListing(Class<T> cl) throws FrameworkException {
		return getGenericRepository().getForListing(cl);
	}

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		// return genericRepository.update(valueObject);
		return null;
	}

	@Override
	public <T, R extends SuperVO> List<T> update(List<R> valueObjects) throws FrameworkException {
		List<T> entities = new ArrayList<T>();
		for (R r : valueObjects) {
			entities.add(updateSingle(r));
		}
		return entities;
	}

	@Override
	public <T> T saveOrUpdate(T entity, boolean newEntity) throws FrameworkException {
		return getGenericRepository().updateSingle(entity, newEntity);
	}

	@Override
	public <T> T getById(Class<T> cl, Long id) throws FrameworkException {
		return getGenericRepository().get(cl, id);
	}

	@Override
	public <T> T get(Class<T> cl, Long id) throws FrameworkException {
		return getGenericRepository().get(cl, id);
	}

	@Override
	public <T> T getByCode(Class<T> cl, String code) throws FrameworkException {
		return getGenericRepository().getByCode(cl, code);
	}

	@Override
	public <T> Boolean isCodeUnique(Class<T> cl, String code) throws FrameworkException {
		return getGenericRepository().isCodeUnique(cl, code);
	}

	@Override
	public <T> Boolean isCodeUnique(Class<T> cl, String code, Long id) throws FrameworkException {
		return getGenericRepository().isCodeUnique(cl, code, id);
	}

	@Override
	public Object updateContent(ContentUpdate contentUpdate) throws FrameworkException {
		return contentUpdate.executeAll();
	}

	public <T extends IGenericRepository> T getGenericRepository() {
		if (genericRepository == null) {
			ServiceRepository serviceRepository = this.getClass().getAnnotation(ServiceRepository.class);
			if (serviceRepository != null && serviceRepository.getClass() != null) {
				genericRepository = ContextProvider.getBean(serviceRepository.claz());
			}
		}
		return (T) genericRepository;
	}

}
