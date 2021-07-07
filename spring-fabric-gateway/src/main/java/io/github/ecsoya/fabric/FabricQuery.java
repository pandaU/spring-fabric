package io.github.ecsoya.fabric;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Fabric query constructor for CouchDB.
 *
 * @author Jin Liu (jin.liu@soyatec.com)
 * @date 2021 -07-07
 * @see <a href=      "http://docs.couchdb.org/en/stable/api/database/find.html?highlight=find#post--db-_find">      CouchDB</a>
 */
public class FabricQuery {

	/**
	 * Equal parameters map.
	 */
	private Map<String, Object> equalsParams = new HashMap<>();

	/**
	 * Like parameters map.
	 */
	private Map<String, String> likeParams = new HashMap<>();

	/**
	 * The type of query.
	 */
	private String type;

	/**
	 * Fabric query
	 *
	 * @param type type
	 */
	public FabricQuery(String type) {
		this.type = type;
	}

	/**
	 * Fabric query
	 */
	public FabricQuery() {
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public String getType() {
		return type;
	}

	/**
	 * Equals fabric query.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return the fabric query
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public FabricQuery equals(String key, Object value) {
		if (key == null || value == null) {
			return this;
		}
		equalsParams.put(key, value);
		return this;
	}

	/**
	 * Like fabric query.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return the fabric query
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public FabricQuery like(String key, String value) {
		if (key == null || value == null) {
			return this;
		}
		likeParams.put(key, value);
		return this;
	}

	/**
	 * Generate query string for CouchDB.
	 *
	 * @return the selector.
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public String selector() {
		JsonObject query = new JsonObject();
		JsonObject selector = new JsonObject();

		if (getType() != null) {
			selector.addProperty("type", type);
		}

		Set<Entry<String, Object>> euqals = equalsParams.entrySet();
		for (Entry<String, Object> entry : euqals) {
			Object value = entry.getValue();
			if (value instanceof Boolean) {
				selector.addProperty(entry.getKey(), (Boolean) value);
			} else if (value instanceof Number) {
				selector.addProperty(entry.getKey(), (Number) value);
			} else if (value instanceof String) {
				selector.addProperty(entry.getKey(), (String) value);
			} else if (value instanceof Character) {
				selector.addProperty(entry.getKey(), (Character) value);
			} else if (value instanceof JsonElement) {
				selector.add(entry.getKey(), (JsonElement) value);
			} else if (value != null) {
				selector.addProperty(entry.getKey(), value.toString());
			}
		}
		Set<Entry<String, String>> likes = likeParams.entrySet();
		for (Entry<String, String> entry : likes) {
			JsonObject regex = new JsonObject();
			regex.addProperty("$regex", ".*?" + entry.getValue() + ".*?");
			selector.add(entry.getKey(), regex);
		}
		query.add("selector", selector);
		Gson gson = new Gson();
		return gson.toJson(query);
	}

	/**
	 * Build fabric query.
	 *
	 * @param type  the type
	 * @param key   the key
	 * @param value the value
	 * @return the fabric query
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public static FabricQuery build(String type, String key, Object value) {
		FabricQuery query = new FabricQuery(type);
		return query.equals(key, value);
	}
}
