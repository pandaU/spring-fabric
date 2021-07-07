package io.github.ecsoya.fabric;

import io.github.ecsoya.fabric.utils.TypeResolver;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * The type Fabric pagination query.
 *
 * @param <T> the type parameter
 * @author XieXiongXiong
 * @date 2021 -07-07  *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FabricPaginationQuery<T> extends FabricQuery {

    /**
     * The bookmark of page querying.
     */
    private String bookmark = "";

    /**
     * Size of each page.
     */
    private int pageSize = 10;

    /**
     * Current page of querying
     */
    private int currentPage;

	@Override
	public String getType() {
		String type = super.getType();
		if (type == null) {
			Class<?> clazz = TypeResolver.resolveRawArgument(FabricPaginationQuery.class, getClass());
			if (clazz != null) {
				setType(clazz.getName());
			}
		}
		return super.getType();
	}

}
