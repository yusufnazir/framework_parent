package software.simple.solutions.framework.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Holiday;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.HolidayProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.IHolidayRepository;
import software.simple.solutions.framework.core.service.IHolidayService;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.valueobjects.HolidayVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = IHolidayRepository.class)
public class HolidayService extends SuperService implements IHolidayService {

	private static final long serialVersionUID = -6958881271813455262L;
	@Autowired
	private IHolidayRepository holidayRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		HolidayVO vo = (HolidayVO) valueObject;

		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(HolidayProperty.NAME));
		}

		if (vo.getDate() == null) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(HolidayProperty.DATE));
		}

		Holiday holiday = new Holiday();
		if (vo.isNew()) {
			Boolean isUnique = holidayRepository.isDateUnique(vo.getDate(), null);
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(HolidayProperty.DATE));
			}
		} else {
			Boolean isUnique = holidayRepository.isDateUnique(vo.getDate(), holiday.getId());
			if (!isUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(HolidayProperty.DATE));
			}
			holiday = get(Holiday.class, vo.getId());
		}
		holiday.setDate(vo.getDate());
		holiday.setDay(NumberUtil.getLong(vo.getDate().getDayOfMonth()));
		holiday.setMonth(NumberUtil.getLong(vo.getDate().getMonthValue()));
		holiday.setYear(NumberUtil.getLong(vo.getDate().getYear()));
		holiday.setName(vo.getName());
		holiday.setActive(vo.getActive());

		return (T) saveOrUpdate(holiday, vo.isNew());
	}

}
