package io.github.ecsoya.fabric.explorer.request;

import com.google.gson.Gson;
import io.github.ecsoya.fabric.bean.FabricObject;
import io.github.ecsoya.fabric.explorer.model.UserModel;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 * The type User fabric request.
 *
 * @author XieXiongXiong
 * @date 2021 -07-06
 */
public class UserFabricRequest extends FabricObject {
    /**
     * TYPE
     */
    public static final String TYPE = "user";

    private UserModel data;

    public UserFabricRequest() {
        super();
        super.setType(TYPE);
    }

    @Override
    public Map<String, Object> getValues() {
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return gson.fromJson(json,Map.class);
    }

    public void setData(UserModel data) {
        this.data = data;
    }
}
