package org.opens.javaskill;

import org.junit.Test;

public class ConditionalJudgmentOptimization {

    /**
     * 简介:
     *      表格驱动法
     * 优点:
     *      该方法虽然实用性可能不强, 但是当遇到可以使用这个技巧的地方可以减少大量的代码
     */
    @Test
    public void test1() {
        int dateNumber = 23;
        System.out.println(getTheWeek(dateNumber));
        System.out.println(getTheWeek2(dateNumber));
    }

    /**
     * 说明:
     *      该算法是错误算法, 只是为了演示表格驱动法.
     * @param dateNumber 传入的日期, 例如: 31, 27
     * @return 这个日期对应的星期
     */
    private String getTheWeek(int dateNumber) {
        String today = "周六";
        //假设: 传入的日期对7取余可以得到星期
        switch( dateNumber % 7 ){
            case 0 :
                today = "周日";
                break;
            case 1 :
                today = "周一";
                break;
            case 2 :
                today = "周二";
                break;
            case 3 :
                today = "周三";
                break;
            case 4 :
                today = "周四";
                break;
            case 5 :
                today = "周五";
                break;
            default:
                today = "周六";
                break;
        }
        return today;
    }

    /**
     * 说明:
     *      表格驱动法就是将一个多支选择变为一个数组进行快速查找
     * @param dateNumber 传入的日期, 例如: 31, 27
     * @return 这个日期对应的星期
     */
    private String getTheWeek2(int dateNumber) {
        String[] weekday = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String today = weekday[dateNumber % 7];
        return today;

    }

}
