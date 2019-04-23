package software.simple.solutions.framework.core.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.View;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.repository.IViewRepository;
import software.simple.solutions.framework.core.service.IViewService;
import software.simple.solutions.framework.core.valueobjects.SuperVO;
import software.simple.solutions.framework.core.valueobjects.ViewVO;

@Transactional
@Service
@ServiceRepository(claz = IViewRepository.class)
public class ViewService extends SuperService implements IViewService {

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		ViewVO vo = (ViewVO) valueObject;

		View view = null;
		if (vo.isNew()) {
			view = new View();
		} else {
			view = get(View.class, vo.getId());
		}
		view.setCode(vo.getCode().toUpperCase());
		view.setName(vo.getName());
		view.setViewClassName(vo.getViewClassName());
		view.setActive(vo.getActive());

		return (T) saveOrUpdate(view, vo.isNew());
	}

}
