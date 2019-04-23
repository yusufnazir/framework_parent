package software.simple.solutions.framework.core.repository.impl;

import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.EntityFile;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IFileRepository;

@Repository
public class FileRepository extends GenericRepository implements IFileRepository {

	@Override
	public EntityFile findFileByEntityAndType(String entityId, String entityName, String typeOfFile)
			throws FrameworkException {
		String query = "from EntityFile where entityId=:entityId and entity=:entity and typeOfFile=:typeOfFile";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("entityId", entityId);
		paramMap.put("entity", entityName);
		paramMap.put("typeOfFile", typeOfFile);
		return getByQuery(query, paramMap);
	}

}
