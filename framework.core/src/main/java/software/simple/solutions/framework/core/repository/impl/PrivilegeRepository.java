package software.simple.solutions.framework.core.repository.impl;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Repository;

import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPrivilegeRepository;

@Repository
public class PrivilegeRepository extends GenericRepository implements IPrivilegeRepository {

	private static final long serialVersionUID = -9173803855268637504L;

	@Override
	public List<Privilege> getPrivileges(List<String> privilegeCodes) throws FrameworkException {
		if (privilegeCodes == null || privilegeCodes.isEmpty()) {
			return null;
		}
		String query = "from Privilege where code in (:codes)";
		ConcurrentMap<String, Object> paramMap = createParamMap();
		paramMap.put("codes", privilegeCodes);
		return createListQuery(query, paramMap);
	}

}
