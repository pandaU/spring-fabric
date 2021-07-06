package io.github.ecsoya.fabric.json;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.github.ecsoya.fabric.annotation.FabricJson;
import io.github.ecsoya.fabric.annotation.FabricValues;
import io.github.ecsoya.fabric.utils.AnnotationUtils;
import io.github.ecsoya.fabric.utils.FabricUtil;
import lombok.Data;

@Data
public class FabricWrapper {

	private String key;

	private String type;

	private String value;

	public FabricWrapper(Object object) {
		if (object != null) {
			initialize(object);
		}
	}

	private void initialize(Object object) {
		if (object == null) {
			return;
		}
		key = FabricUtil.resolveFabricId(object);
		type = FabricUtil.resolveFabricType(object);

		IFabricJsonConverter converter = new DefaultFabricJsonConverter();
		FabricJson fabricJson = object.getClass().getAnnotation(FabricJson.class);
		if (fabricJson != null) {
			Class<? extends IFabricJsonConverter> converterClass = fabricJson.converter();
			if (converterClass != null) {
				try {
					converter = converterClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
				}
			}
		}
		AnnotatedElement valuesElement = AnnotationUtils.getAnnotatedElement(object.getClass(), FabricValues.class);
		if (valuesElement != null) {
			Object values = AnnotationUtils.getValue(object, valuesElement, Object.class);
			if (values != null) {
				value = converter.toString(values);
			} else {
				value = "";
			}
		} else {
			if (object instanceof String){
				value = object.toString();
			}else {
				try {
					Method method = object.getClass().getDeclaredMethod("getValues", null);
					Map data = (Map)method.invoke(object, null);
					Map clone = (Map)method.invoke(object, null);
					data.put("values",clone);
					data.put("type",type);
					data.put("key",key);
					value = converter.toString(data);
				} catch (Exception e) {
				}
			}
		}
	}

	public boolean isValid() {
		return key != null && type != null && value != null;
	}

}
