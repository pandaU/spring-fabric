package io.github.ecsoya.fabric;

import org.hyperledger.fabric.sdk.BlockEvent.TransactionEvent;

/**
 * <p>
 * The type Fabric response.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
public class FabricResponse {

    /**
     * SUCCESS
     */
    public static final int SUCCESS = 1;
    /**
     * FAILURE
     */
    public static final int FAILURE = -505;

    /**
     * Status
     */
    public final int status;

    /**
     * Error msg
     */
    public final String errorMsg;

    /**
     * The transaction id of execution.
     */
    private String transactionId;

    /**
     * Fabric response
     *
     * @param status   status
     * @param errorMsg error msg
     */
    public FabricResponse(int status, String errorMsg) {
		this.status = status;
		this.errorMsg = errorMsg;
	}

    /**
     * Is ok boolean.
     *
     * @return the boolean
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public boolean isOk() {
		return status == SUCCESS;
	}

    /**
     * Is ok boolean.
     *
     * @param all the all
     * @return the boolean
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public boolean isOk(boolean all) {
		return isOk();
	}

    /**
     * Sets transaction id.
     *
     * @param transactionId the transaction id
     * @return the transaction id
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public FabricResponse setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}

    /**
     * Gets transaction id.
     *
     * @return the transaction id
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public String getTransactionId() {
		return transactionId;
	}

    /**
     * Fail fabric response.
     *
     * @param errorMsg the error msg
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public static FabricResponse fail(String errorMsg) {
		return new FabricResponse(FAILURE, errorMsg);
	}

    /**
     * Ok fabric response.
     *
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public static FabricResponse ok() {
		return new FabricResponse(SUCCESS, null);
	}

    /**
     * Create fabric response.
     *
     * @param event the event
     * @return the fabric response
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:21
     */
    public static FabricResponse create(TransactionEvent event) {
		if (event == null) {
			return fail("Invalid transaction event");
		}
		FabricResponse res = new FabricResponse(SUCCESS, null);
		res.setTransactionId(event.getTransactionID());
		return res;
	}

}
