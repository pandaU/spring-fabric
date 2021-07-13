package io.github.ecsoya.fabric.boot;

import io.github.ecsoya.fabric.service.ChainCodeService;
import io.github.ecsoya.fabric.service.ChannelService;
import io.github.ecsoya.fabric.service.impl.ChainCodeServiceImpl;
import io.github.ecsoya.fabric.service.impl.ChannelServiceImpl;
import io.github.ecsoya.fabric.utils.HfClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.github.ecsoya.fabric.config.FabricContext;
import io.github.ecsoya.fabric.service.IFabricInfoService;
import io.github.ecsoya.fabric.service.IFabricObjectService;
import io.github.ecsoya.fabric.service.impl.FabricInfoServiceImpl;
import io.github.ecsoya.fabric.service.impl.FabricObjectServiceImpl;

/**
 * <p>
 * The type Spring fabric gateway auto configure.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Configuration
@EnableConfigurationProperties(SpringFabricProperties.class)
public class SpringFabricGatewayAutoConfigure {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(SpringFabricGatewayAutoConfigure.class);

	/**
	 * Properties
	 */
	@Autowired
	private SpringFabricProperties properties;

	/**
	 * Fabric context fabric context.
	 *
	 * @return the fabric context
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:48
	 */
	@Bean
	@Primary
	public FabricContext fabricContext() {
		logger.info("Init SpringFabricGateway: " + properties);
		return new FabricContext(properties);
	}

	/**
	 * Fabric service fabric object service.
	 *
	 * @param fabricContext the fabric context
	 * @return the fabric object service
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:48
	 */
	@Bean
	public IFabricObjectService fabricService(FabricContext fabricContext) {
		return new FabricObjectServiceImpl(fabricContext);
	}

	/**
	 * Fabric info service fabric info service.
	 *
	 * @param fabricContext the fabric context
	 * @return the fabric info service
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:48
	 */
	@Bean
	public IFabricInfoService fabricInfoService(FabricContext fabricContext) {
		return new FabricInfoServiceImpl(fabricContext);
	}

	/**
	 * Chain code service chain code service.
	 *
	 * @param fabricContext the fabric context
	 * @return the chain code service
	 * @author XieXiongXiong
	 * @date 2021 -07-13 14:35:03
	 */
	@Bean
	public ChainCodeService chainCodeService(FabricContext fabricContext) {
		return new ChainCodeServiceImpl(fabricContext);
	}

	/**
	 * Hf client util hf client util.
	 *
	 * @param fabricContext the fabric context
	 * @return the hf client util
	 * @author XieXiongXiong
	 * @date 2021 -07-13 14:49:35
	 */
	@Bean
	public HfClientUtil hfClientUtil(FabricContext fabricContext){
		return new HfClientUtil(fabricContext);
	}

	/**
	 * Channel service channel service.
	 *
	 * @param fabricContext the fabric context
	 * @return the channel service
	 * @author XieXiongXiong
	 * @date 2021 -07-13 14:49:35
	 */
	@Bean
	public ChannelService channelService(FabricContext fabricContext){
		return new ChannelServiceImpl(fabricContext.getProperties().getChannelConfig());
	}
}
