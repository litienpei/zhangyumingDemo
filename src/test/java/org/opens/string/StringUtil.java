package org.opens.string;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;

public class StringUtil {

    /**
     * 简介:
     *      trim函数可以去除字符串两边的空格.
     * 输出:
     *      3
     */
    @Test
    public void test1() {
        String str = "    ssd   ";
        System.out.println(str.trim().length());
    }

    /**
     * 简介:
     *      web应用中判断一个字符串是否为空, 一般判断以下三种情况:
     *          1. 判断这个字符串是否都是空格;
     *          2. 判断这个字符串是否为null,
     *          3. 判断这个字符串是"";
     *      而StrUtil.isBlank()满足这个要求, 可以去查看源码.
     */
    @Test
    public void test2() {
        String str1 = "    ssd   ";
        String str2 = "       ";
        String str3 = null;
        System.out.println(StrUtil.isBlank(str1));
        System.out.println(StrUtil.isBlank(str2));
        System.out.println(StrUtil.isBlank(str3));
    }

}
