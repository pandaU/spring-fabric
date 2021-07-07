package io.ecsoya.github.fabric.chaincode;

import java.util.Objects;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

/**
 * <p>
 * The type Query meta.
 *
 * @author XieXiongXiong
 * @date 2021 -07-07
 */
@DataType
public class QueryMeta {

    /**
     * Records count
     */
    @Property
	private Integer recordsCount;

    /**
     * Bookmark
     */
    @Property
	private String bookmark;

    /**
     * Gets records count.
     *
     * @return the records count
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:01
     */
    public Integer getRecordsCount() {
		return recordsCount;
	}

    /**
     * Sets records count.
     *
     * @param recordsCount the records count
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:01
     */
    public void setRecordsCount(Integer recordsCount) {
		this.recordsCount = recordsCount;
	}

    /**
     * Gets bookmark.
     *
     * @return the bookmark
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:01
     */
    public String getBookmark() {
		return bookmark;
	}

    /**
     * Sets bookmark.
     *
     * @param bookmark the bookmark
     * @author XieXiongXiong
     * @date 2021 -07-07 11:38:01
     */
    public void setBookmark(String bookmark) {
		this.bookmark = bookmark;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRecordsCount(), getBookmark());
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}

		QueryMeta other = (QueryMeta) obj;

		return Objects.deepEquals(new Object[] { getRecordsCount(), getBookmark() },
				new Object[] { other.getRecordsCount(), other.getBookmark() });
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [recordsCount="
				+ recordsCount + ", bookmark=" + bookmark + "]";
	}
}
