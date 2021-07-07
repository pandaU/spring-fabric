package io.github.ecsoya.fabric.explorer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * The type Fabric explorer properties.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Configuration
@ConfigurationProperties("spring.fabric.explorer")
public class FabricExplorerProperties {

	/**
	 * Title
	 */
	private String title = "Fabric Explorer";

	/**
	 * Logo
	 */
	private String logo = "img/explorer/camel.png";

	/**
	 * Copyright
	 */
	private String copyright = "Ecsoya (jin.liu@soyatec.com)";

	/**
	 * Hyperledger explorer url
	 */
	private String hyperledgerExplorerUrl = "";

	/**
	 * Path
	 */
	private String path = "/explorer";

	/**
	 * Map
	 */
	private Map<String, Object> map;

	/**
	 * Gets title.
	 *
	 * @return the title
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title.
	 *
	 * @param title the title
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets logo.
	 *
	 * @return the logo
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * Sets logo.
	 *
	 * @param logo the logo
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * Gets copyright.
	 *
	 * @return the copyright
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * Sets copyright.
	 *
	 * @param copyright the copyright
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * To map map.
	 *
	 * @return the map
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public Map<String, Object> toMap() {
		if (map == null) {
			map = new HashMap<>(16);
			map.put("title", title);
			map.put("logo", logo);
			map.put("copyright", copyright);
			map.put("hyperledgerExplorerUrl", hyperledgerExplorerUrl);
			map.put("path", getPath());
		}
		return map;
	}

	/**
	 * Gets hyperledger explorer url.
	 *
	 * @return the hyperledger explorer url
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public String getHyperledgerExplorerUrl() {
		return hyperledgerExplorerUrl;
	}

	/**
	 * Sets hyperledger explorer url.
	 *
	 * @param hyperledgerExplorerUrl the hyperledger explorer url
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public void setHyperledgerExplorerUrl(String hyperledgerExplorerUrl) {
		this.hyperledgerExplorerUrl = hyperledgerExplorerUrl;
	}

	/**
	 * Gets path.
	 *
	 * @return the path
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public String getPath() {
		if (path == null || "".equals(path)) {
			return "";
		}
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}

	/**
	 * Sets path.
	 *
	 * @param path the path
	 * @author XieXiongXiong
	 * @date 2021 -07-07 11:38:22
	 */
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "FabricExplorerProperties [" + toMap() + "]";
	}

}
