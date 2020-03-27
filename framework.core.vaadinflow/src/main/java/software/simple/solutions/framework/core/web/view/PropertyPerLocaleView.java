package software.simple.solutions.framework.core.web.view;

import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;

import io.reactivex.functions.Consumer;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.components.select.LanguageSelect;
import software.simple.solutions.framework.core.config.PropertyHolder;
import software.simple.solutions.framework.core.constants.Operator;
import software.simple.solutions.framework.core.constants.ReferenceKey;
import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PropertyPerLocaleProperty;
import software.simple.solutions.framework.core.service.IPropertyService;
import software.simple.solutions.framework.core.service.facade.PropertyPerLocaleServiceFacade;
import software.simple.solutions.framework.core.util.ContextProvider;
import software.simple.solutions.framework.core.util.NumberUtil;
import software.simple.solutions.framework.core.valueobjects.PropertyPerLocaleVO;
import software.simple.solutions.framework.core.web.BasicTemplate;
import software.simple.solutions.framework.core.web.FilterView;
import software.simple.solutions.framework.core.web.flow.MainView;
import software.simple.solutions.framework.core.web.routing.Routes;
import software.simple.solutions.framework.core.web.view.forms.PropertyPerLocaleForm;

@Route(value = Routes.PROPERTY_PER_LOCALE, layout = MainView.class)
public class PropertyPerLocaleView extends BasicTemplate<PropertyPerLocale> {

	private static final long serialVersionUID = 6503015064562511801L;

	public PropertyPerLocaleView() {
		setEntityClass(PropertyPerLocale.class);
		setServiceClass(PropertyPerLocaleServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(PropertyPerLocaleForm.class);
		setParentReferenceKey(ReferenceKey.PROPERTY_PER_LOCALE);
		setEditRoute(Routes.PROPERTY_PER_LOCALE_EDIT);

		getUpdateObserver().subscribe(new Consumer<Object>() {

			@Override
			public void accept(Object t) throws Exception {
				if (t != null) {
					IPropertyService propertyService = ContextProvider.getBean(IPropertyService.class);
					if (t instanceof HashSet) {
						for (Object item : (HashSet<?>) t) {
							PropertyPerLocale propertyPerLocale = (PropertyPerLocale) item;
							if (propertyPerLocale.getReferenceKey().equalsIgnoreCase(ReferenceKey.PROPERTY)) {
								Long id = NumberUtil.getLong(propertyPerLocale.getReferenceId());
								Property property = propertyService.get(Property.class, id);
								PropertyHolder.updateKeyValue(property.getKey(), propertyPerLocale);
							}
						}
					} else {
						PropertyPerLocale propertyPerLocale = (PropertyPerLocale) t;
						if (propertyPerLocale.getReferenceKey().equalsIgnoreCase(ReferenceKey.PROPERTY)) {
							Long id = NumberUtil.getLong(propertyPerLocale.getReferenceId());
							Property property = propertyService.get(Property.class, id);
							PropertyHolder.updateKeyValue(property.getKey(), propertyPerLocale);
						}
					}
				}
			}
		});
	}

	@Override
	public void setUpCustomColumns() {
		addContainerProperty(new ValueProvider<PropertyPerLocale, String>() {

			@Override
			public String apply(PropertyPerLocale source) {
				return source.getReferenceKey();
			}
		}, PropertyPerLocaleProperty.REFERENCE_KEY);
		addContainerProperty(new ValueProvider<PropertyPerLocale, String>() {

			@Override
			public String apply(PropertyPerLocale source) {
				return source.getReferenceId();
			}
		}, PropertyPerLocaleProperty.REFERENCE_ID);
		addContainerProperty(new ValueProvider<PropertyPerLocale, String>() {

			@Override
			public String apply(PropertyPerLocale source) {
				return source.getLanguage() == null ? null : source.getLanguage().getCode();
			}
		}, PropertyPerLocaleProperty.LANGUAGE);
		addContainerProperty(PropertyPerLocale::getValue, PropertyPerLocaleProperty.VALUE);
	}

	/**
	 * Filter implementation
	 * 
	 * @author yusuf
	 *
	 */
	public class Filter extends FilterView {

		private static final long serialVersionUID = 117780868467918033L;

		// private CStringIntervalLayout propertyFld;
		private CStringIntervalLayout referenceKeyFld;
		private CStringIntervalLayout referenceIdFld;
		private LanguageSelect languageFld;
		private CStringIntervalLayout valueFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			// propertyFld = addField(CStringIntervalLayout.class,
			// PropertyPerLocaleProperty.PROPERTY, 0, 0);
			referenceKeyFld = addField(CStringIntervalLayout.class, PropertyPerLocaleProperty.REFERENCE_KEY, 0, 0);
			referenceIdFld = addField(CStringIntervalLayout.class, PropertyPerLocaleProperty.REFERENCE_ID, 0, 1);
			referenceIdFld.setOperator(Operator.EQ);
			referenceIdFld.setOperatorDisabled();
			languageFld = addField(LanguageSelect.class, PropertyPerLocaleProperty.LANGUAGE, 0, 2);
			valueFld = addField(CStringIntervalLayout.class, PropertyPerLocaleProperty.VALUE, 1, 0);
			activeFld = addField(ActiveSelect.class, PropertyPerLocaleProperty.ACTIVE, 1, 1);

			String referenceKey = getReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_KEY);
			if (StringUtils.isNotBlank(referenceKey)) {
				referenceKeyFld.setValue(referenceKey);
				referenceKeyFld.setReadOnly(true);
			}
			String referenceId = getReferenceKey(ReferenceKey.LANGUAGE_PROPERTY_REFERENCE_ID);
			if (StringUtils.isNotBlank(referenceId)) {
				referenceIdFld.setValue(referenceId);
				referenceIdFld.setReadOnly(true);
			}
		}

		@Override
		public Object getCriteria() {
			PropertyPerLocaleVO vo = new PropertyPerLocaleVO();
			vo.setActive(activeFld.getItemId());
			// vo.setPropertyInterval(propertyFld.getValue());
			vo.setReferenceKeyInterval(referenceKeyFld.getValue());
			vo.setReferenceIdInterval(referenceIdFld.getValue());
			vo.setValueInterval(valueFld.getValue());
			vo.setLanguageId(languageFld.getItemId());
			return vo;
		}

	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

}
