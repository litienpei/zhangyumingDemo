package org.opens.hutool;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;

public class IDUtilTest {

    /**
     * 生产随机id, 格式: 3ab0a60c-6cd8-4d6b-bc8e-090b89705d6c
     */
    @Test
    public void test1() {
        System.out.println(IdUtil.randomUUID());
    }

    /**
     * 生产随机id, 格式: b20ccbe944ea436ba6e6d5ce7e772053
     */
    @Test
    public void test2() {
        System.out.println(IdUtil.simpleUUID());
    }

    /**
     * 简介:
     *      ObjectId是MongoDB数据库的一种唯一ID生成策略，是UUID version1的变种，
     * 格式:
     *      5d4bcb4b9f5e01b77258036f
     */
    @Test
    public void test3() {
        System.out.println(IdUtil.objectId());
    }

    /**
     *  简介:
     *      分布式系统中，有一些需要使用全局唯一ID的场景，有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。Twitter的Snowflake 算法就是这种生成器。
     *  格式:
     *      1159362124657594368
     */
    @Test
    public void test4() {
        //参数1为终端ID
        //参数2为数据中心ID
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id = snowflake.nextId();
        System.out.println(id);
    }

}
