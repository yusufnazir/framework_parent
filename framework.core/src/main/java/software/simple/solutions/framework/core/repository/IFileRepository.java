package software.simple.solutions.framework.core.repository;

import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.FrameworkException;

public interface IFileRepository extends IGenericRepository {

	EntityFile findFileByEntityAndType(String entityId, String entityName, String typeOfFile) throws FrameworkException;

}
