package org.opens.mybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.opens.mybatisplus.dao.DateTableMapper;
import org.opens.mybatisplus.pojo.DateTable;
import org.opens.mybatisplus.pojo.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller(value = "dateTableController")
@RequestMapping(value = "/time")
public class DateTableController {

    @Autowired
    private DateTableMapper dateTableMapper;

    /**
     * 简介:
     *      -执行的sql
     *          ==>  Preparing: INSERT INTO date_table ( birthday, name ) VALUES ( ?, ? )
     *          ==>  Parameters: 2019-08-09 00:00:00.0(Timestamp), 张三1(String)
     * @param dateTable
     * @return
     */
    @PostMapping(value = "/insert")
    @ResponseBody
    public ResultBean<String> insert(
            DateTable dateTable
    ) {
        int insertRows = dateTableMapper.insert(dateTable);
        return ResultBean.success("成功插入" + insertRows + "条数据");
    }

    /**
     * 简介:
     *      -执行的sql
     *          ==>  Preparing: SELECT id,birthday,name FROM date_table WHERE (id = ? AND birthday = ?)
     *          ==>  Parameters: 2(Integer), 2019-08-09 00:00:00.0(Timestamp)
     *      -总结
     *          可以发现, mybatis会将Date类型的字段处理成Timestamp类型的时间然后再插入.
     * @param id
     * @param birthday
     * @return
     */
    @GetMapping(value = "/selectByIdAndBirthday")
    @ResponseBody
    public ResultBean<DateTable> selectByIdAndBirthday(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "birthday") Date birthday
    ) {
        QueryWrapper<DateTable> condition = new QueryWrapper<>();
        condition.eq("id", id);
        if(birthday != null) {
            condition.eq("birthday", birthday);
        }
        return ResultBean.success(dateTableMapper.selectOne(condition));
    }

    /**
     * 简介:
     *      -执行的sql
     *          ==>  Preparing: insert into date_table(id, name, birthday) values(null, ?, ?)
     *          ==>  Parameters: 张三3(String), 2019-08-09 00:00:00.0(Timestamp)
     * @param dateTable
     * @return
     */
    @PostMapping(value = "/customInsert")
    @ResponseBody
    public ResultBean<String> customInsert(
            DateTable dateTable
    ) {
        int insertRows = dateTableMapper.customInsert(dateTable);
        return ResultBean.success("成功插入" + insertRows + "条数据");
    }


}
