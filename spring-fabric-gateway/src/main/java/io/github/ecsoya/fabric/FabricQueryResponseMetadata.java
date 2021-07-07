package io.github.ecsoya.fabric;

import lombok.Data;

/**
 * Page query metadata.
 *
 * @author Jin Liu (jin.liu@soyatec.com)
 * @date 2021 -07-07
 * @see FabricPagination
 * @see FabricPaginationQuery
 */
@Data
public class FabricQueryResponseMetadata {

    /**
     * The total count of page query.
     */
    private int recordsCount;

    /**
     * The bookmark of page query.
     */
    private String bookmark;
}
