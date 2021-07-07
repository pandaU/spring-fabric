package io.github.ecsoya.fabric.bean;

import com.google.gson.annotations.Expose;

import io.github.ecsoya.fabric.annotation.FabricIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * <p>
 * The type Fabric identity object.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Data
public class FabricIdentityObject implements IFabricIdentityObject {

    /**
     * Id
     */
    @FabricIgnore
	private String id;

    /**
     * Identity
     */
    @FabricIgnore
	private FabricIdentity identity;

    /**
     * Type
     */
    @Expose(serialize = true, deserialize = false)
	@Setter(AccessLevel.NONE)
	private String type = getType();

}
