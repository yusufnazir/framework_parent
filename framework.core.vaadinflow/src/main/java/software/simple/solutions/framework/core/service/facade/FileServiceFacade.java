package software.simple.solutions.framework.core.service.facade;

import com.vaadin.flow.component.UI;

import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.service.IFileService;
import software.simple.solutions.framework.core.valueobjects.EntityFileVO;

public class FileServiceFacade extends SuperServiceFacade<IFileService> implements IFileService {

	public static final long serialVersionUID = 7599887617188766564L;

	public FileServiceFacade(UI ui, Class<IFileService> s) {
		super(ui, s);
	}

	public static FileServiceFacade get(UI ui) {
		return new FileServiceFacade(ui, IFileService.class);
	}

	@Override
	public EntityFile upLoadFile(EntityFileVO vo) throws FrameworkException {
		return service.upLoadFile(vo);
	}

	@Override
	public EntityFile findFileByEntityAndType(String entityId, String entityName, String typeOfFile)
			throws FrameworkException {
		return service.findFileByEntityAndType(entityId, entityName, typeOfFile);
	}

	@Override
	public void deleteFileByEntityAndType(String entityId, String entityName, String typeOfFile)
			throws FrameworkException {
		service.deleteFileByEntityAndType(entityId, entityName, typeOfFile);
	}
}
