package io.github.ecsoya.fabric.bean;

import lombok.Data;

/**
 * <p>
 * The type Fabric identity.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Data
public class FabricIdentity {

    /**
     * Block number
     */
    private long blockNumber;

    /**
     * Data hash
     */
    private String dataHash;
}
