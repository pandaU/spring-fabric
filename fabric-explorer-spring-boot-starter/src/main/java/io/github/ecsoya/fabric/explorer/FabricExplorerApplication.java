package io.github.ecsoya.fabric.explorer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 * The type Fabric explorer application.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@SpringBootApplication
@ComponentScan(basePackages = "io.github.ecsoya.fabric.explorer.*")
public class FabricExplorerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FabricExplorerApplication.class);
	}

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:22
     */
    public static void main(String[] args) {
		SpringApplication.run(FabricExplorerApplication.class, args);
	}

}
