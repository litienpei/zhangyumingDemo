package org.opens.mybatisplus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.opens.mybatisplus.pojo.DateTable;

@Mapper
public interface DateTableMapper extends BaseMapper<DateTable> {

    public int customInsert(DateTable dateTable);

}
