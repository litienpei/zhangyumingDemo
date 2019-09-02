package org.opens.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaTest1 {



    /**
     * 使用lambda进行遍历list
     */
    @Test
    public void test2() {
        List<Integer> arr = new ArrayList<Integer>();
        arr.add(21);
        arr.add(12);
        arr.add(42);
        arr.add(25);
        arr.add(72);
        arr.forEach(x -> System.out.println(x));
        //这个相当于是展示数据时使用自定义过滤器
        arr.forEach(x -> {
            if(x > 30) {
                System.out.println(x);
            } else {
                System.out.println("太小了");
            }
        });
        //java8新特性: 方法引用
        arr.forEach(System.out::println);
    }

    /**
     * 简介:
     *      Java 8 API添加了一个新的抽象称为流Stream，可以让你以一种声明的方式处理数据。
     *      Stream使用一种类似用SQL语句从数据库查询数据的直观方式来提供一种对Java集合运算和表达的高阶抽象。
     *      Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。
     *      这种风格将要处理的元素集合看作一种流，流在管道中传输，并且可以在管道的节点上进行处理，比如筛选，排序，聚合等。
     *      元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果。
     * stream介绍:
     *      -什么是stream
     *          Stream（流）是一个来自数据源的元素队列并支持聚合操作
     *          元素：是特定类型的对象，形成一个队列。Java中的Stream并不会存储元素，而是按需计算。
     *          数据源 ：流的来源。可以是集合，数组，I/O channel，产生器generator等。
     *          聚合操作： 类似SQL语句一样的操作，比如filter, map, reduce, find,match, sorted等。
     *      -和以前的Collection操作不同，Stream操作还有两个基础的特征：
     *          Pipelining: 中间操作都会返回流对象本身。这样多个操作可以串联成一个管道，如同流式风格（fluent style）。这样做可以对操作进行优化，比如延迟执行(laziness)和短路( short-circuiting)。
     *          内部迭代：以前对集合遍历都是通过Iterator或者For-Each的方式,显式的在集合外部进行迭代，这叫做外部迭代。Stream提供了内部迭代的方式，通过访问者模式(Visitor)实现。
     *
     */
    @Test
    public void test3() {
        List<Integer> arr = new ArrayList<Integer>();
        arr.add(21);
        arr.add(12);
        arr.add(42);
        arr.add(30);
        arr.add(72);
        //filter()函数中需要使用一个lambda表达式产生一个boolean值, 为false的会被筛选掉
        List<Integer> newArr = arr.stream().filter((x) -> x >= 30).collect(Collectors.toList());
        newArr.forEach(System.out::println);
    }

    /**
     * 简介:
     *      使用jdk8+的stream来对list进行排序, 如果是自定义对象, sorted中可以传入比较器
     */
    @Test
    public void test4() {
        List<Integer> arr = Arrays.asList(123, 43, 555, 523, 23);
        //listByClassNo.stream().sorted(Comparator.comparing(Student::getScore).reversed()).collect(Collectors.toList()));
        List<Integer> arr2 = arr.stream().sorted().collect(Collectors.toList());
        arr2.forEach(System.out::println);
    }

}
