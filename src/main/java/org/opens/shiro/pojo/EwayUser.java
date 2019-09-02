package org.opens.shiro.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * (EwayUser)实体类
 *
 * @author makejava
 * @since 2019-08-21 18:21:01
 */
@Data
@TableName(value = "eway_user")
public class EwayUser implements Serializable {
    private static final long serialVersionUID = -33895300648790997L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "account_number")
    private String accountNumber;

    @TableField(value = "name")
    private String name;

    @TableField(value = "password")
    private String password;

    /**
     * 注意:
     *      exist = false这个属性只会影响mybatis-plus的自带查询, 不会影响mybatis自定义查询.
     */
    @TableField(exist = false)
    private List<Role> list;

}