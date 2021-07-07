package io.github.ecsoya.fabric.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.github.ecsoya.fabric.json.DefaultFabricJsonConverter;
import io.github.ecsoya.fabric.json.IFabricJsonConverter;

/**
 * <p>
 * The interface Fabric json.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@Retention(RUNTIME)
@Target({ ElementType.TYPE })
public @interface FabricJson {

    /**
     * Converter class.
     *
     * @return the class
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     */
    Class<? extends IFabricJsonConverter> converter() default DefaultFabricJsonConverter.class;

    /**
     * The type value.
     *
     * @return the string
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:20
     * @see FabricType
     */
    String type() default "";

}
