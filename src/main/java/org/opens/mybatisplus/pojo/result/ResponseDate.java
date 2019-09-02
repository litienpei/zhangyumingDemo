package org.opens.mybatisplus.pojo.result;

import lombok.Getter;

@Getter
public enum ResponseDate {

    /**
     * web请求成功的枚举
     */
    SUCCESS(200, "请求成功"),
    /**
     * web请求失败的枚举
     */
    FAIL(300, "请求失败"),
    /**
     * web请求出错的枚举
     */
    ERROR(500, "发生异常"),
    OK();

    /**
     * 请求返回的状态码
     */
    private int code;

    /**
     * 请求返回的信息
     */
    private String message;

    /**
     * 简介:
     *      1. 可以将枚举类型看成提前创建在这个类中的静态对象, 可以直接获取.
     *      2. 在这个例子中, 如果将EnumDemo()这个无参构造屏蔽就会使OK这个枚举报错.
     *      3. 私有构造表示只能使用已经枚举出的枚举.
     */
    private ResponseDate() {
    }

    private ResponseDate(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * @return 对应的返回值message.
     */
    @Override
    public String toString() {
        return this.getMessage();
    }

    /**
     * @return 将返回信息翻译为状态码
     */
    public int parseCode() {
        return this.getCode();
    }

}
