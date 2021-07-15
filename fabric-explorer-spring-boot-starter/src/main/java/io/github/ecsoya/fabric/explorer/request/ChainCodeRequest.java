package io.github.ecsoya.fabric.explorer.request;

import lombok.Data;

/**
 * <p>
 * The type Chain code request.
 *
 * @author XieXiongXiong
 * @date 2021 -07-15
 */
@Data
public class ChainCodeRequest {
    private String chainCodeName;

    private Integer currentPage;

    private Integer pageSize;
}
