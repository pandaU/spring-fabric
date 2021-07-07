package io.github.ecsoya.fabric.bean;

import java.util.Date;

import lombok.Data;

/**
 * Fabric object querying history.
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
@Data
public class FabricQueryHistory {

    /**
     * Query date.
     */
    private Date date;

    /**
     * From IP Address.
     */
    private String ip;

    /**
     * Fabric query history
     *
     * @param ip   ip
     * @param date date
     */
    public FabricQueryHistory(String ip, Date date) {
		this.ip = ip;
		this.date = date;
	}

    /**
     * Fabric query history
     *
     * @param ip ip
     */
    public FabricQueryHistory(String ip) {
		this.ip = ip;
		date = new Date();
	}
}
