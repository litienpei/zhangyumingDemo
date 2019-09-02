package org.opens.mybatisplus.pojo.result;

import lombok.Data;

@Data
public class ResultBean<T> {

    /**
     * 业务自定义状态码
     */
    private int code;

    /**
     * 请求状态描述，调试用
     */
    private String message;

    /**
     * 请求数据，对象或数组均可
     */
    private T data;

    public static <T> ResultBean<T> success(T data) {
        ResultBean<T> result = new ResultBean<T>();
        enum2result(result, ResponseDate.SUCCESS);
        result.data = data;
        return result;
    }

    /**
     * 简介:
     *      重载的一个好处就是当你曾经写过的方法已经在程序中大量使用后, 如果删除这个方法重新写就可能导致某些使用这个方法的地方
     *      出错, 而重载可以很好的解决这个问题.
     *      -重载的原因
     *          之所以对success方法进行重载, 是因为ResponseDate枚举类在失败和发生错误时的返回信息可以是固定的, 但是请求成
     *          功的操作有crud四种, 如果只提示请求成功不合适.
     *
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResultBean<T> success(T data, String message) {
        ResultBean<T> result = new ResultBean<T>();
        enum2result(result, ResponseDate.SUCCESS);
        result.data = data;
        result.message = message;
        return result;
    }

    public static ResultBean<String> fail() {
        ResultBean<String> result = new ResultBean<String>();
        enum2result(result, ResponseDate.FAIL);
        return result;
    }

    public static ResultBean<String> error() {
        ResultBean<String> result = new ResultBean<String>();
        enum2result(result, ResponseDate.ERROR);
        return result;
    }

    private static void enum2result(ResultBean<?> result, ResponseDate responseDate) {
        result.message = responseDate.toString();
        result.code = responseDate.parseCode();
    }



}
