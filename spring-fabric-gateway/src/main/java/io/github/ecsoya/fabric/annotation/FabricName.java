package io.github.ecsoya.fabric.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * The interface Fabric name.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface FabricName {

    /**
     * Value string.
     *
     * @return the string
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    String value();
}
