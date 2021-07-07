package io.github.ecsoya.fabric.bean;

import java.util.HashMap;
import java.util.Map;

import io.github.ecsoya.fabric.annotation.FabricValues;
import lombok.Data;

/**
 * Common fabric object bean.
 * <p>
 * Using the CompositeKey with the id and type to identify a specific object.
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
@Data
public class FabricObject implements IFabricObject {

    /**
     * The key or id of the object.
     */
    private String id;

    /**
     * The type of the object.
     */
    private String type;

    /**
     * Values of current object, it should be convert to/from JSON with gson
     * library.
     */
    private Map<String, Object> values;

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:14
     */
    public void put(String key, Object value) {
		if (values == null) {
			values = new HashMap<String, Object>(16);
		}
		values.put(key, value);
	}
}
