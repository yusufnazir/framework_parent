package software.simple.solutions.framework.core.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Privilege;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IPrivilegeRepository;
import software.simple.solutions.framework.core.service.IPrivilegeService;
import software.simple.solutions.framework.core.valueobjects.PrivilegeVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = IPrivilegeRepository.class)
public class PrivilegeService extends SuperService implements IPrivilegeService {

	private static final long serialVersionUID = 1091461735328519788L;
	@Autowired
	private IPrivilegeRepository privilegeRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		PrivilegeVO vo = (PrivilegeVO) valueObject;

		Privilege privilege = new Privilege();
		if (!vo.isNew()) {
			privilege = get(Privilege.class, vo.getId());
		}
		privilege.setCode(vo.getCode());
		privilege.setKey(vo.getKey());

		return (T) saveOrUpdate(privilege, vo.isNew());
	}

	@Override
	public List<Privilege> getPrivileges(List<String> privilegeCodes) throws FrameworkException {
		return privilegeRepository.getPrivileges(privilegeCodes);
	}

}
