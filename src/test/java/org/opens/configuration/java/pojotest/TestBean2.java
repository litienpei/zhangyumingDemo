package org.opens.configuration.java.pojotest;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestBean2 {

    private int id;

    private String name;
    /**
     * 简介:
     *
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date date;



}
