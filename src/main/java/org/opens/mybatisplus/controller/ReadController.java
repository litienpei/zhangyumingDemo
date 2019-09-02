package org.opens.mybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.opens.mybatisplus.dao.UserMapper;
import org.opens.mybatisplus.pojo.UserDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController(value = "readController")
@RequestMapping(value = "/user")
public class ReadController {

    @Autowired
    private UserMapper userMapper = null;

    /**
     * 简介:
     *      最简单的根据id查询对应的用户.
     * @param id id值
     * @return {"count":1,"rows":[{"id":1,"name":"王五","old":12}]}
     */
    @GetMapping(value = "/selectUserDemoById")
    public Map<String, Object> selectById(
            @RequestParam(value = "id") int id
    ) {
        UserDemo userDemo = userMapper.selectById(id);
        Map<String, Object> res = new HashMap<>();
        res.put("rows", Arrays.asList(userDemo));
        res.put("count", 1);
        return res;
    }

    /**
     * 执行的sql:
     *      ==>  Preparing: SELECT id,old,name FROM jpa_user WHERE id IN ( ? , ? , ? )
     *      ==>  Parameters: 1(Integer), 2(Integer), 3(Integer)
     * 总结:
     *      in关键字会在大数据量的时候影响性能, 但是由于这个方法使用的是主键, 所以有主键索引能好些.
     * @param ids
     * @return
     */
    @GetMapping(value = "/selectUserDemoByIds")
    public Map<String, Object> selectBatchIds(
            @RequestParam(value = "ids") int[] ids
    ) {
        Map<String, Object> res = new HashMap<>();
        List<Integer> arr = new ArrayList<>(ids.length);
        for (int id : ids) {
            arr.add(id);
        }
        List<UserDemo> list = userMapper.selectBatchIds(arr);
        res.put("rows", list);
        res.put("count", list.size());
        return res;
    }

    /**
     * 执行的sql:
     *      ==>  Preparing: SELECT id,old,name FROM jpa_user WHERE name = ? AND id = ?
     *      ==>  Parameters: 王五(String), 1(Integer)
     * 总结:
     *      1.这个方法可以配合上if判断, 可以达到动态sql的效果.
     *      2.Map<String, Object>作为参数时, 可以将它理解为条件全是"=", 连接全是and的特殊情况.
     * 注意:
     *      1.required = false表示这个请求可以没有这个参数;
     *      2.如果传入selectByMap的Map<String, Object>对象为空, 则where语句会自动被屏蔽, 此时就是全表查.
     * 这个方法的请求方式现在有三种:
     *      1.http://localhost/user/selectByMap?id=1
     *      2.http://localhost/user/selectByMap?id=1&name=王五
     *      3.http://localhost/user/selectByMap
     * @param id 传入的主键id
     * @param name 非主键参数
     * @return {"count":1,"rows":[{"id":1,"name":"王五","old":12}]}
     */
    @GetMapping(value = "/selectByMap")
    public Map<String, Object> selectByMap(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "name", required = false) String name
    ) {
        Map<String, Object> parameterOne = new HashMap<>();
        Map<String, Object> res = new HashMap<>();
        System.out.println(id == null);

        if(name != null) {
            parameterOne.put("name", name);
        }
        if(id != null) {
            parameterOne.put("id", id);
        }

        List<UserDemo> list = userMapper.selectByMap(parameterOne);
        res.put("rows", list);
        res.put("count", list.size());
        return res;
    }

    /**
     * 简介:
     *      -执行的sql
     *          ==>  Preparing: SELECT id,old,name FROM jpa_user WHERE (id = ? AND name = ?)
     *          ==>  Parameters: 1(Integer), 王五(String)
     *      -QueryWrapper<T>是什么?
     *          它是条件构造器, 可以不写sql构造出复杂条件的sql.
     *      -请求的方式
     *          1.http://localhost/user/UserByIdOrName?id=1
     *          2.http://localhost/user/UserByIdOrName?id=1&name=王五
     * 注意:
     *      条件构造函数(eq, gt, ne, like)这些条件之间默认是以and进行连接的, 如果是or需要自行指明.
     *
     * @param id
     * @param name
     * @return
     */
    @GetMapping(value = "/UserByIdOrName")
    public Map<String, Object> selectOne(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name", required = false) String name
    ) {

        QueryWrapper<UserDemo> condition = new QueryWrapper<UserDemo>();
        condition.eq("id", id);
        if(name != null) {
            condition.eq("name", name);
        }
        List<UserDemo> list = new ArrayList<>(1);
        list.add(userMapper.selectOne(condition));

        Map<String, Object> res = new HashMap<>(2);
        res.put("rows", list);
        res.put("count", 1);
        return res;
    }

    @RequestMapping(value = "/countsByIdOrName")
    public Map<String, Object> countsByIdOrName(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "name", required = false) String name
    ) {
        QueryWrapper<UserDemo> condition = new QueryWrapper<>();
        condition.eq("id", id);
        if(name != null) {
            condition.eq("name", name);
        }

        Map<String, Object> res = new HashMap<>(2);
        res.put("rows", new ArrayList<>());
        res.put("count", userMapper.selectCount(condition));
        return res;
    }

    /**
     * 简介:
     *      使用原生limit实现分页查询.
     *
     * @param pageNumber 当前页码
     * @param pageSize   每页行数
     * @param id         条件1
     * @param name       条件2
     * @return           分页后的数据
     */
    @RequestMapping(value = "/selectByPage")
    public Map<String, Object> selectByPage(
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "name", required = false) String name

    ) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("pageNumber", pageSize * (pageNumber - 1));
        parameter.put("pageSize", pageSize);
        parameter.put("id", id);
        parameter.put("name", name);
        List<UserDemo> arr = userMapper.selectByPageTTDatabase(parameter);

        Map<String, Object> res = new HashMap<>(2);
        res.put("rows", arr);
        res.put("count", arr.size());
        res.put("pageNumber", pageNumber);
        res.put("pageSize", pageSize);

        return res;
    }

    /**
     * 简介:
     *      通过配置mybatis的插件进行分页.
     * 输出:
     *      {
     *          "records": [],    #数据
     *          "total": 9,       #数据总行数
     *          "size": 5,        #每页行数
     *          "current": 2,     #当前页
     *          "orders": [],
     *          "searchCount": true,
     *          "pages": 2        #总页数
     *      }
     * 执行sql:
     *      -场景1(只携带name参数时)
     *          ==>  Preparing: SELECT id,old,name FROM jpa_user WHERE (name LIKE ?) LIMIT ?,?
     *          ==> Parameters: %王%(String), 0(Long), 5(Long)
     *      -场景2(不携带任何参数)
     *          ==>  Preparing: SELECT id,old,name FROM jpa_user LIMIT ?,?
     *          ==> Parameters: 0(Long), 5(Long)
     *      -场景3(携带所有参数)
     *          ==>  Preparing: SELECT id,old,name FROM jpa_user WHERE (id = ? AND name LIKE ?) LIMIT ?,?
     *          ==> Parameters: 1(Integer), %王%(String), 0(Long), 5(Long)
     *      -总结
     *          最终产生的sql会自动根据传入的QueryWrapper构建出对应的sql语句.
     * @param pageNumber
     * @param pageSize
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/selectByPage2")
    public IPage<UserDemo> selectByPage2(
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "name", required = false) String name
    ) {
        Page<UserDemo> page = new Page<>(pageNumber, pageSize);
        QueryWrapper<UserDemo> condition = new QueryWrapper<>();
        if(id != null) {
            condition.eq("id", id);
        }
        if(name != null && !"".equals(name.trim())) {
            condition.like("name", name);
        }
        return userMapper.selectPage(page, condition);
    }

}
