package io.github.ecsoya.fabric.explorer.model.fabric;

import io.github.ecsoya.fabric.bean.FabricObject;
import lombok.Data;


/**
 * <p>
 * The type Fabric user object.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Data
public class FabricLandObject extends FabricObject {
    /**
     * TYPE
     */
    public static final String TYPE = "monthlyStatisticsLand";

    /**
     * month 月度
     */
    private String month;
    /**
     * arableLand 当前面积
     */
    private Double arableLand;
    /**
     * difference 变化量
     */
    private Double difference;
    /**
     * initArableLand 初始面积
     */
    private Double initArableLand;
    /**
     * area 地区
     */
    private String area;
    /**
     * agriculturalChangeScale 农专面积
     */
    private Double agriculturalChangeScale;
    /**
     * lawEnforcementDeduct 依法扣除
     */
    private Double lawEnforcementDeduct;
    /**
     * disasters 灾害
     */
    private Double disasters;
    /**
     * subTotal 减少总量
     */
    private Double subTotal;
    /**
     * down 倒挂
     */
    private Double down;
    /**
     * batchBack 撤批腾退
     */
    private Double batchBack;
    /**
     * addTotal 新增总量
     */
    private Double addTotal;

    private String lastProjectType;

    private String lastProjectId;

    private String lastProjectName;

    private Double lastProjectArable;

    private String createTime;

    private String updateTime;
    /**
     * Fabric user object
     */
    public FabricLandObject() {
        super();
        super.setType(TYPE);
    }
}
