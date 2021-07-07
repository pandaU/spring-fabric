package io.github.ecsoya.fabric.gateway;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractEvent;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Transaction;
import org.hyperledger.fabric.gateway.impl.ContractImpl;
import org.hyperledger.fabric.gateway.impl.TransactionImpl;
import org.hyperledger.fabric.gateway.spi.Checkpointer;

/**
 * Overrite the default {@link Contract} to provided transactionId after
 * execution.
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
public class FabricContract implements Contract {

	/**
	 * Delegate
	 */
	private ContractImpl delegate;

	/**
	 * Orderer timeout seconds
	 *
	 * @since 1.0.6
	 */
	private long ordererTimeout = 60;

	/**
	 * Proposal timeout seconds
	 *
	 * @since 1.0.6
	 */
	private long proposalTimeout = 5;

	/**
	 * Fabric contract
	 *
	 * @param delegate delegate
	 */
	public FabricContract(ContractImpl delegate) {
		this.delegate = delegate;
	}

	@Override
	public Transaction createTransaction(String name) {
		return delegate.createTransaction(name);
	}

	/**
	 * Execute transaction string.
	 *
	 * @param name the name
	 * @param args the args
	 * @return the string
	 * @throws ContractException    the contract exception
	 * @throws TimeoutException     the timeout exception
	 * @throws InterruptedException the interrupted exception
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:18
	 */
	public String executeTransaction(String name, String... args)
			throws ContractException, TimeoutException, InterruptedException {
		TransactionImpl tx = (TransactionImpl) delegate.createTransaction(name);
		FabricTransaction fabricTransaction = new FabricTransaction(tx, delegate);
		fabricTransaction.setOrdererTimeout(getOrdererTimeout());
		fabricTransaction.setProposalTimeout(getProposalTimeout());
		return fabricTransaction.execute(args);
	}

	@Override
	public byte[] submitTransaction(String name, String... args)
			throws ContractException, TimeoutException, InterruptedException {
		return delegate.submitTransaction(name, args);
	}

	@Override
	public byte[] evaluateTransaction(String name, String... args) throws ContractException {
		return delegate.evaluateTransaction(name, args);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(Consumer<ContractEvent> listener) {
		return delegate.addContractListener(listener);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(Consumer<ContractEvent> listener, String eventName) {
		return delegate.addContractListener(listener, eventName);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(Consumer<ContractEvent> listener, Pattern eventNamePattern) {
		return delegate.addContractListener(listener, eventNamePattern);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(Checkpointer checkpointer, Consumer<ContractEvent> listener)
			throws IOException {
		return delegate.addContractListener(checkpointer, listener);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(Checkpointer checkpointer, Consumer<ContractEvent> listener,
			String eventName) throws IOException {
		return delegate.addContractListener(checkpointer, listener, eventName);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(Checkpointer checkpointer, Consumer<ContractEvent> listener,
			Pattern eventNamePattern) throws IOException {
		return delegate.addContractListener(checkpointer, listener, eventNamePattern);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(long startBlock, Consumer<ContractEvent> listener) {
		return delegate.addContractListener(startBlock, listener);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(long startBlock, Consumer<ContractEvent> listener,
			String eventName) {
		return delegate.addContractListener(startBlock, listener, eventName);
	}

	@Override
	public Consumer<ContractEvent> addContractListener(long startBlock, Consumer<ContractEvent> listener,
			Pattern eventNamePattern) {
		return delegate.addContractListener(startBlock, listener, eventNamePattern);
	}

	@Override
	public void removeContractListener(Consumer<ContractEvent> listener) {
		delegate.removeContractListener(listener);
	}

	/**
	 * Gets orderer timeout.
	 *
	 * @return the orderer timeout
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:18
	 */
	public long getOrdererTimeout() {
		return ordererTimeout;
	}

	/**
	 * Sets orderer timeout.
	 *
	 * @param ordererTimeout the orderer timeout
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:18
	 */
	public void setOrdererTimeout(long ordererTimeout) {
		this.ordererTimeout = ordererTimeout;
	}

	/**
	 * Gets proposal timeout.
	 *
	 * @return the proposal timeout
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:18
	 */
	public long getProposalTimeout() {
		return proposalTimeout;
	}

	/**
	 * Sets proposal timeout.
	 *
	 * @param proposalTimeout the proposal timeout
	 * @author XieXiongXiong
	 * @date 2021 -07-07 10:34:18
	 */
	public void setProposalTimeout(long proposalTimeout) {
		this.proposalTimeout = proposalTimeout;
	}

}
