package io.github.ecsoya.fabric.service.impl;

import io.github.ecsoya.fabric.config.ChannelConfigTxGenProperties;
import io.github.ecsoya.fabric.service.ChannelService;
import io.github.ecsoya.fabric.utils.FileUtils;
import io.github.ecsoya.fabric.utils.HfClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.lang.String.format;
import static org.hyperledger.fabric.sdk.Channel.PeerOptions.createPeerOptions;

/**
 * <p>
 * The type Channel service.
 *
 * @author XieXiongXiong
 * @date 2021 -07-12
 */
@Slf4j
public class ChannelServiceImpl implements ChannelService {
    private final static Integer SUCCESS = 200;

    private ChannelConfigTxGenProperties config;

    public ChannelServiceImpl(ChannelConfigTxGenProperties config) {
        this.config = config;
    }

    @Override
    public Channel createChannel(String channelName) throws IOException, InvalidArgumentException, TransactionException {
        Collection<Orderer> orderers = HfClientUtil.getOrderers();
        Orderer orderer = HfClientUtil.CLIENT.newOrderer(orderers.iterator().next().getName(), orderers.iterator().next().getUrl(), orderers.iterator().next().getProperties());
        String txPath = Thread.currentThread().getContextClassLoader().getResource(getTxPath(channelName)).getPath().substring(1);
        ChannelConfiguration configuration = new ChannelConfiguration(new File(txPath));
        //3. ????????????
        byte[] signData = HfClientUtil.CLIENT.getChannelConfigurationSignature(configuration, HfClientUtil.CLIENT.getUserContext());
        Channel channel = HfClientUtil.CLIENT.newChannel(channelName, orderer, configuration, signData);
        Collection<Peer> peers = HfClientUtil.getPeers();
        Channel newChannel = channel.initialize();
        newChannel.serializeChannel(new File(config.getBlockPrefix() + channelName + config.getBlocksuffix()));
        String channel64String = Base64.getEncoder().encodeToString(newChannel.serializeChannel());
        FileUtils.writeFile(channel64String, config.getBase64Prefix() + channelName + config.getBase64Suffix(), false);
        peers.forEach(peer -> {
            try {
                Peer newPeer = HfClientUtil.CLIENT.newPeer(peer.getName(), peer.getUrl(), peer.getProperties());
                newChannel.joinPeer(newPeer);
            } catch (Exception e) {
                log.error("peer???{} ??????????????????", peer.getName(), e);
            }
        });
        return newChannel;
    }

    @Override
    public Channel setAnchor(String channelName, Collection<Peer> peers, Collection<Orderer> orderers, String addAnchor, String removeAnchor) throws Exception {
        Channel channel = reconstructChannel(false, channelName, peers, orderers);

        // Get config update for adding an anchor peer.
        Channel.AnchorPeersConfigUpdateResult configUpdateAnchorPeers = channel.getConfigUpdateAnchorPeers(channel.getPeers().iterator().next(), HfClientUtil.CLIENT.getUserContext(),
                Arrays.asList(addAnchor), null);

        if (configUpdateAnchorPeers.getUpdateChannelConfiguration() != null){
            channel.updateChannelConfiguration(configUpdateAnchorPeers.getUpdateChannelConfiguration(),
                    HfClientUtil.CLIENT.getUpdateChannelConfigurationSignature(configUpdateAnchorPeers.getUpdateChannelConfiguration(), HfClientUtil.CLIENT.getUserContext()));
        }

        if (removeAnchor != null && !removeAnchor.isEmpty()){
            configUpdateAnchorPeers = channel.getConfigUpdateAnchorPeers(channel.getPeers().iterator().next(), HfClientUtil.CLIENT.getUserContext(),
                    null, Arrays.asList(removeAnchor));

            if (configUpdateAnchorPeers.getUpdateChannelConfiguration() != null){
                channel.updateChannelConfiguration(configUpdateAnchorPeers.getUpdateChannelConfiguration(),
                        HfClientUtil.CLIENT.getUpdateChannelConfigurationSignature(configUpdateAnchorPeers.getUpdateChannelConfiguration(), HfClientUtil.CLIENT.getUserContext()));
            }
        }
        return channel;
    }

    @Override
    public String createChannelTx(String channelName) {
        Runtime runtime = Runtime.getRuntime();
        String cmd = config.getConfigTxGen() + " -configPath "+config.getConfigTxPath()+" -profile TwoOrgsChannel -outputCreateChannelTx " + config.getTxPathPrefix() + channelName+config.getTxPathSuffix() +" -channelID " + channelName;
        StringBuffer sb = new StringBuffer();
        try {
            Process process = runtime.exec(cmd);
            InputStream in = process.getErrorStream();
            byte[] bytes = new byte[1024];
            int index = 0;
            while ((index = in.read(bytes)) != -1){
                sb.append(new String(bytes, "utf8"));
            }
        } catch (IOException e) {
            log.error("?????? cmd???{} ??????", cmd, e);
        }
        return sb.toString();
    }

    private String getTxPath(String channelName){
        return config.getTxPathPrefix() + channelName + config.getTxPathSuffix();
    }

    private Channel reconstructChannel(final boolean isSystemChannel, String name, Collection<Peer> peers , Collection<Orderer> orderers) throws Exception {

        Channel newChannel = HfClientUtil.CLIENT.newChannel(name);

        orderers.forEach(orderer -> {
            try {
                newChannel.addOrderer(HfClientUtil.CLIENT.newOrderer(orderer.getName(),orderer.getUrl(),orderer.getProperties()));
            } catch (InvalidArgumentException e) {
               log.error("??????orderer??????????????????");
            }
        });

        if (isSystemChannel) {
            newChannel.initialize();
            return newChannel;
        }
        for (Peer peer: peers) {
            Set<String> channels = HfClientUtil.CLIENT.queryChannels(peer);
            if (!channels.contains(name)) {
                throw new AssertionError(format("Peer %s does not appear to belong to channel %s", peer.getName(), name));
            }
            Channel.PeerOptions peerOptions = createPeerOptions().setPeerRoles(EnumSet.of(Peer.PeerRole.CHAINCODE_QUERY,
                    Peer.PeerRole.ENDORSING_PEER, Peer.PeerRole.LEDGER_QUERY, Peer.PeerRole.EVENT_SOURCE));

            peer =HfClientUtil.CLIENT.newPeer(peer.getName(),peer.getUrl(),peer.getProperties());
            newChannel.addPeer(peer, peerOptions);
        }
        Channel channel = newChannel.initialize();
        return channel;
    }

    private String configTxlatorDecode(HttpClient httpclient, byte[] channelConfigurationBytes) throws IOException {
        HttpPost httppost = new HttpPost(config.getHttpConfigTxLator() + "/protolator/decode/common.Config");
        httppost.setEntity(new ByteArrayEntity(channelConfigurationBytes));

        HttpResponse response = httpclient.execute(httppost);
        int statuscode = response.getStatusLine().getStatusCode();
        if (statuscode != SUCCESS){
            log.error("configTxlatorDecode http????????????");
            return null;
        }
        return EntityUtils.toString(response.getEntity());
    }

    private byte[] configTxLatorEncode(HttpClient httpclient, String jsonEncoded) throws IOException {
        HttpPost httppost = new HttpPost(config.getHttpConfigTxLator() + "/protolator/encode/common.Config");
        httppost.setEntity(new StringEntity(jsonEncoded));

        HttpResponse response = httpclient.execute(httppost);

        int statuscode = response.getStatusLine().getStatusCode();
        if (statuscode == SUCCESS){
            return EntityUtils.toByteArray(response.getEntity());
        }
        log.error("configTxLatorEncode http????????????");
        return null;
    }

    private byte[] getChannelUpdateBytes(Channel fooChannel, byte[] reEncodedOriginalConfig, byte[] updatedConfigBytes) throws IOException {
        HttpPost httppost;
        HttpResponse response;

        httppost = new HttpPost(config.getHttpConfigTxLator() + "/configtxlator/compute/update-from-configs");

        HttpEntity multipartEntity = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("original", reEncodedOriginalConfig, ContentType.APPLICATION_OCTET_STREAM, "originalFakeFilename")
                .addBinaryBody("updated", updatedConfigBytes, ContentType.APPLICATION_OCTET_STREAM, "updatedFakeFilename")
                .addBinaryBody("channel", fooChannel.getName().getBytes()).build();

        httppost.setEntity(multipartEntity);

        response = HttpClients.createDefault().execute(httppost);
        int statuscode = response.getStatusLine().getStatusCode();
        if (statuscode != SUCCESS){
            log.error("getChannelUpdateBytes http????????????");
            return null;
        }
        return EntityUtils.toByteArray(response.getEntity());
    }
}
