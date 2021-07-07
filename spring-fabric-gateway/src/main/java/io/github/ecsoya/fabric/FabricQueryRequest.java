package io.github.ecsoya.fabric;

/**
 * <p>
 * The type Fabric query request.
 *
 * @param <T> the type parameter
 * @author XieXiongXiong
 * @date 2021 -07-07  *
 */
public class FabricQueryRequest<T> extends FabricRequest {

    /**
     * Extract queried object with this type.
     */
    public Class<T> type;

    /**
     * Fabric query request
     *
     * @param type      type
     * @param function  function
     * @param arguments arguments
     */
    public FabricQueryRequest(Class<T> type, String function, String... arguments) {
		super(function, arguments);
		this.type = type;
	}

}
