package io.github.ecsoya.fabric.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import io.github.ecsoya.fabric.annotation.FabricIgnore;

/**
 * <p>
 * The type Fabric gson deserialize exclusion strategy.
 *
 * @author Jin Liu (jin.liu@soyatec.com)
 * @date 2021 -07-07
 * @see FabricIgnore
 * @see FabricGson
 */
public class FabricGsonDeserializeExclusionStrategy implements ExclusionStrategy {

    /**
     * INSTANCE
     */
    public static final FabricGsonDeserializeExclusionStrategy INSTANCE = new FabricGsonDeserializeExclusionStrategy();

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		FabricIgnore ignore = f.getAnnotation(FabricIgnore.class);
		return ignore != null && ignore.deserialize();
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		FabricIgnore ignore = clazz.getAnnotation(FabricIgnore.class);
		return ignore != null && ignore.deserialize();
	}

}
