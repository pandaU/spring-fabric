package io.github.ecsoya.fabric.config;

import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.impl.GatewayImpl;

import lombok.Data;

/**
 * The configuration properties of the {@link Gateway}.
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
@Data
public class FabricGatewayProperties {

    /**
     * The timeout of committing to fabric, the unit is minutes.
     * <p>
     * The default value of fabric gateway is 5 minutes, here I increased it to 10
     * minutes.
     *
     * @see GatewayImpl
     */
    private long commitTimeout = 10;

    /**
     * Orderer timeout seconds
     *
     * @since 1.0.6
     */
    private long ordererTimeout = 60;

    /**
     * Proposal timeout seconds
     *
     * @since 1.0.6
     */
    private long proposalTimeout = 5;

    /**
     * Discovery of gateway builder.
     *
     * @see GatewayImpl
     */
    private boolean discovery = false;

    /**
     * The commitHandler class name
     *
     * @see GatewayImpl
     */
    private String commitHandler;

    /**
     * The queryHandler class name.
     *
     * @see GatewayImpl
     */
    private String queryHandler;

    /**
     * The wallet configuration of gateway.
     *
     * @see GatewayImpl
     */
    private FabricWalletProperties wallet = new FabricWalletProperties();

}
