package org.opens.shiro.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.opens.shiro.pojo.EwayUser;

import java.util.Set;

@Mapper
public interface EwayUserDao extends BaseMapper<EwayUser> {

    public EwayUser getUserAndRolesById(int id);

    /**
     *
     * @param account 用户的账号
     * @return 该账号对应的用户信息
     */
    public EwayUser getUsersByAccount(String account);

    public Set<String> getRolesNameByAccount(String account);



}
