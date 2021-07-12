package io.github.ecsoya.fabric.service;

import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.util.Collection;

/**
 * <p>
 * The interface Channel service.
 *
 * @author XieXiongXiong
 * @date 2021 -07-12
 */
public interface ChannelService {
    /**
     * Create channel channel.
     *
     * @param ChannelName the channel name
     * @return the channel
     * @author XieXiongXiong
     * @date 2021 -07-12 11:13:47
     */
    Channel createChannel(String ChannelName) throws IOException, InvalidArgumentException, TransactionException;

    /**
     * Sets anchor.
     *
     * @param channelName the channel name
     * @param peers       the peers
     * @param orderers    the orderers
     * @return the anchor
     * @author XieXiongXiong
     * @date 2021 -07-12 11:14:25
     */
    Channel setAnchor(String channelName, Collection<Peer> peers , Collection<Orderer> orderers, String addAnchor, String removeAnchor) throws Exception;


    /**
     * Create channel tx.
     *
     * @author XieXiongXiong
     * @date 2021 -07-12 11:15:22
     */
    String createChannelTx(String channelName);
}
