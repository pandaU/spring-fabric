package io.github.ecsoya.fabric.json;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import io.github.ecsoya.fabric.annotation.FabricIgnore;

/**
 * JSON utility for serialize and deserialize the fabric objects.
 *
 * @author Jin Liu (jin.liu@soyatec.com)
 * @date 2021 -07-07
 * @see FabricIgnore
 * @see FabricGsonDeserializeExclusionStrategy
 * @see FabricGsonSerializeExclusionStrategy
 */
public class FabricGson {

    /**
     * gson
     */
    private static Gson gson;
	static {
		gson = new GsonBuilder().enableComplexMapKeySerialization().setDateFormat(DateFormat.LONG)
				.setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).setVersion(1.0)
				.addSerializationExclusionStrategy(FabricGsonSerializeExclusionStrategy.INSTANCE)
				.addDeserializationExclusionStrategy(FabricGsonDeserializeExclusionStrategy.INSTANCE)
				.setFieldNamingStrategy(new FabricGsonNamingStrategy()).create();
	}

    /**
     * Fabric gson
     */
    private FabricGson() {
	}

    /**
     * Stringify string.
     *
     * @param object the object
     * @return the string
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static String stringify(Object object) {
		if (object == null) {
			return null;
		} else if (object instanceof JsonElement) {
			JsonElement element = (JsonElement) object;
			if (element.isJsonPrimitive()) {
				return element.getAsString();
			}
		}
		return gson.toJson(object);
	}

    /**
     * Json json element.
     *
     * @param object the object
     * @return the json element
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static JsonElement json(Object object) {
		if (object == null) {
			return null;
		}
		return gson.toJsonTree(object);
	}

    /**
     * Build t.
     *
     * @param <T>  the type parameter
     * @param json the json
     * @param type the type
     * @return the t
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static <T> T build(String json, Class<T> type) {
		if (json == null) {
			return null;
		}

		return gson.fromJson(json, type);
	}

    /**
     * Build t.
     *
     * @param <T>  the type parameter
     * @param json the json
     * @param type the type
     * @return the t
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static <T> T build(JsonElement json, Class<T> type) {
		if (json == null) {
			return null;
		}

		return gson.fromJson(json, type);
	}

    /**
     * Build list list.
     *
     * @param <T>  the type parameter
     * @param json the json
     * @param type the type
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static <T> List<T> buildList(String json, Class<T> type) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		return buildList(element, type);
	}

    /**
     * Build list list.
     *
     * @param <T>     the type parameter
     * @param element the element
     * @param type    the type
     * @return the list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static <T> List<T> buildList(JsonElement element, Class<T> type) {
		if (element == null || !element.isJsonArray()) {
			return Collections.emptyList();
		}
		List<T> results = new ArrayList<>();

		JsonArray array = element.getAsJsonArray();
		for (JsonElement child : array) {
			T value = null;
			if (child.isJsonObject() && child.getAsJsonObject().has("Key") && child.getAsJsonObject().has("Record")) {
				value = gson.fromJson(child.getAsJsonObject().get("Record"), type);
			} else {
				value = gson.fromJson(child, type);
			}
			results.add(value);
		}
		return results;
	}

}
