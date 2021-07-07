package io.github.ecsoya.fabric.utils;

import java.lang.reflect.AnnotatedElement;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import io.github.ecsoya.fabric.annotation.FabricId;
import io.github.ecsoya.fabric.annotation.FabricJson;
import io.github.ecsoya.fabric.annotation.FabricType;
import io.github.ecsoya.fabric.bean.IFabricObject;
import io.github.ecsoya.fabric.json.IFabricJsonConverter;

/**
 * <p>
 * The type Fabric util.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class FabricUtil {

    /**
     * String to hash byte [ ].
     *
     * @param str the str
     * @return the byte [ ]
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static byte[] stringToHash(String str) {
		if (str == null || "".equals(str)) {
			return null;
		}
		try {
			return Hex.decodeHex(str.toCharArray());
		} catch (DecoderException e) {
			return null;
		}
	}

    /**
     * Hash to string string.
     *
     * @param hash the hash
     * @return the string
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static String hashToString(byte[] hash) {
		if (hash == null) {
			return null;
		}
		return Hex.encodeHexString(hash);
	}

    /**
     * Gets converter.
     *
     * @param type the type
     * @return the converter
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static IFabricJsonConverter getConverter(Class<?> type) {
		if (type == null) {
			return IFabricJsonConverter.defaultConverter();
		}
		FabricJson fabricJson = type.getAnnotation(FabricJson.class);
		if (fabricJson == null) {
			return IFabricJsonConverter.defaultConverter();
		}
		Class<? extends IFabricJsonConverter> converter = fabricJson.converter();
		if (converter != null) {
			try {
				return converter.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
			}
		}
		return IFabricJsonConverter.defaultConverter();
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
		if (json == null || type == null) {
			return null;
		}
		return getConverter(type).fromString(json, type);
	}

    /**
     * Resolve fabric id string.
     *
     * @param object the object
     * @return the string
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static String resolveFabricId(Object object) {
		if (object == null) {
			return null;
		} else if (object instanceof IFabricObject) {
			return ((IFabricObject) object).getId();
		} else {
			AnnotatedElement keyElement = AnnotationUtils.getAnnotatedElement(object.getClass(), FabricId.class);
			if (keyElement != null) {
				return AnnotationUtils.getValue(object, keyElement, String.class);
			}
			return null;
		}
	}

    /**
     * Resolve fabric type string.
     *
     * @param object the object
     * @return the string
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public static String resolveFabricType(Object object) {
		if (object == null) {
			return IFabricObject.DEFAULT_TYPE;
		} else if (object instanceof Class<?>) {
			Class<?> type = (Class<?>) object;
			try {
				return resolveFabricType(type.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				return IFabricObject.DEFAULT_TYPE;
			}
		} else if (object instanceof IFabricObject) {
			return ((IFabricObject) object).getType();
		} else {
			AnnotatedElement typeElement = AnnotationUtils.getAnnotatedElement(object.getClass(), FabricType.class);
			if (typeElement != null) {
				return AnnotationUtils.getValue(object, typeElement, String.class);
			} else {
				FabricJson fabricJson = object.getClass().getAnnotation(FabricJson.class);
				if (fabricJson != null) {
					return fabricJson.type();
				}
			}
			return IFabricObject.DEFAULT_TYPE;
		}
	}
}
