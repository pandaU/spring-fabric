package io.github.ecsoya.fabric.bean;

/**
 * Fabric object interface.
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
public interface IFabricObject {

    /**
     * DEFAULT_TYPE
     */
    public static final String DEFAULT_TYPE = IFabricObject.class.getName();

    /**
     * The type of the object, the default is the class type.
     *
     * @return the type
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:14
     */
    default String getType() {
		return DEFAULT_TYPE;
	}

    /**
     * Get the id of current object.
     *
     * @return the id
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:14
     */
    String getId();

    /**
     * Set new id for current object.
     *
     * @param id the id
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:14
     */
    void setId(String id);

}
