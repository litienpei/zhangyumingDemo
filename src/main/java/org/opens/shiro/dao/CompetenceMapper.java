package org.opens.shiro.dao;

import java.util.Set;

public interface CompetenceMapper {

    /**
     * 简介:
     *      -函数意义
     *          getPermissionBasedOnRoleId - > 获取基于角色ID的权限
     * @param id 角色的id
     * @return
     */
    public Set<String> getPermissionBasedOnRoleId(int id);

}
