package org.opens.hutool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RuntimeUtil;
import org.junit.Test;

import java.util.Date;

public class HutoolTest {

    private String timeConversionContainsSeconds = "yyyy-MM-dd HH:mm:ss";

    private String timeConversionDate = "yyyy-MM-dd";

    @Test
    public void test1() {
        String now = DateUtil.now();
        System.out.println(now);
    }

    @Test
    public void test2() {
        String time1 = "2017-05-06";
        String time2 = "2008-12-15";
        System.out.println(DateUtil.parse(time1));
        System.out.println(DateUtil.parse(time2, timeConversionDate).getClass().getName());//cn.hutool.core.date.DateTime

    }


    @Test
    public void test3() {
        Date date = new Date();
        System.out.println(DateUtil.format(date, "yyyy/MM/dd"));
    }

    /**
     * 使用hutool执行命令行命令
     */
    @Test
    public void test4() {
        String str = RuntimeUtil.execForStr("ipconfig");
        System.out.println(str);
    }

    @Test
    public void test123() {
        long currentTime = System.currentTimeMillis();
        String str = "2019-07-12 10:10:10";
        Date date = DateUtil.parse(str);
        System.out.println(date);
        System.out.println(DateUtil.offsetDay(date, 31));
        System.out.println(DateUtil.offsetMonth(date, 3));
        System.out.println(DateUtil.offsetMonth(date, -3));
        //昨天
        DateUtil.yesterday();
        //明天
        DateUtil.tomorrow();
        //上周
        DateUtil.lastWeek();
        //下周
        DateUtil.nextWeek();
        //上个月
        DateUtil.lastMonth();
        //下个月
        DateUtil.nextMonth();
    }

}
