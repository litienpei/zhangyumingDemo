package org.opens.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Map;

public class Map2StrTest {

    Map<String, String> stringStringMap = ImmutableMap.of("Guava", "Map", "Test", "HAH");

    /**
     * 简介:
     *      将map转换为字符串
     */
    @Test
    public void test1() {
        String join = Joiner.on(",").withKeyValueSeparator("=").join(stringStringMap);
        //输出: Guava=Map,Test=HAH
        System.out.println(join);
    }

}
