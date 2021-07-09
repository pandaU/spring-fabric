package application.java.channel;

import application.java.AppGateway;
import application.java.ClientUtil;
import application.java.EnrollorderAdmin;
import application.java.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.hyperledger.fabric.sdk.Channel.PeerOptions.createPeerOptions;

/**
 * <p>
 * The type Create channel and set ac.
 *
 * @author XieXiongXiong
 * @date 2021 -07-09
 */
public class CreateChannelAndSetAc {
    public static final String HTTP_CONFIGTXLATOR = "http://192.168.17.132:7059";
    // Batch time out in configtx.yaml
    private static final String ORIGINAL_BATCH_TIMEOUT = "\"timeout\": \"2s\"";
    // What we want to change it to.
    private static final String UPDATED_BATCH_TIMEOUT = "\"timeout\": \"5s\"";


    private static final String SYSTEM_CHANNEL_NAME = "systemordererchannel";

    private static final String PEER_0_ORG_1_EXAMPLE_COM_7051 = "peer0.org1.xxzx.com:7051";

    private static final String REGX_S_HOST_PEER_0_ORG_1_EXAMPLE_COM = "(?s).*\"host\":[ \t]*\"peer0\\.org1\\.xxzx\\.com\".*";

    private static final String REGX_S_ANCHOR_PEERS = "(?s).*\"*AnchorPeers\":[ \t]*\\{.*";


    private static final String REGX_IS_SYSTEM_CHANNEL = "(?s).*\"Consortiums\":[ \\t\\s]*\\{[ \\s\\t]*\"groups\":[ \\t\\s]*\\{[ \\t\\s]*\"SampleConsortium\":[ \\t\\s]*\\{.*";

    public static final String PRE_PATH = "C:\\Users\\MSI\\Desktop\\fabric\\fabric-samples\\asset-transfer-basic\\application-java\\src\\main\\resources\\channel\\";


    public String listenerHandler1;

    public String listenerHandler2;

    public BlockingQueue<QueuedBlockEvent> blockingQueue1 = new LinkedBlockingQueue<>();

    public BlockingQueue<QueuedBlockEvent> blockingQueue2 = new ArrayBlockingQueue<>(1000);


    public BlockingQueue<BlockEvent> eventQueueCaputure = new LinkedBlockingQueue<>();


    int eventCountFilteredBlock = 0;

    int eventCountBlock = 0;

    public static void main(String[] args) {

    }

    public static Channel newChannel(String channelName,String baseChannel) throws Exception {
        HFClient client = ClientUtil.CLIENT;
        Gateway gateway = AppGateway.connect();
        Channel xxzx = gateway.getNetwork(baseChannel).getChannel();
        Collection<Orderer> orderers =  xxzx.getOrderers();
        Orderer orderer = client.newOrderer(orderers.iterator().next().getName(), orderers.iterator().next().getUrl(), orderers.iterator().next().getProperties());
        ChannelConfiguration configuration = new ChannelConfiguration(new File(PRE_PATH + channelName + ".tx"));
        //3. 获取签名
        byte[] signData = client.getChannelConfigurationSignature(configuration, client.getUserContext());
        Channel channel = client.newChannel(channelName, orderer, configuration, signData);
        /*orderers.forEach(order->{
            try {
                Orderer newOrderer = client.newOrderer(order.getName(), order.getUrl(), order.getProperties());
                channel.addOrderer(newOrderer);
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        });*/
        Collection<Peer> peers = xxzx.getPeers();
        Channel newChannel = channel.initialize();
        newChannel.serializeChannel(new File(PRE_PATH + channelName + ".block"));
        String channel64String = Base64.getEncoder().encodeToString(newChannel.serializeChannel());
        FileUtils.writeFile(channel64String, PRE_PATH + "base64" + channelName + ".txt", false);
        peers.forEach(peer -> {
            try {
                Peer newPeer = client.newPeer(peer.getName(), peer.getUrl(), peer.getProperties());
                newChannel.joinPeer(newPeer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return newChannel;
    }

    public  void setAnc(HFClient client,String channelName, Collection<Peer> peers , Collection<Orderer> orderers) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Channel channel = reconstructChannel(false, channelName, client, peers, orderers);

        byte[] channelConfigurationBytes = channel.getChannelConfigurationBytes();

        String originalConfigJson = configTxlatorDecode(httpClient, channelConfigurationBytes);

        // Get config update for adding an anchor peer.
        Channel.AnchorPeersConfigUpdateResult configUpdateAnchorPeers = channel.getConfigUpdateAnchorPeers(channel.getPeers().iterator().next(), client.getUserContext(),
                Arrays.asList(PEER_0_ORG_1_EXAMPLE_COM_7051), null);


        //Now add anchor peer to channel configuration.
        channel.updateChannelConfiguration(configUpdateAnchorPeers.getUpdateChannelConfiguration(),
                client.getUpdateChannelConfigurationSignature(configUpdateAnchorPeers.getUpdateChannelConfiguration(), client.getUserContext()));
        Thread.sleep(3000);

        // Getting foo channels current configuration bytes to check with configtxlator
        channelConfigurationBytes = channel.getChannelConfigurationBytes();
        originalConfigJson = configTxlatorDecode(httpClient, channelConfigurationBytes);

        //Now remove the anchor peer -- get the config update block.
        configUpdateAnchorPeers = channel.getConfigUpdateAnchorPeers(channel.getPeers().iterator().next(), client.getUserContext(),
                null, Arrays.asList("peer0.org1.xxzx.com:8051"));


        // Now do the actual update.
        channel.updateChannelConfiguration(configUpdateAnchorPeers.getUpdateChannelConfiguration(),
                client.getUpdateChannelConfigurationSignature(configUpdateAnchorPeers.getUpdateChannelConfiguration(), client.getUserContext()));
        Thread.sleep(3000); // give time for events to happen
        // Getting foo channels current configuration bytes to check with configtxlator.
        channelConfigurationBytes = channel.getChannelConfigurationBytes(client.getUserContext(), channel.getPeers().iterator().next());
        originalConfigJson = configTxlatorDecode(httpClient, channelConfigurationBytes);

        configUpdateAnchorPeers = channel.getConfigUpdateAnchorPeers(channel.getPeers().iterator().next(), client.getUserContext(),
                null, null);

        Collection<QueuedBlockEvent> drain1 = new ArrayList<>();
        blockingQueue1.drainTo(drain1);
        Collection<? super QueuedBlockEvent> drain2 = new ArrayList<>();
        blockingQueue2.drainTo(drain2);

        Collection<? super BlockEvent> eventQDrain = new ArrayList<>();
        eventQueueCaputure.drainTo(eventQDrain);

        QueuedBlockEvent[] drain1Array = drain1.toArray(new QueuedBlockEvent[drain1.size()]);
        QueuedBlockEvent[] drain2Array = drain2.toArray(new QueuedBlockEvent[drain2.size()]);
        BlockEvent[] drainEventQArray = eventQDrain.toArray(new BlockEvent[eventQDrain.size()]);

        for (int i = drain1Array.length - 1; i > -1; --i) {
            final long blockNumber = drain1Array[i].getBlockEvent().getBlockNumber();
            final String url = drain1Array[i].getBlockEvent().getPeer().getUrl();
        }
    }

    private Channel reconstructChannel(final boolean isSystemChannel, String name, HFClient client, Collection<Peer> peers , Collection<Orderer> orderers) throws Exception {

        Channel newChannel = client.newChannel(name);

        orderers.forEach(orderer -> {
            try {
                newChannel.addOrderer(client.newOrderer(orderer.getName(),orderer.getUrl(),orderer.getProperties()));
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        });

        if (isSystemChannel) {
            newChannel.initialize();
            return newChannel;
        }
        int i = 0;
        for (Peer peer: peers) {
            Set<String> channels = client.queryChannels(peer);
            if (!channels.contains(name)) {
                throw new AssertionError(format("Peer %s does not appear to belong to channel %s", peer.getName(), name));
            }
            Channel.PeerOptions peerOptions = createPeerOptions().setPeerRoles(EnumSet.of(Peer.PeerRole.CHAINCODE_QUERY,
                    Peer.PeerRole.ENDORSING_PEER, Peer.PeerRole.LEDGER_QUERY, Peer.PeerRole.EVENT_SOURCE));

            if (i % 2 == 0) {
                peerOptions.registerEventsForFilteredBlocks();
            } else {
                peerOptions.registerEventsForBlocks();
            }
            ++i;
            peer =client.newPeer(peer.getName(),peer.getUrl(),peer.getProperties());
            newChannel.addPeer(peer, peerOptions);
        }
        newChannel.initialize();

        return newChannel;
    }

    private String configTxlatorDecode(HttpClient httpclient, byte[] channelConfigurationBytes) throws IOException {
        HttpPost httppost = new HttpPost(HTTP_CONFIGTXLATOR + "/protolator/decode/common.Config");
        httppost.setEntity(new ByteArrayEntity(channelConfigurationBytes));

        HttpResponse response = httpclient.execute(httppost);
        int statuscode = response.getStatusLine().getStatusCode();
        //  out("Got %s status for decoding current channel config bytes", statuscode);
        if (statuscode != 200){
            throw new RuntimeException("http远程失败");
        }
        return EntityUtils.toString(response.getEntity());
    }

    private byte[] configTxLatorEncode(HttpClient httpclient, String jsonEncoded) throws IOException {
        HttpPost httppost = new HttpPost(HTTP_CONFIGTXLATOR + "/protolator/encode/common.Config");
        httppost.setEntity(new StringEntity(jsonEncoded));

        HttpResponse response = httpclient.execute(httppost);

        int statuscode = response.getStatusLine().getStatusCode();
        if (statuscode == 200 ){
            return EntityUtils.toByteArray(response.getEntity());
        }
        throw  new RuntimeException();
    }

    private byte[] getChannelUpdateBytes(Channel fooChannel, byte[] reEncodedOriginalConfig, byte[] updatedConfigBytes) throws IOException {
        HttpPost httppost;
        HttpResponse response;

        // Now send to configtxlator multipart form post with original config bytes, updated config bytes and channel name.
        httppost = new HttpPost(HTTP_CONFIGTXLATOR + "/configtxlator/compute/update-from-configs");

        HttpEntity multipartEntity = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addBinaryBody("original", reEncodedOriginalConfig, ContentType.APPLICATION_OCTET_STREAM, "originalFakeFilename")
                .addBinaryBody("updated", updatedConfigBytes, ContentType.APPLICATION_OCTET_STREAM, "updatedFakeFilename")
                .addBinaryBody("channel", fooChannel.getName().getBytes()).build();

        httppost.setEntity(multipartEntity);

        response = HttpClients.createDefault().execute(httppost);
        int statuscode = response.getStatusLine().getStatusCode();
        if (statuscode != 200){
            throw  new  RuntimeException();
        }
        return EntityUtils.toByteArray(response.getEntity());
    }
}
