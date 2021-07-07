package io.ecsoya.github.fabric.chaincode;

import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

/**
 * <p>
 * The type Json util.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class JsonUtil {

    /**
     * GS
     */
    private static final Gson GS = new Gson();

    /**
     * Stringify string.
     *
     * @param object the object
     * @return the string
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:01
     */
    public static String stringify(Object object) {
		if (object == null) {
			return "";
		}
		return GS.toJson(object);
	}

    /**
     * Payload byte [ ].
     *
     * @param object the object
     * @return the byte [ ]
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:01
     */
    public static byte[] payload(Object object) {
		return stringify(object).getBytes(StandardCharsets.UTF_8);
	}
}
