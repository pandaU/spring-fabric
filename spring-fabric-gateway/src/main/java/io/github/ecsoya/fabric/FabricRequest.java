package io.github.ecsoya.fabric;

/**
 * <p>
 * The type Fabric request.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class FabricRequest {

    /**
     * Chaincode function name.
     */
    public String function;

    /**
     * Chaincode function arguments.
     */
    public String[] arguments;

    /**
     * Fabric request
     *
     * @param function  function
     * @param arguments arguments
     */
    public FabricRequest(String function, String... arguments) {
		this.function = function;
		this.arguments = arguments;
	}

    /**
     * Check validate.
     *
     * @throws FabricException the fabric exception
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public void checkValidate() throws FabricException {
		if (function == null || "".equals(function)) {
			throw new FabricException("The executable function name is empty.");
		}
	}
}
