package io.github.ecsoya.fabric.json;

/**
 * <p>
 * The type Default fabric json converter.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class DefaultFabricJsonConverter implements IFabricJsonConverter {

    /**
     * INSTANCE
     */
    public static final DefaultFabricJsonConverter INSTANCE = new DefaultFabricJsonConverter();

	@Override
	public String toString(Object object) {
		return FabricGson.stringify(object);
	}

	@Override
	public <T> T fromString(String string, Class<T> type) {
		return FabricGson.build(string, type);
	}

}
