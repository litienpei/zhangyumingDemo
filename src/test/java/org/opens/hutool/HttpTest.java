package org.opens.hutool;

import cn.hutool.http.HttpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpTest {

    //需要请求的url
    private String url = "http://localhost:80/business/findByName";

    /**
     * 发起post请求
     */
    @Test
    public void test1() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "生育缴费信息查询");
        //url为请求的地址, params为携带的参数信息
        String result = HttpUtil.post(url, params);
        System.out.println(result);
    }

    /**
     * 发起get请求
     */
    @Test
    public void test2() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "生育缴费信息查询");
        //url为请求的地址, params为携带的参数信息
        String result = HttpUtil.get(url, params);
        System.out.println(result);
    }

}
