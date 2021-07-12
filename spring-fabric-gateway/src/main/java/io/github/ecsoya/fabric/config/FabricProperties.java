package io.github.ecsoya.fabric.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import lombok.Data;

/**
 * The root configuration of the fabric and network.
 *
 * @author ecsoya
 * @date 2021 -07-07
 * @see FabricContext
 */
@Data
public class FabricProperties {

    /**
     * The channel name of fabric, required.
     */
    private String channel;

    /**
     * The organizations of fabric.
     */
    private String[] organizations;

    /**
     * The name of the fabric.
     */
    private String name;

    /**
     * The count of all peers.
     */
    private int peers;


    /**
     * The gateway configuration of fabric.
     */
    private FabricGatewayProperties gateway = new FabricGatewayProperties();

    /**
     * The network configuration of fabric.
     */
    protected FabricNetworkProperties network = new FabricNetworkProperties();

    /**
     * The chaincode configuration of fabric.
     */
    private FabricChaincodeProperties chaincode = new FabricChaincodeProperties();


	private ChannelConfigTxGenProperties channelConfig = new ChannelConfigTxGenProperties();

    /**
     * Gets network contents.
     *
     * @return Load the contents of the network.
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:18
     */
    public InputStream getNetworkContents() {
		if (network == null) {
			return null;
		}
		String file = network.getFile();
		if (file == null || "".equals(file)) {
			return null;
		}
		File localFile = new File(file);
		if (!localFile.exists()) {
			return null;
		}
		try {
			return new FileInputStream(localFile);
		} catch (FileNotFoundException e) {
			return null;
		}
	}

}
