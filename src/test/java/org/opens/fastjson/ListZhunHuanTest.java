package org.opens.fastjson;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ListZhunHuanTest {

    /**
     * 对象中的list进行转换
     */
    @Test
    public void test1() {
        PersonBean2<Integer> person = new PersonBean2<>(1, "王五");
        person.add(12).add(13).add(12).add(13).add(12).add(13);
        //System.out.println(person);
        String jsonStr = JSON.toJSONString(person);
        System.out.println(jsonStr);
        PersonBean2 person2 = JSON.parseObject(jsonStr, PersonBean2.class);
        System.out.println(person2);
    }

    /**
     * 简介:
     *      使用fast-json处理list.
     * 输出:
     *      [1,11,111,1111]
     *      1
     *      11
     *      111
     *      1111
     */
    @Test
    public void test2() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(11);
        list.add(111);
        list.add(1111);
        String str = JSON.toJSONString(list);
        System.out.println(str);
        List<Integer> arr = JSON.parseArray(str, Integer.class);
        arr.forEach(System.out::println);
    }

}
