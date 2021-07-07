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

/**
 * <p>
 * The type Fabric wrapper.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Data
public class FabricWrapper {

    /**
     * Key
     */
    private String key;

    /**
     * Type
     */
    private String type;

    /**
     * Value
     */
    private String value;

    /**
     * Fabric wrapper
     *
     * @param object object
     */
    public FabricWrapper(Object object) {
		if (object != null) {
			initialize(object);
		}
	}

    /**
     * Initialize.
     *
     * @param object the object
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
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
			value = converter.toString(object);
		}
	}

    /**
     * Is valid boolean.
     *
     * @return the boolean
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public boolean isValid() {
		return key != null && type != null && value != null;
	}

}
