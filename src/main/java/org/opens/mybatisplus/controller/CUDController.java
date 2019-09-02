package org.opens.mybatisplus.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.opens.mybatisplus.dao.UserMapper;
import org.opens.mybatisplus.pojo.UserDemo;
import org.opens.mybatisplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简介:
 *      crud是指在做计算处理时的增加(Create)、读取(Read)、更新(Update)和删除(Delete)几个单词的首字母简写。
 *      crud主要被用在描述软件系统中数据库或者持久层的基本操作功能。
 * 功能:
 *      create -> Post请求
 *      read   -> Get请求
 *      update -> Put请求
 *      delete -> Delete请求
 */
@RestController(value = "cUDController")
@RequestMapping(value = "/user")
public class CUDController {

    @Autowired
    private UserMapper userMapper;

    @Qualifier(value = "userService")
    @Autowired
    private UserService userService;

    /**
     * 简介:
     *      这种添加方式是以被添加的对象为参数的,.
     *      -请求方式
     *          1. http://localhost/user/insert?name=张张3&old=222
     *          2. http://localhost/user/insert?id=14&name=张张3&old=222
     *          3. http://localhost/user/insert?name=张张4
     *      -mybatis-plus在添加数据时, 可以不指定id, 因为指定了也是没用的, id默认使用mysql的自增张策略.
     * @param userDemo
     * @return
     */
    @PostMapping(value = "/insert")
    public Map<String, Object> insert(
            UserDemo userDemo
    ) {
        int affectedRows = userMapper.insert(userDemo);
        Map<String, Object> result = new HashMap<>();
        result.put("count", affectedRows);
        result.put("row", new ArrayList<UserDemo>());
        return result;
    }

    /**
     * 简介:
     *      -功能:
     *          这个方法的功能时根据id来删除对应的用户, 并且配置了事务, 就是当该方法删除的数据超过1行的话, 就会引起事务.
     *      -执行的sql
     *          ==>  Preparing: DELETE FROM jpa_user WHERE id=?
     *          ==>  Parameters: 14(Integer)
     *
     * @param id
     * @return
     * @throws Exception
     */
    @DeleteMapping(value = "/deleteById")
    public Map<String, Object> deleteById(
        @RequestParam(value = "id") int id
    ) {
        boolean deleteFlag = userService.removeById(id);

        Map<String, Object> result = new HashMap<>();
        result.put("code", deleteFlag ? 200 : 300);
        result.put("message", deleteFlag ? "删除成功" : "删除失败");
        return result;
    }

    /**
     * 简介:
     *      -执行的sql
     *          --sql1(传全部参数) http://localhost/user/updateById?id=24&name=李四10&old=244
     *              ==>  Preparing: UPDATE jpa_user SET old=?, name=? WHERE id=?
     *              ==>  Parameters: 244(Integer), 李四10(String), 24(Integer)
     *          --sql2(少传一个参数) http://localhost/user/updateById?id=24&old=244
     *              ==>  Preparing: UPDATE jpa_user SET old=? WHERE id=?
     *              ==>  Parameters: 2440(Integer), 24(Integer)
     *          --sql3(少了id) http://localhost/user/updateById?old=244 更新失败
     *              ==>  Preparing: UPDATE jpa_user SET old=? WHERE id=?
     *              ==>  Parameters: 2440(Integer), null
     *      -说明
     *          由于使用了对象传参, 不能限制其必须被传参, 但是并不会产生错误, 但是会使id值为null, 导致更新失败.
     *
     * @param userDemo
     * @return
     */
    @PutMapping(value = "/updateById")
    public Map<String, Object> updateById(
            UserDemo userDemo
    ) {
        int affectedRows =  userMapper.updateById(userDemo);
        Map<String, Object> result = new HashMap<>();
        result.put("code", affectedRows == 1 ? 200 : 300);
        result.put("message", affectedRows == 1 ? "修改成功" : "修改失败");
        return result;
    }

    /**
     * 简介:
     *      -执行的sql
     *          --场景1 http://localhost/user/update?id=25&name=123456&old=123
     *              ==>  Preparing: UPDATE jpa_user SET old=?, name=? WHERE (id = ? OR name = ?)
     *              ==>  Parameters: 25(Integer), 123456(String), 123(Integer), 123456(String)
     *          --场景2 http://localhost/user/update?id=25&name=&old=123 使name为空
     *              ==>  Preparing: UPDATE jpa_user SET old=?, name=? WHERE (id = ?)
     *              ==>  Parameters: 25(Integer), (String), 123(Integer)
     *          --场景3
     *              缺少old参数可以正常请求, 但是缺少name参数的话就不可以正常请求了, 报空指针异常.
     *      -
     * @param userDemo
     * @return
     */
    @PutMapping(value = "update")
    public Map<String, Object> update(
            UserDemo userDemo
    ) {
        QueryWrapper<UserDemo> condition = new QueryWrapper<>();
        if(userDemo == null || userDemo.getId() == null) {
            return getResult(0, "update");
        }
        condition.eq("id", userDemo.getId());
        if(!StrUtil.isBlank(userDemo.getName())) {
            condition.or().eq("name", userDemo.getName().trim());
        }

        /*
        简介:
            这个部分使用测试为什么缺少old参数可以正常请求, 但是缺少name参数就不能正常请求了, 但是测试失败了, 实验如下:
        猜测:
            是否因为函数中使用了name参数, 所以导致必须有这个参数? 将old参数也加到条件中后再次请求并查看结果.
        结果:
            修改后的代码在没有old参数时仍旧可以正常请求, 所以猜测错误.
        改动代码:
            if(userDemo.getOld() != null) {
                condition.or().eq("old", userDemo.getOld());
            }
        */
        int updateRows = userMapper.update(userDemo, condition);
        //过滤掉用户输入的无效空格, 可以给这个pojo做一个封装函数, 将参数中的String的左右两侧的空格都处理掉
        userDemo.setName(userDemo.getName().trim());
        return getResult(updateRows, "update");
    }

    /**
     * 简介:
     *      -执行的sql
     *          --请求1(不携带参数name) http://localhost/user/deleteByMap?id=123
     *              ==>  Preparing: DELETE FROM jpa_user WHERE id = ?
     *              ==>  Parameters: 123(Integer)
     *          --请求2(都携带) http://localhost/user/deleteByMap?id=25&name=旺达
     *              ==>  Preparing: DELETE FROM jpa_user WHERE name = ? AND id = ?
     *              ==>  Parameters: 旺达(String), 25(Integer)
     *          --请求3(不懈怠任何参数)
     *              报错:
     *              ...
     *                "status": 400,
     *                "error": "Bad Request",
     *                "message": "Required Integer parameter 'id' is not present",
     *              ...
     *       -总结
     *          1. @RequestParam修饰的参数的required参数默认为true, 表示请求必须传递这个参数才能正常传递.
     *
     * @param id
     * @param name
     * @return
     */
    @DeleteMapping(value = "/deleteByMap")
    public Map<String,Object> deleteByMap(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name", required = false) String name
    ) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        if(name != null && !"".equals(name.trim())) {
            params.put("name", name);
        }

        int deleteRows = userMapper.deleteByMap(params);
        return getResult(deleteRows, "delete");
    }

    /**
     * 简介:
     *      -执行的sql
     *          --场景1 http://localhost/user/deleteByWrapper?id=123
     *              ==>  Preparing: DELETE FROM jpa_user WHERE (id = ?)
     *              ==>  Parameters: 123(Integer)
     *          --场景2 http://localhost/user/deleteByWrapper?id=123&name=张 条件构造器构造出来的sql
     *              ==>  Preparing: DELETE FROM jpa_user WHERE (id = ? OR name LIKE ?)
     *              ==>  Parameters: 123(Integer), 张%(String)
     *           --场景3 http://localhost/user/deleteByWrapper 使用了id的默认值
     *              ==>  Preparing: DELETE FROM jpa_user WHERE (id = ?)
     *              ==>  Parameters: -1(Integer)
     *      -注意
     *          1. like删除需慎重, 真正的环境中还是不要使用like作为条件进行删除, 这里只是效果展示;
     *          2. id没有设置required = false, 但是其默认值就是false, 可以不传递, 但是为了防止删除整个数据库, 一定要设置
     *          一个默认值, 这个值是应该是无效的.
     *
     * @param id
     * @param name
     * @return
     */
    @DeleteMapping(value = "/deleteByWrapper")
    public Map<String, Object> deleteByWrapper(
            @RequestParam(value = "id", defaultValue = "-1") Integer id,
            @RequestParam(value = "name", required = false) String name
    ) {
        QueryWrapper<UserDemo> condition = new QueryWrapper<>();
        condition.eq("id", id);
        if(name != null && !"".equals(name.trim())) {
            condition.or().likeRight("name", name);
        }

        int deleteRows = userMapper.delete(condition);
        return getResult(deleteRows, "delete");
    }

    /**
     * 简介:
     *      -执行的sql
     *          --场景1 http://localhost/user/deleteBatchIds?ids= 意思是传递的ids是一个空的
     *              直接被筛选掉, 不会访问数据库去执行sql.
     *          --场景2 http://localhost/user/deleteBatchIds?ids=20,21,22
     *              ==>  Preparing: DELETE FROM jpa_user WHERE id IN ( ? , ? , ? )
     *              ==>  Parameters: 20(Integer), 21(Integer), 22(Integer)
     *      -注意事项
     *          传递数组类型的参数时, 不能指定为null, 会导致传递参数失败报错.
     *          报错:
     *              ...
     *              "status": 400,
     *              "error": "Bad Request",
     *              "message": "Failed to convert value of type 'java.lang.String' to required type 'int[]'; nested exception is java.lang.NumberFormatException: For input string: \"null\"",
     *              ...
     *          可以发现, 报错的意思是String不能转换为int[], convert转换器转换失败.
     *
     * @param arr
     * @return
     */
    @DeleteMapping(value = "/deleteBatchIds")
    public Map<String, Object> deleteBatchIds(
            @RequestParam(value = "ids", required = true) int[] arr
    ) {
        if(arr.length == 0) {
            return getResult(0, "删除");
        }
        List<Integer> paramList = new ArrayList<>();
        for (int i : arr) {
            paramList.add(i);
        }
        int deleteRows = userMapper.deleteBatchIds(paramList);
        return getResult(deleteRows, "delete");
    }

    private Map<String, Object> getResult(
            int affectedRows,
            String databaseOperation
    ) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", affectedRows > 0 ? 200 : 300);
        result.put("message", affectedRows > 0 ? "成功" + databaseOperation + "了" + affectedRows + "条数据" : databaseOperation + "失败");
        return result;
    }

}
