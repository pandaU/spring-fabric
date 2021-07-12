package io.github.ecsoya.fabric.config;

import lombok.Data;

/**
 * <p>
 * The type Channel config tx gen properties.
 *
 * @author XieXiongXiong
 * @date 2021 -07-12
 */
@Data
public class ChannelConfigTxGenProperties {
    /**
     * Tx path prefix
     */
    private  String txPathPrefix = "/usr/local/src/channelConfig/";

    /**
     * Tx path suffix
     */
    private  String txPathSuffix = ".tx";

    /**
     * Base 64 prefix
     */
    private  String base64Prefix = "/usr/local/src/channelConfig/base64/";

    /**
     * Base 64 suffix
     */
    private  String base64Suffix = ".txt";

    /**
     * Http config tx lator
     */
    private  String httpConfigTxLator = "http://localhost:7059";

    /**
     * Block prefix
     */
    private  String blockPrefix = "/usr/local/src/channelConfig/block/";

    /**
     * Blocksuffix
     */
    private  String blocksuffix = ".block";

    /**
     * Config tx gen
     */
    private  String configTxGen = "/usr/local/src/xxzx-fabric/bin/configtxgen";

    /**
     * Config tx path
     */
    private  String configTxPath = "/usr/local/src/xxzx-fabric/xxzx-singleton-network/configtx";

}
