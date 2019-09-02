package org.opens.hutool;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

public class StringTest {

    /**
     * 简介:
     *      替换字符串中的{}为指定字符串
     */
    @Test
    public void test1() {
        String strTemplate = "你好,{}";
        String str = StrUtil.format(strTemplate, "张三");
        System.out.println(str);
    }

    /**
     * 简介:
     *      使用sub方法进行截取字符串, 功能与subString相同, 但是比这个方法强大.
     * 功能:
     *      截取字符串
     */
    @Test
    public void test2() {
        String str = "abcdefghigklmnopqrstuvwxyz";
        //包含左边不包含右边, 并且可以像python那样支持反向索引: -1表示最后一个, -2表示倒数第二个
        System.out.println(StrUtil.sub(str, 3, -2));
        System.out.println(StrUtil.sub(str, 3, 4));
        //自动容错左值大于右值
        System.out.println(StrUtil.sub(str, 4, 3));
    }

    /**
     * 简介:
     *      -输出
     *          true true true
     *          true true false
     *      -isEmpty和isBlank的不同
     *          --官网解释
     *              这两个方法的区别是hasEmpty只判断是否为null或者空字符串（""），hasBlank则会把不可见字符也算做空，isEmpty和isBlank同理。
     *          --现象总结
     *              从现象上看就是isBlank在判断字符串是否为空时会忽略" "(空格字符串).
     *          --引申
     *              查看源码即可知道这两个方法的不同之处.
     *
     */
    @Test
    public void test3() {
        String str1 = "";
        String str2 = null;
        String str3 = "    ";
        System.out.println(StrUtil.isBlank(str1) + " " + StrUtil.isBlank(str2) + " " + StrUtil.isBlank(str3));
        System.out.println(StrUtil.isEmpty(str1) + " " + StrUtil.isEmpty(str2) + " " + StrUtil.isEmpty(str3));
    }

    /**
     * 简介:
     *      -功能
     *          判断指定字符串是否以"指定字符串"开头, 如果不是则添加.
     *      -输出
     *          苏州社保卡怎么办理?
     *          苏州社保卡怎么办理?
     *      -总结
     *          这个方法源码中封装了String类的startWith方法和concat方法.
     */
    @Test
    public void test4() {
        String str1 = "社保卡怎么办理?";
        String str2 = "苏州社保卡怎么办理?";
        System.out.println(StrUtil.addPrefixIfNot(str1, "苏州"));
        System.out.println(StrUtil.addPrefixIfNot(str2, "苏州"));
    }

    /**
     * 简介:
     *      -输出
     *              #这里是输出了空子字符串"";
     *          sds
     *          sds
     *      -功能
     *          --官方介绍
     *              清理空白字符
     *          --现象观察
     *              这个方法会删除指定字符串中的所有space, 不管位置在哪.
     */
    @Test
    public void test5() {
        String str1 = "  ";
        String str2 = " sds ";
        String str3 = " s   d    s ";
        System.out.println(StrUtil.cleanBlank(str1));
        System.out.println(StrUtil.cleanBlank(str2));
        System.out.println(StrUtil.cleanBlank(str3));
    }

    /**
     * 简介:
     *      -输出:
     *          true
     *          false
     *          true
     *      -功能:
     *          hasEmpty, hasBlank是用来验证多个字符串中是否有无效字符串的, 验证的规则hasEmpty是isEnpty, hasBlank是isBlank.
     *          等于说是批量验证.
     */
    @Test
    public void test6() {
        String str1 = "";
        String str2 = "1";
        String str3 = "  ";
        String str4 = null;
        System.out.println(StrUtil.hasEmpty(str4, str2));
        System.out.println(StrUtil.hasEmpty(str3, str2));
        System.out.println(StrUtil.hasEmpty(str3, str2, str1));
        System.out.println(StrUtil.hasBlank(str3, str2, str1));
    }

    /**
     * 简介:
     *      -输出
     *          为什么我的手机卡?
     *          我的手机卡?
     *          我的手机卡
     *      -功能
     *          给指定字符串去除掉指定内容.
     */
    @Test
    public void test7() {
        String str1 = "为什么我的手机卡?";
        System.out.println(str1);
        str1 = StrUtil.removeAll(str1, "为什么");
        System.out.println(str1);
        str1 = StrUtil.removeAll(str1, "?");
        System.out.println(str1);
    }

}
