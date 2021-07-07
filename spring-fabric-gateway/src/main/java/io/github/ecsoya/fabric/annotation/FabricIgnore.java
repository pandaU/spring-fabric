package io.github.ecsoya.fabric.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Ignore to save fabric properties.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface FabricIgnore {

    /**
     * Ignore to write, default is true
     *
     * @return boolean boolean
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public boolean serialize() default true;

    /**
     * Ignore to read, default is true.
     *
     * @return boolean boolean
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public boolean deserialize() default true;
}
