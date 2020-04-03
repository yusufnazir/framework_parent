package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface ICountryRepository extends IGenericRepository {

	Boolean isAlpha2Unique(String alpha2, Long id) throws FrameworkException;

	Boolean isAlpha3Unique(String alpha3, Long id) throws FrameworkException;

	Boolean isNameUnique(String name, Long id) throws FrameworkException;

}
