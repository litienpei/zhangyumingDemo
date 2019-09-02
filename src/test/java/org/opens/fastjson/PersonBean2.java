package org.opens.fastjson;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明:
 *      与PersonBean区别就是多了一个List<T>字段
 * @param <T>
 */
public class PersonBean2<T> {

    private Integer id;

    private String name;

    private List<T> list = new ArrayList<>();

    public PersonBean2(Integer id, String name, List<T> list) {
        this.id = id;
        this.name = name;
        this.list = list;
    }

    public PersonBean2() {
    }

    public PersonBean2<T> add(T value) {
        this.list.add(value);
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public PersonBean2(Integer id, String name) {
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
        return "PersonBean2{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", list=" + list +
                '}';
    }

}
