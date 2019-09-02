package org.opens.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import org.junit.Test;
import org.opens.configuration.java.pojotest.TestBean2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@SuppressWarnings("unchecked")
public class JsonPathDemo {

    String jsonStr = "{\"store\":{\"book\":[{\"title\":\"高效Java\",\"price\":10},{\"title\":\"研磨设计模式\",\"price\":12},{\"title\":\"重构\",\"isbn\":\"553\",\"price\":8},{\"title\":\"虚拟机\",\"isbn\":\"395\",\"price\":22}],\"bicycle\":{\"color\":\"red\",\"price\":19}}}";

    /**
     * 简介:
     *      成功读取到了json字符串中的数据, 步骤是先使用read方法将json字符串先转换为对象, 然后根据后面设置的规则检索数据,
     */
    @Test
    public void test1() {
        TestBean2 bean = new TestBean2();
        bean.setId(1);
        bean.setName("张三");
        bean.setDate(new Date());
        String json = JSON.toJSONString(bean);
        Object res = JSONPath.read(json, "$.name");//似乎是如果结果集只有一条, 返回就是Object
        System.out.println(res.getClass());
        System.out.println(res);
    }

    /**
     * 简介:
     *      -之所以不直接使用JSONPath的eval函数, 是因为java8字典的流特性比这个还强大.
     *      -这个例子的功能是对比test1判断返回的记过的格式是否和数据的个数有关.
     * 输出:
     *      [1,2,3,4]
     */
    @Test
    public void test2() {
        List<TestBean2> list = new ArrayList<>();
        list.add(new TestBean2(1, "sd1", new Date()));
        list.add(new TestBean2(2, "sd2", new Date()));
        list.add(new TestBean2(3, "sd3", new Date()));
        list.add(new TestBean2(4, "sd4", new Date()));

        List<Object> res = (List<Object>) JSONPath.read(JSON.toJSONString(list), "$.id");
        System.out.println(res);
    }

    /**
     * 简介:
     *      (一/零)个参数时list的转换
     * 输出:
     *      [1]
     *      []
     */
    @Test
    public void test3() {
        List<TestBean2> list = new ArrayList<>();
        list.add(new TestBean2(1, "sd1", new Date()));
        List<TestBean2> list1 = new ArrayList<>();
        List<Object> res = (List<Object>) JSONPath.read(JSON.toJSONString(list), "$.id");
        List<Object> res1 = (List<Object>) JSONPath.read(JSON.toJSONString(list1), "$.id");
        System.out.println(res);
        System.out.println(res1);
    }

    /**
     * 简介:
     *      使用read读取json字符串, 然后用oql语句检索出数据转换为一个list;
     * 注意:
     *      read会将一个String转换为一个对象, 而后面的"$"可以想象为当前对象, "."相当于逐级打开对象中的对象.
     */
    @Test
    public void test4() {
        // 获取json中store下book下的所有title值
        List<Object> titles = (List<Object>) JSONPath.read(jsonStr, "$.store.book.title");
        System.out.println("$.store.book.title = " + titles);
        List<Object> prices = (List<Object>) JSONPath.read(jsonStr, "$.store.book.price");
        System.out.println("$.store.book.price = " + prices);
    }

    /**
     * 简介:
     *      和test4功能时相同的, 相当于使简化写法.
     * 输出:
     *      $.store.book.title = [高效Java, 研磨设计模式, 重构, 虚拟机]
     */
    @Test
    public void test5() {
        // 获取json中store下book下的所有title值
        List<Object> titles = (List<Object>) JSONPath.read(jsonStr, "$..title");
        System.out.println("$.store.book.title = " + titles);
    }

    /**
     * 简介:
     *      搜索目标集合中包含"isbn"字段的对象.
     * 输出:
     *      $.store.book.title = [{"price":8,"isbn":"553","title":"重构"},{"price":22,"isbn":"395","title":"虚拟机"}]
     */
    @Test
    public void test6() {
        // 获取json中store下book下的所有title值
        List<Object> titles = (List<Object>) JSONPath.read(jsonStr, "$.store.book[?(@.isbn)]");
        System.out.println("$.store.book.title = " + titles);
    }

    /**
     * 简介:
     *      检索目标对象中price小于10的对象.
     * 输出:
     *      $.store.book[price < 10] = [{"price":8,"isbn":"553","title":"重构"}]
     */
    @Test
    public void test7() {
        List<Object> prices = (List<Object>) JSONPath.read(jsonStr, "$.store.book[price < 10]");
        System.out.println("$.store.book[price < 10] = " + prices);
    }

    /**
     * 简介:
     *      判断json串中数组中元素的个数.
     * 输出:
     *      $.store.book[title like '%java%'] = 4
     */
    @Test
    public void test8() {
        int size = (int) JSONPath.read(jsonStr, "$.store.book.size()");
        System.out.println("$.store.book.size() = " + size);
    }

    /**
     * 简介:
     *      该功能可能有用, 用来搜索出json中符合要求的数据, $.store.book[title like '%Java%']表达式的意思是匹配出store
     *      对象中的book对象中title中包含Java字段的对象.
     * 输出:
     *      $.store.book[title like '%Java%'] = [{"price":10,"title":"高效Java"}]
     */
    @Test
    public void test9() {
        List<Object> res = (List<Object>) JSONPath.read(jsonStr, "$.store.book[title like '%Java%']");
        System.out.println("$.store.book[title like '%Java%'] = " + res);
    }


}
