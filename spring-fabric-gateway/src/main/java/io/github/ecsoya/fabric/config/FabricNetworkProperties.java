package io.github.ecsoya.fabric.config;

import org.hyperledger.fabric.sdk.NetworkConfig;

import lombok.Data;

/**
 * Fabric network configurations.
 *
 * @author ecsoya
 * @date 2021 -07-07
 * @see NetworkConfig
 */
@Data
public class FabricNetworkProperties {

    /**
     * The name of the network.
     */
    private String name;

    /**
     * The network config file, required.
     *
     * @see NetworkConfig
     */
    private String file;

}
