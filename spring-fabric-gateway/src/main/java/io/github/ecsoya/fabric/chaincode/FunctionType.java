package io.github.ecsoya.fabric.chaincode;

/**
 * Default chaincode function names.
 * <p>
 * Default chaincode path: src/chaincode/common/chaincode.go
 * <p>
 * <p>
 * {@link https://ecsoya.github.io/fabric/pages/gateway.html#%E9%93%BE%E7%A0%81%E5%87%BD%E6%95%B0%E5%8F%8A%E5%8F%82%E6%95%B0%E8%AF%B4%E6%98%8E}
 *
 * @author ecsoya * @date 2021-07-07
 */
public enum FunctionType {

    /**
     * Function for create.
     */
    FUNCTION_CREATE("create"),

    /**
     * Single element query by id and type.
     */
    FUNCTION_GET("get"),

    /**
     * Delete function with id and type.
     */
    FUNCTION_DELETE("delete"),

    /**
     * Update function.
     */
    FUNCTION_UPDATE("update"),

    /**
     * Query(Page query) function name.
     */
    FUNCTION_QUERY("query"),

    /**
     * Query all (limit 100 records by fabric chaincodes defaultly.)
     */
    FUNCTION_LIST("list"),

    /**
     * Query history of element function.
     */
    FUNCTION_HISTORY("history"),

    /**
     * Query count of element function
     */
    FUNCTION_COUNT("count"),

    /**
     * Check if exists of element function
     */
    FUNCTION_EXISTS("exists");

    /**
     * Function name
     */
    private final String functionName;

    /**
     * Function type
     *
     * @param functionName function name
     */
    private FunctionType(String functionName) {
		this.functionName = functionName;
	}

    /**
     * Gets function name.
     *
     * @return the function name
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    public String getFunctionName() {
		return functionName;
	}

}
