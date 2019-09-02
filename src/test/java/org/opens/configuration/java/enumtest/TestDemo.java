package org.opens.configuration.java.enumtest;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.opens.mybatisplus.pojo.result.ResultBean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestDemo {

    /**
     * 简介:
     *      enum就像是预先创建好的对象, 可以直接获取使用, 但是要注意的是enum就像pojo一样, 也需要get方法才可以, 但是可以没有set方法.
     */
    @Test
    public void test1() {
        System.out.println(EnumDemo.ERROR.getCode());
        System.out.println(EnumDemo.ERROR.getMessage());
    }

    @Test
    public void test2() {
        //EnumDemo a = new EnumDemo();  私有构造使其不能实例化
    }

    /**
     * 简介:
     *      更改enum的toString方法为非标准toString, 就可以直接使用枚举名称进行获取对应的值.
     * 输出:
     *      发生异常
     *      请求失败
     *      请求成功
     * 说明:
     *      原理请查看对应枚举的toString.
     */
    @Test
    public void test3() {
        System.out.println(EnumDemo.ERROR);
        System.out.println(EnumDemo.FAIL);
        System.out.println(EnumDemo.SUCCESS);
    }

    /**
     * 简介:
     *      由于web请求中一次请求返回一个值的可能性非常小, 例如EnumDemo被设计为一个表示web请求的状态返回, 由于重写了toString
     *      , 所以可以直接以枚举名称获取, 使用parseCode方法获取对应的状态码.
     * 输出:
     *      500
     *      300
     *      200
     */
    @Test
    public void test4() {
        System.out.println(EnumDemo.ERROR.parseCode());
        System.out.println(EnumDemo.FAIL.parseCode());
        System.out.println(EnumDemo.SUCCESS.parseCode());
    }

    /**
     * 简介:
     *      使用了一波反射, 本来目的是用函数名反射出enum的类, 最后想了想没必要.
     */
    @Test
    public void test5() {
        ResultBean<List<String>> result = new ResultBean<>();
        for(Method method : result.getClass().getMethods()) {
            System.out.println(method.getName());
        }
    }

    /**
     * 简介:
     *      测试自定义枚举返回结果是否可用.
     * 输出:
     *      {"code":200,"data":["1","2","3","4","5","6","7"],"message":"请求成功"}
     *      {"code":300,"message":"请求失败"}
     *      {"code":500,"message":"发生异常"}
     *
     */
    @Test
    public void test6() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        System.out.println(JSON.toJSONString(ResultBean.success(list)));
        System.out.println(JSON.toJSONString(ResultBean.fail()));
        System.out.println(JSON.toJSONString(ResultBean.error()));
    }


}
