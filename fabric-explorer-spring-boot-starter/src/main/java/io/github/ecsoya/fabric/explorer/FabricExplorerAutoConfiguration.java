package io.github.ecsoya.fabric.explorer;

import javax.annotation.PostConstruct;

import io.github.ecsoya.fabric.config.FabricContext;
import io.github.ecsoya.fabric.explorer.repository.dao.ChainCodeDAO;
import io.github.ecsoya.fabric.explorer.repository.dao.impl.ChainCodeDAOImpl;
import io.github.ecsoya.fabric.explorer.repository.mapper.ChainCodeMapper;
import io.github.ecsoya.fabric.explorer.service.ChainCodeService;
import io.github.ecsoya.fabric.explorer.service.impl.ChainCodeServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractResourceBasedMessageSource;

import io.github.ecsoya.fabric.boot.SpringFabricGatewayAutoConfigure;
import io.github.ecsoya.fabric.explorer.controller.FabricExplorerController;

/**
 * <p>
 * The type Fabric explorer auto configuration.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Configuration
@ImportAutoConfiguration(classes = { SpringFabricGatewayAutoConfigure.class, MessageSourceAutoConfiguration.class })
@EnableConfigurationProperties(FabricExplorerProperties.class)
@MapperScan(basePackages = {"io.github.ecsoya.fabric.explorer.repository.mapper", "com.baomidou.springboot.mapper*"})
public class FabricExplorerAutoConfiguration {

    /**
     * Logger
     */
    private Logger logger = LoggerFactory.getLogger(FabricExplorerAutoConfiguration.class);

    /**
     * Properties
     */
    @Autowired
	private FabricExplorerProperties properties;

    /**
     * Message source
     */
    @Autowired(required = false)
	private MessageSource messageSource;

    /**
     * Initialize.
     *
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:23
     */
    @PostConstruct
	private void initialize() {
		logger.info("Init FabricExplorerAutoConfiguration: " + properties);
		if (messageSource instanceof AbstractResourceBasedMessageSource) {
			((AbstractResourceBasedMessageSource) messageSource).addBasenames("static/i18n/explorer");
		}
	}

    /**
     * Fabric explorer handler mapping fabric explorer handler mapping.
     *
     * @return the fabric explorer handler mapping
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:23
     */
    @Bean
	public FabricExplorerHandlerMapping fabricExplorerHandlerMapping() {
		FabricExplorerHandlerMapping mapping = new FabricExplorerHandlerMapping();
		mapping.setOrder(Integer.MAX_VALUE - 3);
		return mapping;
	}
	@Bean
	public ChainCodeDAO chainCodeDAO(ChainCodeMapper chainCodeMapper) {
		return new ChainCodeDAOImpl(chainCodeMapper);
	}

	@Bean
	public ChainCodeService chainCodeService(FabricContext fabricContext, ChainCodeDAO chainCodeDAO) {
		return new ChainCodeServiceImpl(fabricContext, chainCodeDAO);
	}

    /**
     * Fabric explorer controller fabric explorer controller.
     *
     * @return the fabric explorer controller
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:23
     */
    @Bean
	public FabricExplorerController fabricExplorerController() {
		return new FabricExplorerController();
	}




}
