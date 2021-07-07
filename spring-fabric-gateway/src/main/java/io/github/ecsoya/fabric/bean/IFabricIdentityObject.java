package io.github.ecsoya.fabric.bean;

/**
 * <p>
 * The interface Fabric identity object.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public interface IFabricIdentityObject extends IFabricObject {

    /**
     * Gets identity.
     *
     * @return the identity
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:14
     */
    FabricIdentity getIdentity();

    /**
     * Sets identity.
     *
     * @param identity the identity
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:14
     */
    void setIdentity(FabricIdentity identity);
}
