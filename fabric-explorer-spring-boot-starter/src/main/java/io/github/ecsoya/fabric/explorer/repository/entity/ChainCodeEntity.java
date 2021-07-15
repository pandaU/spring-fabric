package io.github.ecsoya.fabric.explorer.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * <p>
 * The type Chaincode entity.
 *
 * @author XieXiongXiong
 * @date 2021 -07-15
 */
@Data
@TableName("chain_code")
public class ChainCodeEntity extends Model<ChainCodeEntity> {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "chain_code_name")
    private String chainCodeName;

    @TableField(value = "chain_code_version")
    private String chainCodeVersion;

    @TableField(value = "chain_code_sequence")
    private Long chainCodeSequence;

    @TableField(value = "chain_code_policy")
    private String chainCodePolicy;

    @TableField(value = "chain_code_language")
    private String chainCodeLanguage;

    @TableField(value = "create_msp")
    private String createMsp;

    @TableField(value = "create_role")
    private String createRole;

    @TableField(value = "chain_code_package_id")
    private String chainCodePackageId;

    @TableField(value = "create_time")
    private String createTime;

    @TableField(value = "update_time")
    private String updateTime;

    @TableField(value = "status")
    private Integer status;

}
