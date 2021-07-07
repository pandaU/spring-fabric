package io.github.ecsoya.fabric.explorer.model.fabric;

import io.github.ecsoya.fabric.bean.FabricObject;
import lombok.Data;

/**
 * <p>
 * The type User fabric request.
 *
 * @author XieXiongXiong
 * @date 2021 -07-06
 */
@Data
public class FabricUserObject extends FabricObject {
    /**
     * TYPE
     */
    public static final String TYPE = "user";

    private String name;

    private Integer age;

    public FabricUserObject() {
        super();
        super.setType(TYPE);
    }
}
