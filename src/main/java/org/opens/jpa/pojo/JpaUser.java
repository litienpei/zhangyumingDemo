package org.opens.jpa.pojo;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "jpa_user")
@Data
public class JpaUser {

    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 用户年龄
     */
    @Column(name = "old")
    private Integer old;

}
