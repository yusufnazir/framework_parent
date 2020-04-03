package software.simple.solutions.framework.core.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import software.simple.solutions.framework.core.annotations.ServiceRepository;
import software.simple.solutions.framework.core.entities.Currency;
import software.simple.solutions.framework.core.exceptions.Arg;
import software.simple.solutions.framework.core.exceptions.ExceptionBuilder;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.CurrencyProperty;
import software.simple.solutions.framework.core.properties.SystemMessageProperty;
import software.simple.solutions.framework.core.repository.ICurrencyRepository;
import software.simple.solutions.framework.core.service.ICurrencyService;
import software.simple.solutions.framework.core.valueobjects.CurrencyVO;
import software.simple.solutions.framework.core.valueobjects.SuperVO;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
@Service
@ServiceRepository(claz = ICurrencyRepository.class)
public class CurrencyService extends SuperService implements ICurrencyService {

	private static final long serialVersionUID = -4663562154895643047L;

	@Autowired
	private ICurrencyRepository currencyRepository;

	@Override
	public <T, R extends SuperVO> T updateSingle(R valueObject) throws FrameworkException {
		CurrencyVO vo = (CurrencyVO) valueObject;

		if (StringUtils.isBlank(vo.getCode())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(CurrencyProperty.CODE));
		}

		if (StringUtils.isBlank(vo.getName())) {
			throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_IS_REQUIRED,
					Arg.build().key(CurrencyProperty.NAME));
		}

		Currency currency = new Currency();
		if (vo.isNew()) {
			Boolean isCodeUnique = currencyRepository.isCodeUnique(Currency.class, vo.getCode());
			if (!isCodeUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(CurrencyProperty.CODE));
			}
		} else {
			Boolean isCodeUnique = currencyRepository.isCodeUnique(Currency.class, vo.getCode(), vo.getId());
			if (!isCodeUnique) {
				throw ExceptionBuilder.FRAMEWORK_EXCEPTION.build(SystemMessageProperty.FIELD_MUST_BE_UNIQUE,
						Arg.build().key(CurrencyProperty.CODE));
			}
			currency = get(Currency.class, vo.getId());
		}
		currency.setCode(vo.getCode());
		currency.setName(vo.getName());
		currency.setActive(vo.getActive());

		return (T) saveOrUpdate(currency, vo.isNew());
	}
}
