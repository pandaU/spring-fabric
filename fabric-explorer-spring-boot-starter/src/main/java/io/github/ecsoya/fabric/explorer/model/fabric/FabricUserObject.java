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
public class FabricUserObject extends FabricObject {
    /**
     * TYPE
     */
    public static final String TYPE = "user";

    /**
     * Name
     */
    private String name;

    /**
     * Age
     */
    private Integer age;

    /**
     * Fabric user object
     */
    public FabricUserObject() {
        super();
        super.setType(TYPE);
    }
}
