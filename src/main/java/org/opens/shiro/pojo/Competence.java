package org.opens.shiro.pojo;

import java.io.Serializable;

/**
 * (Competence)实体类
 *
 * @author makejava
 * @since 2019-08-21 18:21:02
 */
public class Competence implements Serializable {
    private static final long serialVersionUID = -41604890674133971L;
    
    private Integer id;
    
    private String url;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}