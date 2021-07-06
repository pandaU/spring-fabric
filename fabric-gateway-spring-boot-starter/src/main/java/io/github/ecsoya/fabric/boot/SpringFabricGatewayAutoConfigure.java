package io.github.ecsoya.fabric.boot;

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

@Configuration
@EnableConfigurationProperties(SpringFabricProperties.class)
public class SpringFabricGatewayAutoConfigure {

	private Logger logger = LoggerFactory.getLogger(SpringFabricGatewayAutoConfigure.class);

	@Autowired
	private SpringFabricProperties properties;

	@Bean
	@Primary
	public FabricContext fabricContext() {
		logger.info("Init SpringFabricGateway: " + properties);
		return new FabricContext(properties);
	}

	@Bean
	public IFabricObjectService fabricService(FabricContext fabricContext) {
		return new FabricObjectServiceImpl(fabricContext);
	}

	@Bean
	public IFabricInfoService fabricInfoService(FabricContext fabricContext) {
		return new FabricInfoServiceImpl(fabricContext);
	}
}
