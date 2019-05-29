package software.simple.solutions.framework.core.service;

import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;

public interface IFileService extends ISuperService {

	EntityFile upLoadFile(EntityFileVO vo) throws FrameworkException;

	EntityFile findFileByEntityAndType(String entityId, String entityName, String typeOfFile) throws FrameworkException;

	void deleteFileByEntityAndType(String entityId, String entityName, String typeOfFile) throws FrameworkException;

}
