package software.simple.solutions.framework.core.web.view;

import java.util.HashSet;

import com.vaadin.data.ValueProvider;

import io.reactivex.functions.Consumer;
import software.simple.solutions.framework.core.components.CCheckBox;
import software.simple.solutions.framework.core.components.CGridLayout;
import software.simple.solutions.framework.core.components.CRichTextArea;
import software.simple.solutions.framework.core.components.CTextArea;
import software.simple.solutions.framework.core.components.FilterView;
import software.simple.solutions.framework.core.components.FormView;
import software.simple.solutions.framework.core.components.filter.CStringIntervalLayout;
import software.simple.solutions.framework.core.components.select.ActiveSelect;
import software.simple.solutions.framework.core.components.select.LanguageSelect;
import software.simple.solutions.framework.core.config.PropertyHolder;
import software.simple.solutions.framework.core.entities.ILocalized;
import software.simple.solutions.framework.core.entities.MappedSuperClass;
import software.simple.solutions.framework.core.entities.Property;
import software.simple.solutions.framework.core.entities.PropertyPerLocale;
import software.simple.solutions.framework.core.exceptions.FrameworkException;
import software.simple.solutions.framework.core.properties.PropertyPerLocaleProperty;
import software.simple.solutions.framework.core.service.facade.PropertyPerLocaleServiceFacade;
import software.simple.solutions.framework.core.util.ComponentUtil;
import software.simple.solutions.framework.core.valueobjects.PropertyPerLocaleVO;
import software.simple.solutions.framework.core.web.BasicTemplate;

public class PropertyPerLocaleView extends BasicTemplate<PropertyPerLocale> {

	private static final long serialVersionUID = 6503015064562511801L;

	public PropertyPerLocaleView() {
		setEntityClass(PropertyPerLocale.class);
		setServiceClass(PropertyPerLocaleServiceFacade.class);
		setFilterClass(Filter.class);
		setFormClass(Form.class);

		getUpdateObserver().subscribe(new Consumer<Object>() {

			@Override
			public void accept(Object t) throws Exception {
				if (t != null) {
					if (t instanceof HashSet) {
						for (Object item : (HashSet<?>) t) {
							PropertyHolder.updateKeyValue((PropertyPerLocale) item);
						}
					} else {
						PropertyHolder.updateKeyValue((PropertyPerLocale) t);
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
				return source.getProperty() == null ? null : source.getProperty().getKey();
			}
		}, PropertyPerLocaleProperty.PROPERTY);
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

		private CStringIntervalLayout propertyFld;
		private LanguageSelect languageFld;
		private CStringIntervalLayout valueFld;
		private ActiveSelect activeFld;

		@Override
		public void executeBuild() throws FrameworkException {
			propertyFld = addField(CStringIntervalLayout.class, PropertyPerLocaleProperty.PROPERTY, 0, 0);
			languageFld = addField(LanguageSelect.class, PropertyPerLocaleProperty.LANGUAGE, 0, 1);
			valueFld = addField(CStringIntervalLayout.class, PropertyPerLocaleProperty.VALUE, 1, 0);
			activeFld = addField(ActiveSelect.class, PropertyPerLocaleProperty.ACTIVE, 1, 1);

			MappedSuperClass parentEntity = getParentEntity();
			if (parentEntity != null && parentEntity instanceof ILocalized) {
				ILocalized property = (ILocalized) getParentEntity();
				propertyFld.setValue(property.getKey());
				propertyFld.setReadOnly(true);
			}
		}

		@Override
		public Object getCriteria() {
			PropertyPerLocaleVO vo = new PropertyPerLocaleVO();
			vo.setActive(activeFld.getItemId());
			vo.setPropertyInterval(propertyFld.getValue());
			vo.setValueInterval(valueFld.getValue());
			vo.setLanguageId(languageFld.getItemId());
			return vo;
		}

	}

	public class Form extends FormView {

		private static final long serialVersionUID = 6109727427163734676L;

		private CGridLayout formGrid;
		private CTextArea propertyFld;
		private CRichTextArea valueFld;
		private LanguageSelect languageFld;
		private CCheckBox activeFld;

		private PropertyPerLocale propertyPerLocale;

		public Form() {
			super();
		}

		@Override
		public void executeBuild() {
			formGrid = createFormGrid();
			addComponent(formGrid);
		}

		private CGridLayout createFormGrid() {
			formGrid = ComponentUtil.createGrid();

			propertyFld = formGrid.addField(CTextArea.class, PropertyPerLocaleProperty.PROPERTY, 0, 0, 0, 1);

			languageFld = formGrid.addField(LanguageSelect.class, PropertyPerLocaleProperty.LANGUAGE, 0, 2);

			activeFld = formGrid.addField(CCheckBox.class, PropertyPerLocaleProperty.ACTIVE, 0, 3);

			valueFld = formGrid.addField(CRichTextArea.class, PropertyPerLocaleProperty.VALUE, 0, 4);
			valueFld.setWidth("1000px");
			valueFld.setHeight("600px");

			return formGrid;
		}

		@SuppressWarnings("unchecked")
		@Override
		public PropertyPerLocale setFormValues(Object entity) throws FrameworkException {
			propertyPerLocale = (PropertyPerLocale) entity;
			activeFld.setValue(propertyPerLocale.getActive());
			propertyFld.setValue(
					propertyPerLocale.getProperty() == null ? null : propertyPerLocale.getProperty().getKey());
			valueFld.setValue(propertyPerLocale.getValue());
			languageFld.setLongValue(propertyPerLocale.getLanguage().getId());

			MappedSuperClass parentEntity = getParentEntity();
			if (parentEntity != null) {
				propertyFld.setReadOnly(true);
			}

			return propertyPerLocale;
		}

		@Override
		public void handleNewForm() throws FrameworkException {
			activeFld.setValue(true);

			MappedSuperClass parentEntity = getParentEntity();
			if (parentEntity != null && parentEntity instanceof Property) {
				Property property = (Property) getParentEntity();
				propertyFld.setValue(property.getKey());
				propertyFld.setReadOnly(true);
			}
		}

		@Override
		public Object getFormValues() throws FrameworkException {
			PropertyPerLocaleVO vo = new PropertyPerLocaleVO();

			vo.setId(propertyPerLocale == null ? null : propertyPerLocale.getId());

			vo.setActive(activeFld.getValue());
			vo.setProperty(propertyFld.getValue());
			vo.setValue(valueFld.getValue());
			vo.setLanguageId(languageFld.getItemId());

			return vo;
		}

	}

}
