package org.opens.configuration.java.algorithms;

import org.junit.Test;

public class LowLevelAlgorithms {

    /**
     * 简介:
     *      该算法是用来快速交换两个数的值的.
     * 输出:
     *      a: 23, b:2999
     *      a: 2999, b:23
     */
    @Test
    public void test1() {
        int a = 23;
        int b = 2999;
        System.out.println("a: " + a + ", b:" + b);
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a: " + a + ", b:" + b);
    }

}
