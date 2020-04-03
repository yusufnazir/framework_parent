package software.simple.solutions.framework.core.repository;

import java.time.LocalDate;

import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IHolidayRepository extends IGenericRepository {

	Boolean isDateUnique(LocalDate date, Long id) throws FrameworkException;

}
