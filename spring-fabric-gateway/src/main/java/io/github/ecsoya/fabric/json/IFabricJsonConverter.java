package io.github.ecsoya.fabric.json;

/**
 * <p>
 * The interface Fabric json converter.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public interface IFabricJsonConverter {

    /**
     * To string string.
     *
     * @param object the object
     * @return the string
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    String toString(Object object);

    /**
     * From string t.
     *
     * @param <T>    the type parameter
     * @param string the string
     * @param type   the type
     * @return the t
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    <T> T fromString(String string, Class<T> type);

    /**
     * Default converter fabric json converter.
     *
     * @return the fabric json converter
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    static IFabricJsonConverter defaultConverter() {
		return DefaultFabricJsonConverter.INSTANCE;
	}
}
