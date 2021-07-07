package io.github.ecsoya.fabric;

import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import io.github.ecsoya.fabric.bean.FabricHistory;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import io.github.ecsoya.fabric.json.FabricGson;
import io.github.ecsoya.fabric.utils.FabricUtil;

/**
 * <p>
 * The type Fabric query response.
 *
 * @param <T> the type parameter
 * @author XieXiongXiong
 * @date 2021 -07-07  *
 */
public class FabricQueryResponse<T> extends FabricResponse {

	/**
	 * Data
	 */
	public final T data;

	/**
	 * Metadata
	 */
	public FabricQueryResponseMetadata metadata;

	/**
	 * Fabric query response
	 *
	 * @param status   status
	 * @param errorMsg error msg
	 * @param data     data
	 */
	public FabricQueryResponse(int status, String errorMsg, T data) {
		super(status, errorMsg);
		this.data = data;
	}

	/**
	 * Gets metadata.
	 *
	 * @return the metadata
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public FabricQueryResponseMetadata getMetadata() {
		return metadata;
	}

	/**
	 * Sets metadata.
	 *
	 * @param metadata the metadata
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public void setMetadata(FabricQueryResponseMetadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean isOk(boolean all) {
		if (all) {
			return data != null && super.isOk(all);
		}
		return super.isOk(all);
	}

	/**
	 * Failure fabric query response.
	 *
	 * @param <T>      the type parameter
	 * @param errorMsg the error msg
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public static <T> FabricQueryResponse<T> failure(String errorMsg) {
		return new FabricQueryResponse<>(FAILURE, errorMsg, null);
	}

	/**
	 * Success fabric query response.
	 *
	 * @param <T>  the type parameter
	 * @param data the data
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public static <T> FabricQueryResponse<T> success(T data) {
		return new FabricQueryResponse<T>(SUCCESS, null, data);
	}

	/**
	 * Build fabric query response.
	 *
	 * @param <T>  the type parameter
	 * @param data the data
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public static <T> FabricQueryResponse<T> build(T data) {
		if (data == null) {
			return failure(null);
		}
		return success(data);
	}

	/**
	 * Create fabric query response.
	 *
	 * @param <T>  the type parameter
	 * @param res  the res
	 * @param type the type
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public static <T> FabricQueryResponse<T> create(ProposalResponse res, Class<T> type) {
		Status status = res.getStatus();
		if (status != Status.SUCCESS) {
			return failure(res.getMessage());
		} else {
			if (type != null) {
				try {
					int chaincodeStatus = res.getChaincodeActionResponseStatus();
					if (chaincodeStatus != -1) {

						byte[] payload = res.getChaincodeActionResponsePayload();
						return create(payload, type);
					} else {
						return failure("Chaincode executed failure.");
					}
				} catch (InvalidArgumentException e) {
					return failure("Chaincode error: " + e.getMessage());
				}
			} else {
				return success(null);
			}
		}
	}

	/**
	 * Create fabric query response.
	 *
	 * @param <T>     the type parameter
	 * @param payload the payload
	 * @param type    the type
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	public static <T> FabricQueryResponse<T> create(byte[] payload, Class<T> type) {
		if (payload == null) {
			return success(null);
		}
		T payloadData = parsePayload(new String(payload, Charset.forName("utf-8")), type);
		return success(payloadData);
	}

	/**
	 * Parse payload t.
	 *
	 * @param <T>   the type parameter
	 * @param value the value
	 * @param type  the type
	 * @return the t
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:21
	 */
	@SuppressWarnings("unchecked")
	private static <T> T parsePayload(String value, Class<T> type) {
		if (value == null) {
			return null;
		}
		if (String.class == type) {
			return (T) value;
		} else if (Number.class.isAssignableFrom(type)) {
			try {
				return (T) NumberFormat.getInstance().parse(value);
			} catch (ParseException e) {
				return null;
			}
		} else if (Boolean.class == type || boolean.class == type) {
			try {
				return (T) Boolean.valueOf(value);
			} catch (Exception e) {
				return null;
			}
		} else if (!"".equals(value)) {
			return FabricUtil.build(value, type);
		}
		return null;
	}

	/**
	 * Many fabric query response.
	 *
	 * @param <T>  the type parameter
	 * @param res  the res
	 * @param type the type
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:22
	 */
	public static <T> FabricQueryResponse<List<T>> many(ProposalResponse res, Class<T> type) {
		Status status = res.getStatus();
		if (status != Status.SUCCESS) {
			return failure(res.getMessage());
		} else {
			if (type != null) {
				try {
					int chaincodeStatus = res.getChaincodeActionResponseStatus();
					if (chaincodeStatus != -1) {
						byte[] payload = res.getChaincodeActionResponsePayload();
						return parsePayloadMany(new String(payload, Charset.forName("utf-8")), type);
					} else {
						return failure("Chaincode executed failure.");
					}
				} catch (InvalidArgumentException e) {
					return failure("Chaincode error: " + e.getMessage());
				}
			} else {
				return success(null);
			}
		}
	}

	/**
	 * Many fabric query response.
	 *
	 * @param <T>      the type parameter
	 * @param payloads the payloads
	 * @param type     the type
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:22
	 */
	public static <T> FabricQueryResponse<List<T>> many(byte[] payloads, Class<T> type) {
		String json = new String(payloads, Charset.forName("utf-8"));
		return parsePayloadMany(json, type);
	}

	/**
	 * Parse payload many fabric query response.
	 *
	 * @param <T>  the type parameter
	 * @param json the json
	 * @param type the type
	 * @return the fabric query response
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:22
	 */
	private static <T> FabricQueryResponse<List<T>> parsePayloadMany(String json, Class<T> type) {
		try {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(json);

			JsonArray array = null;
			JsonElement meta = null;
			if (element.isJsonArray()) {
				// 普通查询模式
				array = element.getAsJsonArray();
			} else if (element.isJsonObject()) {
				// 分页查询模式
				JsonObject object = element.getAsJsonObject();
				JsonElement data = object.get("data");
				if (data.isJsonArray()) {
					array = data.getAsJsonArray();
				}

				meta = object.get("meta");
			}
			List<T> results = new ArrayList<>();

			if (array != null) {
				for (JsonElement child : array) {
					if (type == FabricHistory.class ){
						JsonObject object = child.getAsJsonObject();
						String value = object.get("value").getAsString();
						JsonElement jsonElement = parser.parse(value);
						object.add("value",jsonElement);
					}
					T value = FabricUtil.build(FabricGson.stringify(child), type);
					results.add(value);
				}
			}
			FabricQueryResponse<List<T>> res = success(results);

			if (meta != null) {
				res.setMetadata(FabricGson.build(meta, FabricQueryResponseMetadata.class));
			}
			return res;

		} catch (JsonSyntaxException e) {
			return FabricQueryResponse.failure(e.getMessage());
		}
	}
}
