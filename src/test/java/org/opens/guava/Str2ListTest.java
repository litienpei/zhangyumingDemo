package org.opens.guava;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;

public class Str2ListTest {

    String str = "zhang,wang,li,liu";

    /**
     * 简介:
     *      使用Splitter对字符串进行切割生成list.
     */
    @Test
    public void test1() {
        List<String> list = Splitter.on(",").splitToList(str);
        /*
        输出:
            zhang
            wang
            li
            liu
        */
        list.stream().forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<String> list = Splitter.on("|").omitEmptyStrings().splitToList("Hello | world|||");
        /*
        输出:
                Hello空格
                空格world
        */
        list.stream().forEach(System.out::println);
        //输出: 2
        System.out.println(list.size());
        //把前后的空格去掉
        List<String> split = Splitter.on("|").omitEmptyStrings().trimResults().splitToList("Hello | world|||");
        /*
        输出:
                Hello
                world
        */
        split.stream().forEach(System.out::println);
        //输出: 2
        System.out.println(split.size());
    }

    /**
     * 简介:
     *      根据长度切割字符串
     */
    @Test
    public void test3() {
        List<String> result = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
        /*
            输出:
                aaaa
                bbbb
                cccc
                dddd
        */
        result.forEach(System.out::println);
        //输出: 4
        System.out.println(result.size());
    }

    /**
     * 简介:
     *      使用limit对切割次数做限制
     * 注意:
     *      最后一次会切完所有剩余字符
     */
    @Test
    public void test4() {
        List<String> result = Splitter.fixedLength(4).limit(3).splitToList("aaaabbbbccccdddd");
        /*
            输出:
                aaaa
                bbbb
                ccccdddd
        */
        result.forEach(System.out::println);
        //输出: 3
        System.out.println(result.size());
    }

}
