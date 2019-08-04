package software.simple.solutions.framework.core.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IFileRepository;
import software.simple.solutions.framework.core.service.IConfigurationService;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;

@Service
@Transactional(propagation=Propagation.REQUIRED, rollbackFor = Exception.class)
@ServiceRepository(claz = IFileRepository.class)
public class FileService extends SuperService implements IFileService {

	private static final long serialVersionUID = -9174327437240882209L;

	@Autowired
	private IFileRepository fileRepository;

	@Autowired
	private IConfigurationService configurationService;

	@Override
	public EntityFile upLoadFile(EntityFileVO vo) throws FrameworkException {
		EntityFile entityFile = findFileByEntityAndType(vo.getEntityId(), vo.getEntityName(), vo.getTypeOfFile());
		boolean isNew = (entityFile == null);
		if (entityFile == null) {
			entityFile = new EntityFile();
			entityFile.setEntity(vo.getEntityName());
			entityFile.setEntityId(vo.getEntityId());
			entityFile.setTypeOfFile(vo.getTypeOfFile());
		}
		entityFile.setName(vo.getFilename());
		entityFile.setFilePath(vo.getFilePath());
		if (vo.isDatabase()) {
			entityFile.setFileObject(vo.getFileObject());
		}
		return saveOrUpdate(entityFile, isNew);
	}

	@Override
	public EntityFile findFileByEntityAndType(String entityId, String entityName, String typeOfFile)
			throws FrameworkException {
		return fileRepository.findFileByEntityAndType(entityId, entityName, typeOfFile);
	}

	@Override
	public void deleteFileByEntityAndType(String entityId, String entityName, String typeOfFile)
			throws FrameworkException {
		fileRepository.deleteFileByEntityAndType(entityId, entityName, typeOfFile);
	}

	@Override
	public <T> Integer delete(Class<T> cl, Long id) throws FrameworkException {
		EntityFile entityFile = (EntityFile) get(cl, id);
		String filePath = entityFile.getFilePath();
		new File(filePath).delete();
		return super.delete(cl, id);
	}

}
