package io.github.ecsoya.fabric.config;

import lombok.Data;

/**
 * <p>
 * The type Fabric chaincode function properties.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Data
public class FabricChaincodeFunctionProperties {

    /**
     * Create
     */
    private String create = "create";

    /**
     * Get
     */
    private String get = "get";

    /**
     * Delete
     */
    private String delete = "delete";

    /**
     * Update
     */
    private String update = "update";

    /**
     * Query
     */
    private String query = "query";

    /**
     * History
     */
    private String history = "history";

    /**
     * Count
     */
    private String count = "count";

    /**
     * Exists
     */
    private String exists = "exists";
}
