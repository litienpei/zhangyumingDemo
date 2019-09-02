package org.opens.hutool;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.PageUtil;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.*;

public class PageUtilTest {

    /**
     * 简介:
     *      将页码转换为数据库需要的数据, 这里是转换为了mysql需要的.
     * 输出:
     *      [0, 10]
     *      [10, 20]
     *      [20, 30]
     * 注意:
     *      mybatis-plus的PaginationInterceptor插件已经很好的支持分页了, 使用这个的可能性不高.
     */
    @Test
    public void test1() {
        int[] startEnd1 = PageUtil.transToStartEnd(1, 10);
        int[] startEnd2 = PageUtil.transToStartEnd(2, 10);
        int[] startEnd3 = PageUtil.transToStartEnd(3, 10);
        System.out.println(Arrays.toString(startEnd1));
        System.out.println(Arrays.toString(startEnd2));
        System.out.println(Arrays.toString(startEnd3));
    }

    /**
     * 简介:
     *      将每页行数和数据总行数转换为页面需要的总页数
     * 输出:
     *      667
     *      1
     *      0
     */
    @Test
    public void test2() {
        int totalPage1 = PageUtil.totalPage(2000, 3);
        int totalPage2 = PageUtil.totalPage(1, 3);
        int totalPage3 = PageUtil.totalPage(0, 3);
        System.out.println(totalPage1);
        System.out.println(totalPage2);
        System.out.println(totalPage3);
    }

    /**
     * 简介:
     *      彩虹分页插件, 生成彩虹插件, 详细参考代码
     */
    @Test
    public void test3() {
        //参数意义分别为：当前页、总页数、每屏展示的页数
        int[] rainbow1 = PageUtil.rainbow(5, 20, 5);
        //结果：[3, 4, 5, 6, 7]
        System.out.println(Arrays.toString(rainbow1));

        int[] rainbow2 = PageUtil.rainbow(10, 20, 5);
        //结果：[8, 9, 10, 11, 12]
        System.out.println(Arrays.toString(rainbow2));

        //将结果转换为json
        int[] rainbow3 = PageUtil.rainbow(15, 20, 5);
        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("data", rainbow3);
        //结果: {"code":200,"data":[13,14,15,16,17]}
        System.out.println(JSON.toJSONString(res));
    }

    @Test
    public void test4() {
        int[] rainbow = PageUtil.rainbow(15, 20, 5);
        intArr2list(rainbow).forEach(System.out::println);
    }

    private  List<Integer> intArr2list(int[] arr) {
        List<Integer> res = new ArrayList<>();
        for (Integer i : arr) {
            res.add(i);
        }
        return res;
    }

    /**
     * 简介:
     *      遍历int[]的两种方式
     */
    @Test
    public void test5() {
        int[] arr = {1, 2, 3};
        for(int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }

    @Test
    public void test6() {
        Object[] newArray1 = ArrayUtil.newArray(Integer.class, 3);
        Object[] newArray2 = ArrayUtil.newArray(String.class, 3);
        Object[] newArray3 = ArrayUtil.newArray(Long.class, 3);
        System.out.println(newArray1.getClass().getName());
        System.out.println(newArray2.getClass().getName());
        System.out.println(newArray3.getClass().getName());
    }



}
