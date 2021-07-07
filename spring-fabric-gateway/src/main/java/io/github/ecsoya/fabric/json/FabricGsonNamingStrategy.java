package io.github.ecsoya.fabric.json;

import java.lang.reflect.Field;

import com.google.gson.FieldNamingStrategy;

import io.github.ecsoya.fabric.annotation.FabricName;

/**
 * <p>
 * The type Fabric gson naming strategy.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class FabricGsonNamingStrategy implements FieldNamingStrategy {

	@Override
	public String translateName(Field f) {
		FabricName fabricName = f.getAnnotation(FabricName.class);
		if (fabricName != null) {
			String value = fabricName.value();
			if (value != null && !"".equals(value)) {
				return value;
			}
		}
		return f.getName();
	}

}
