package org.opens.shiro.pojo;

import java.io.Serializable;

/**
 * (EwayUserRole)实体类
 *
 * @author makejava
 * @since 2019-08-21 18:21:02
 */
public class EwayUserRole implements Serializable {
    private static final long serialVersionUID = -37430144545764799L;
    
    private Integer ewayUserId;
    
    private Integer roleId;

    public Integer getEwayUserId() {
        return ewayUserId;
    }

    public void setEwayUserId(Integer ewayUserId) {
        this.ewayUserId = ewayUserId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}