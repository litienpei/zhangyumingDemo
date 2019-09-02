package org.opens.configuration.java.pojotest;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Date;

public class JsonFormatTest {

    /**
     * 简介:
     *      测试json处理工具对Date的处理
     * 输出:
     *      {"date":1566980948223,"id":1,"name":"张三"}
     * 总结:
     *      可以发现, fastjson默认是转换为时间戳的.
     */
    @Test
    public void test1() {
        TestBean bean = new TestBean();
        bean.setId(1);
        bean.setName("张三");
        bean.setDate(new Date());
        System.out.println(JSON.toJSONString(bean));
    }

    /**
     * 简介:
     *      给需要生成json的对象添加fastjson的@JSONField注解, fastjson会自动做出时间格式转换.
     * 输出:
     *      {"date":"2019-08-28 16:32:55","id":1,"name":"张三"}
     */
    @Test
    public void test2() {
        TestBean2 bean = new TestBean2();
        bean.setId(1);
        bean.setName("张三");
        bean.setDate(new Date());
        System.out.println(JSON.toJSONString(bean));
    }

}
