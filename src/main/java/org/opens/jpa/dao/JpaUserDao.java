package org.opens.jpa.dao;

import org.opens.jpa.pojo.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaUserDao extends JpaRepository<JpaUser, Integer>, JpaSpecificationExecutor<JpaUser> {

    /**
     * 简介:
     *      @query表示使用hql语句进行查询或者使用原生sql语句进行查询
     *      但是使用原生sql的话, 需要使nativeQuery = true.
     * @return 返回所有JpaUser对象
     */
    @Query(value = "select * from jpa_user", nativeQuery = true)
    public List<JpaUser> showAll();

    /**
     * 简介:
     *      使用@Query注解配置复杂查询, 也可以用原生sql
     * @param id 用户id
     * @param name 用户姓名
     * @return  返回满足要求的List
     */
    @Query(value = "select f from JpaUser f where f.id = :id and f.name = :name")
    public List<JpaUser> showUsersByIdAndName(
            @Param(value = "id") int id,
            @Param(value = "name") String name
    );

}
