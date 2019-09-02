package org.opens.javaskill;

import org.junit.Test;

import java.util.Arrays;

public class Arr2List {

    /**
     * 输出:
     *      1
     *      3
     * 总结:
     *      int[]不能用Arrays.asList方法进行转换, Arrays.asList的参数必须是对象.
     */
    @Test
    public void test1() {
        int[] arr1 = new int[]{1, 2, 3};
        Integer[] arr2 = new Integer[]{1, 2, 3};
        System.out.println(Arrays.asList(arr1).size());
        System.out.println(Arrays.asList(arr2).size());
    }

    /**
     * 输出:
     *      报错java.lang.NullPointerException
     * 总结:
     *      Arrays.asList不能转换null
     */
    @Test
    public void test2() {
        Integer[] arr1 = null;
        System.out.println(Arrays.asList(arr1).size());
    }

}
