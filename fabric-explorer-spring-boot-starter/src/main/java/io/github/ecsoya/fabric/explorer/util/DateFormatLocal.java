package io.github.ecsoya.fabric.explorer.util;

import java.text.SimpleDateFormat;

/**
 * <p>
 * The type Date format local.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class DateFormatLocal {
    /**
     * FM
     */
    public static final ThreadLocal<SimpleDateFormat> FM = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
}
