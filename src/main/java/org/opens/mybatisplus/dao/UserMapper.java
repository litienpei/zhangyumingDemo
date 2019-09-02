package org.opens.mybatisplus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.opens.mybatisplus.pojo.UserDemo;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<UserDemo> {

    public List<UserDemo> selectByPageTTDatabase(Map<String, Object> map);

}
