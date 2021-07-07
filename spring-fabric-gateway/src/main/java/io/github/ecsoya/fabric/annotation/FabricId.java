package io.github.ecsoya.fabric.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * The interface Fabric id.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Retention(RUNTIME)
@Target({ FIELD, ElementType.METHOD })
public @interface FabricId {

}
