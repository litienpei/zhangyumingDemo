package org.opens.fastjson;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

public class ZhunHuanTest {

    /**
     * 将javabean转换为json
     */
    @Test
    public void test1() {
        PersonBean personBean = new PersonBean(1, "张三");
        String jsonString = JSON.toJSONString(personBean);
        System.out.println(jsonString); //{"id":1,"name":"张三"}
    }

    /**
     * json字符换转换为java对象
     */
    @Test
    public void test2() {
        PersonBean personBean = new PersonBean(1, "张三");
        String jsonString = JSON.toJSONString(personBean);
        PersonBean newPersonBean = JSON.parseObject(jsonString, PersonBean.class);
        System.out.println(newPersonBean);
    }



}
