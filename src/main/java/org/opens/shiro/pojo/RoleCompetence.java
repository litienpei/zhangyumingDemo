package org.opens.shiro.pojo;

import java.io.Serializable;

/**
 * (RoleCompetence)实体类
 *
 * @author makejava
 * @since 2019-08-21 18:21:02
 */
public class RoleCompetence implements Serializable {
    private static final long serialVersionUID = -49791794073363956L;
    
    private Integer roleId;
    
    private Integer competenceId;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Integer competenceId) {
        this.competenceId = competenceId;
    }

}