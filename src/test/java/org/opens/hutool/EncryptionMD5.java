package org.opens.hutool;

import cn.hutool.crypto.digest.DigestUtil;
import org.junit.Test;

public class EncryptionMD5 {

    /**
     * 简介:
     *      使用md5算法对字符串加密.
     * 缺点:
     *      目前并没有发现加盐, 加密次数等操作, 加密程度不够.
     */
    @Test
    public void test1() {
        String passwd = "ewaytek";
        System.out.println(DigestUtil.md5Hex(passwd));
    }


}
