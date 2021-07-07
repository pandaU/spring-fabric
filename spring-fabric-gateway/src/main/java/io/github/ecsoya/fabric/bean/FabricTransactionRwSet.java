package io.github.ecsoya.fabric.bean;

import java.util.ArrayList;
import java.util.List;

import org.hyperledger.fabric.protos.ledger.rwset.kvrwset.KvRwset.KVRead;
import org.hyperledger.fabric.protos.ledger.rwset.kvrwset.KvRwset.KVWrite;

import lombok.Data;

/**
 * Fabric transaction reads/writes set.
 *
 * @author ecsoya
 * @date 2021 -07-07
 */
@Data
public class FabricTransactionRwSet {

    /**
     * Read transactions.
     */
    private List<FabricTransactionRw> reads = new ArrayList<FabricTransactionRw>();

    /**
     * Write transactions.
     */
    private List<FabricTransactionRw> writes = new ArrayList<FabricTransactionRw>();

    /**
     * Sets reads.
     *
     * @param readsList the reads list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:14
     */
    public void setReads(List<KVRead> readsList) {
		reads = new ArrayList<>();
		if (readsList != null) {
			for (int i = 0; i < readsList.size(); i++) {
				reads.add(FabricTransactionRw.fromRead(i, readsList.get(i)));
			}
		}
	}

    /**
     * Sets writes.
     *
     * @param writesList the writes list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public void setWrites(List<KVWrite> writesList) {
		writes = new ArrayList<>();
		if (writesList != null && !writesList.isEmpty()) {
			for (int i = 0; i < writesList.size(); i++) {
				writes.add(FabricTransactionRw.fromWrite(i, writesList.get(i)));
			}
		}
	}

    /**
     * Add reads.
     *
     * @param readsList the reads list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public void addReads(List<KVRead> readsList) {
		if (readsList == null || readsList.isEmpty()) {
			return;
		}
		for (int i = 0; i < readsList.size(); i++) {
			FabricTransactionRw r = FabricTransactionRw.fromRead(reads.size(), readsList.get(i));
			if (r == null) {
				continue;
			}
			reads.add(r);
		}
	}

    /**
     * Add writes.
     *
     * @param writesList the writes list
     * @author XieXiongXiong
     * @date 2021 -07-07 10:34:15
     */
    public void addWrites(List<KVWrite> writesList) {
		if (writesList == null || writesList.isEmpty()) {
			return;
		}
		for (int i = 0; i < writesList.size(); i++) {
			FabricTransactionRw w = FabricTransactionRw.fromWrite(writes.size(), writesList.get(i));
			if (w == null) {
				continue;
			}
			writes.add(w);
		}
	}
}
