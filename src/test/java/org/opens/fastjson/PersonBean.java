package org.opens.fastjson;

import com.alibaba.fastjson.annotation.JSONField;

public class PersonBean {

    private Integer id;

    /**
     * 说明:
     *      @JSONField注解可以定制化该字段生成json是的名称, 在这个例子中不使用该注解则该字段转换为json时名称为name, 加上该注解之后为sss
     */
    @JSONField(name = "sss")
    private String name;

    public PersonBean() {
    }

    public PersonBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
