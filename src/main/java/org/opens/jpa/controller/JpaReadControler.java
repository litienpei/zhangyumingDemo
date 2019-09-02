package org.opens.jpa.controller;

import cn.hutool.core.util.StrUtil;
import org.opens.jpa.dao.JpaUserDao;
import org.opens.jpa.pojo.JpaUser;
import org.opens.mybatisplus.pojo.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController(value = "jpaReadControler")
@RequestMapping(value = "/jpa2")
public class JpaReadControler {

    @Autowired
    private JpaUserDao jpaUserDao;

    /**
     * 简介:
     *      -执行的sql
     *          select * from jpa_user
     * @return
     */
    @GetMapping(value = "/showAll")
    public ResultBean showAll() {
        return ResultBean.success(jpaUserDao.showAll());
    }

    /**
     * 简介:
     *      -执行的sql
     *          select jpauser0_.id as id1_0_, jpauser0_.name as name2_0_, jpauser0_.old as old3_0_ from jpa_user jpauser0_ where jpauser0_.id=? and jpauser0_.name=?
     *      -注意
     *          这个方法时自定义方法, 用hql语句编写的sql; jpa会把hql语句最终转换为标准sql.
     *
     * @param id
     * @param name
     * @return
     */
    @GetMapping(value = "/showUsersByIdAndName")
    public ResultBean showUsersByIdAndName(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "name", required = false) String name
    ) {
        return ResultBean.success(jpaUserDao.showUsersByIdAndName(id, name));
    }

    /**
     * 简介:
     *      -执行的sql
     *          select jpauser0_.id as id1_0_, jpauser0_.name as name2_0_, jpauser0_.old as old3_0_ from jpa_user jpauser0_
     *
     * @return 查询到的全部数据
     */
    @RequestMapping(value = "/findAll")
    public ResultBean findAll() {
        return ResultBean.success(jpaUserDao.findAll());
    }

    /**
     * 简介:
     *      -执行的sql
     *          select jpauser0_.id as id1_0_, jpauser0_.name as name2_0_, jpauser0_.old as old3_0_ from jpa_user jpauser0_ order by jpauser0_.id desc, jpauser0_.name desc
     *      -说明
     *          Direction是Sort类中的静态枚举, 可以直接使用, 用来限制最终sql的排序方向;
     *          1. Sort.Direction.DESC 反向排序(从大到小)
     *          2. Sort.Direction.ASC 正向排序
     * @return
     */
    @RequestMapping(value = "/findAllToSort")
    public ResultBean findAllToSort() {
        return ResultBean.success(jpaUserDao.findAll(new Sort(Sort.Direction.DESC, "id", "name")));
    }

    /**
     * 简介:
     *      -执行的sql:
     *          select jpauser0_.id as id1_0_, jpauser0_.name as name2_0_, jpauser0_.old as old3_0_ from jpa_user jpauser0_ where jpauser0_.id in (? , ? , ? , ? , ?)
     *      -说明:
     *          可以发现, 是将传入的list转换为了 in sql函数.
     * @param idArr
     * @return
     */
    @RequestMapping(value = "/findAllById")
    public ResultBean findAllById(
            @RequestParam(value = "idArr", required = true) Integer[] idArr
    ) {
        return ResultBean.success(jpaUserDao.findAllById(Arrays.asList(idArr)));
    }

    /**
     * 簡介:
     *      -执行的sql
     *          --场景1 http://localhost/jpa2/select2page?pageNumber=1&pageSize=3
     *              Hibernate: select jpauser0_.id as id1_0_, jpauser0_.name as name2_0_, jpauser0_.old as old3_0_ from jpa_user jpauser0_ where 1=1 order by jpauser0_.id desc limit ?
     *              Hibernate: select count(jpauser0_.id) as col_0_0_ from jpa_user jpauser0_ where 1=1
     *          --场景2 http://localhost/jpa2/select2page?pageNumber=1&pageSize=3&id=24
     *              Hibernate: select jpauser0_.id as id1_0_, jpauser0_.name as name2_0_, jpauser0_.old as old3_0_ from jpa_user jpauser0_ where jpauser0_.id=23 order by jpauser0_.id desc limit ?, ?
     *              Hibernate: select count(jpauser0_.id) as col_0_0_ from jpa_user jpauser0_ where jpauser0_.id=23
     *          --场景3 http://localhost/jpa2/select2page?pageNumber=2&pageSize=3&id=24&name=李
     *              Hibernate: select jpauser0_.id as id1_0_, jpauser0_.name as name2_0_, jpauser0_.old as old3_0_ from jpa_user jpauser0_ where jpauser0_.id=23 and (jpauser0_.name like ?) order by jpauser0_.id desc limit ?, ?
     *              Hibernate: select count(jpauser0_.id) as col_0_0_ from jpa_user jpauser0_ where jpauser0_.id=23 and (jpauser0_.name like ?)
     *          --总结
     *              可以发现:
     *                  1. CriteriaBuilder是一个条件构造器;
     *                  2. CriteriaQuery是一个排序构造器;
     *                  3. Pageable是一个分页构造器;
     *                  4. Page是最终返回对象格式的封装;
     *                  5. Root对象可以想象为对应的Entity, 可以用这个来制定条件构造器所指定的数据库列名称.
     *
     * @param pageNumber
     * @param pageSize
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "/select2page")
    public Page select2page(
            @RequestParam(value = "pageNumber", required = true) int pageNumber,
            @RequestParam(value = "pageSize", required = true) int pageSize,
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "name", required = false) String name

    ) {
        Pageable page = PageRequest.of(pageNumber - 1 < 0 ? 0 : pageNumber - 1, pageSize < 1 ? 5 : pageSize);
        Specification<JpaUser> specification = new Specification<JpaUser>() {
            @Override
            public Predicate toPredicate(Root<JpaUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(id != null) {
                    list.add(criteriaBuilder.equal(root.get("id"), id));
                }
                if(!StrUtil.isBlank(name)) {
                    list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
                }
                query.orderBy(criteriaBuilder.desc(root.get("id")));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<JpaUser> list = jpaUserDao.findAll(specification, page);
        return list;
    }



}
