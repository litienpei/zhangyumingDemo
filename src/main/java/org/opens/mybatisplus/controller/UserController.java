package org.opens.mybatisplus.controller;

import org.opens.mybatisplus.dao.UserMapper;
import org.opens.mybatisplus.pojo.UserDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简介:
 *      这个demo主要介绍了以下内容:
 *          1. spring mvc数据传递的方式, 也就是如何解析页面传递过来的数据;
 *          2. 使用自定义转换器转换页面传过来的数据;
 *          3. 如何解析批量数据为List;
 *          4. 如何发起异步请求传递json数据到后台(感觉不常用, 因为可以直接传递为什么还要转换为其他类型);
 *          5. 使用spring mvc自带的时间转换器和数字格式转换器;
 *          6. 路径传参;
 */
@Controller(value = "userController")
@RequestMapping(value = "/mybatis")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/user")
    @ResponseBody
    public UserDemo getUserById(
            @RequestParam(value = "id") int id
    ) {
        logger.info("查询id=" + id);
        return userMapper.selectById(id);
    }

    /**
     * 简介:
     *      使用int[]这样的数组接受参数
     * 注意:
     *      数组参数的请求方式比价特殊:http://localhost:80/mybatis/array,
     *      携带参数: intArr=1,2,3 strArr=s1,s2,s3
     *      携带参数需要以逗号相隔传递到spring mvc中才能转换为数组.
     * 结果:
     *      {
     *          "intArr": [
     *          1,
     *          2,
     *          3
     *          ],
     *          "strArr": [
     *          "s1",
     *          "s2",
     *          "s3"
     *          ]
     *      }
     * @param intArr int类型的数组参数
     * @param strArr String类型的数组参数
     * @return 显示这些数组
     */
    @PostMapping(value = "/array")
    @ResponseBody
    public Map<String, Object> getArray(
            int[] intArr,
            String[] strArr
    ) {
        Map<String, Object> res = new HashMap<>();
        res.put("intArr", intArr);
        res.put("strArr", strArr);
        return res;
    }

    /**
     * 简介:
     *      给参数加上@RequestBody就表示这个参数是接受json类型的参数的
     * 用途:
     *      一般是发起$.post()请求时, 可以用JSON.stringify(js中的数组)转换为json, 但是感觉并不常用, 因为这样做的话
     *      还需要给$.post()设置一个参数: contentType : application/json
     * 例如:
     *      ...
     *      var param = {
     *          "id" : 1,
     *          "name" : "zs",
     *          "old" : 23
     *      };
     *      $.post({
     *          url : "/mybatis/jsonparams",
     *          //这个参数的作用是通知该请求所携带的参数时json类型的
     *          contentType : application/json,
     *          data : JSON.stringify(param),
     *          success : function() {
     *              //回调操作;
     *              ...
     *          }
     *      });
     *      ...
     * @param userDemo 对应1个新增的数据
     * @return 新增的数据
     */
    @PostMapping(value = "/jsonparams")
    @ResponseBody
    public UserDemo postByJsonParams(
            @RequestBody UserDemo userDemo
    ) {
        return userDemo;
    }

    /**
     * 简介:
     *      通过url传递参数
     * 请求方式为:
     *      http://localhost:80/mybatis/urlParams/3/zhangyuming/27
     * @param id 对应用户id
     * @param name 对应用户姓名
     * @param old 对应用户年龄
     * @return 这个用户的信息
     */
    @GetMapping(value = "/urlParams/{id}/{name}/{old}")
    @ResponseBody
    public UserDemo getUrlParams(
            @PathVariable(value = "id") int id,
            @PathVariable(value = "name") String name,
            @PathVariable(value = "old") int old
    ) {
        UserDemo user = new UserDemo();
        user.setId(id);
        user.setName(name);
        user.setOld(old);
        return user;
    }

    /**
     * 简介:
     *      这个demo主要用来模拟处理时间类型的参数和特殊格式的数字类型的参数
     * 说明:
     *      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)这个注解默认可以解析yyyy-MM-dd格式的时间, 如果是其他
     *      类型的时间, 就需要去配置文件中修改spring.mvc.date-format: 其他解析格式
     *
     *      @NumberFormat(pattern = "#,###.##")主要用来处理特殊格式的字符串为数字, 例如#,###.##这个匹配模式就可以
     *      传入123,456,789.45格式的字符串, 这个注解可以帮我们转换为对应的数字, 并且使用这个注解的好处就是不影响正常格
     *      式, 意思是即时你规定了#,###.##这个匹配模式, 仍旧可以传入123456789.45格式的数字.
     * @param date 时间类型的参数
     * @param number Double 类型的参数
     * @return 验证的结果
     */
    @PostMapping(value = "/DateAndNumber")
    @ResponseBody
    public Map<String, Object> dateAndNumberFormat(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @NumberFormat(pattern = "#,###.##") Double number
    ) {
        Map<String, Object> res = new HashMap<>();
        res.put("date", date);
        res.put("number", number);
        return res;
    }

    /**
     * 简介:
     *      这里的转换就用到了我们在org.opens.mybatisplus.converter.String2User包下定义的转换器, 这个转换器能将
     *      2-pengpeng-23类型的数据转换为UserDemo类型.
     * 对应请求:
     *      http://localhost:80/mybatis/converter2user?userDemo=2-pengpeng-23
     * @param userDemo
     * @return
     */
    @GetMapping(value = "/converter2user")
    @ResponseBody
    public UserDemo converterDemo(UserDemo userDemo) {
        return userDemo;
    }

    /**
     * 简介:
     *      这个demo的使用场景是想一次性接受多个参数并进行转化的例子, 这个过程不仅用到了我们自定义的转换器, 还用到了Spring
     *      mvc自带的数组转换器GenericConverter, 转换的过程应该是先使用GenericConverter将String转换为数组, 然后找到
     *      子字符串的转换器进行转换
     * 请求方式:
     *      http://localhost:80/mybatis/insertManyUsers
     *      携带参数:
     *          userDemoList : 2-pengpeng-23,3-pengpeng2-23,4-pengpeng3-25
     *      返回结果:
     *          [
     *              {
     * 				    "id": 2,
     * 				    "name": "pengpeng",
     * 				    "old": 23
     *              },
     *              {
     * 				    "id": 3,
     * 				    "name": "pengpeng2",
     * 				    "old": 23
     *              },
     *              {
     * 				    "id": 4,
     * 				    "name": "pengpeng3",
     * 				    "old": 25
     *              }
     * 			]
     * @param userDemoList
     * @return
     */
    @PostMapping(value = "insertManyUsers")
    @ResponseBody
    public List<UserDemo> inserts(List<UserDemo> userDemoList) {
        return userDemoList;
    }

}
