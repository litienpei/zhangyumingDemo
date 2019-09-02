package org.opens.guava;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class List2StrTest {

    private List<Integer> list = Arrays.asList(1, 34, 545, 23, 12);

    private List<Integer> list2 = Arrays.asList(1, 34, 545, 23, null, 12);

    /**
     * 简介
     *      这个方法就是将on中指定的字符插入到后面指定的集合中形成一个String.
     */
    @Test
    public void test1() {
        String join = Joiner.on("#").join(list);
        //輸出: 1#34#545#23#12
        System.out.println(join);
    }

    /**
     * 简介:
     *      使用skipNulls()方法跳过集合中的null.
     * 注意:
     *      如果不使用这个方法的话, 会报错java.lang.NullPointerException
     */
    @Test
    public void test2() {
        /*String join = Joiner.on("#").join(list2);//报错: java.lang.NullPointerException
        System.out.println(join);*/
        String join = Joiner.on("#").skipNulls().join(list2);
        //输出: 1#34#545#23#12
        System.out.println(join);
    }

    /**
     * 简介:
     *      不仅可以像test2中那样跳过null值, 还能直接使用指定的内容替换掉null.
     */
    @Test
    public void test3() {
        String join = Joiner.on("#").useForNull("0").join(list2);
        //输出: 1#34#545#23#0#12
        System.out.println(join);
    }

    /**
     * 简介:
     *      使用高效字符串进行转换
     * 注意:
     *      如果不跳过null还是会报错
     * @throws IOException
     */
    @Test
    public void test4() throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        Joiner.on("#").skipNulls().appendTo(stringBuffer, list2);
        System.out.println(stringBuffer.toString());
    }

    @Test
    public void test5() {

    }

}
