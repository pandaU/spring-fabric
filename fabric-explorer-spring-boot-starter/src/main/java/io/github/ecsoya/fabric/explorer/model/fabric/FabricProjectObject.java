package io.github.ecsoya.fabric.explorer.model.fabric;

import io.github.ecsoya.fabric.bean.FabricObject;
import lombok.Data;

/**
 * <p>
 * The type Fabric project object.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Data
public class FabricProjectObject extends FabricObject {
    private String month;

    private Double arableLand;

    private String projectName;
    /**
     * area 地区
     */
    private String area;

    private String createTime;

    private String updateTime;
}
