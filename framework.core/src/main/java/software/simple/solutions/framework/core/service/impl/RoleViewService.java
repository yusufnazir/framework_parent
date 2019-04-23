package software.simple.solutions.framework.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Role;
import software.simple.solutions.framework.core.entities.RoleView;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IRoleViewRepository;
import software.simple.solutions.framework.core.service.IRoleViewService;
import software.simple.solutions.framework.core.valueobjects.RoleViewVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional
@Service
@ServiceRepository(claz = IRoleViewRepository.class)
public class RoleViewService extends SuperService implements IRoleViewService {

	@Autowired
	private IRoleViewRepository roleViewRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		RoleViewVO vo = (RoleViewVO) valueObject;

		RoleView roleView = new RoleView();
		if (!vo.isNew()) {
			roleView = get(RoleView.class, vo.getId());
		}
		roleView.setRole(get(Role.class, vo.getRoleId()));
		roleView.setView(get(View.class, vo.getViewId()));
		roleView.setActive(vo.getActive());

		return (T) saveOrUpdate(roleView, vo.isNew());
	}

}
