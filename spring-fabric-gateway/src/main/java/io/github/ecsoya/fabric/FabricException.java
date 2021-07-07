package io.github.ecsoya.fabric;

/**
 * <p>
 * The type Fabric exception.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class FabricException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -6755525873084547174L;

    /**
     * Fabric exception
     */
    public FabricException() {
	}

    /**
     * Fabric exception
     *
     * @param message message
     */
    public FabricException(String message) {
		super(message);
	}

    /**
     * Fabric exception
     *
     * @param cause cause
     */
    public FabricException(Throwable cause) {
		super(cause);
	}

    /**
     * Fabric exception
     *
     * @param message message
     * @param cause   cause
     */
    public FabricException(String message, Throwable cause) {
		super(message, cause);
	}

    /**
     * Fabric exception
     *
     * @param message            message
     * @param cause              cause
     * @param enableSuppression  enable suppression
     * @param writableStackTrace writable stack trace
     */
    public FabricException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
