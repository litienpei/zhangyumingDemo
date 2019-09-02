package org.opens;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opens.jpa.dao.JpaUserDao;
import org.opens.jpa.pojo.JpaUser;
import org.opens.mybatisplus.dao.UserMapper;
import org.opens.mybatisplus.pojo.UserDemo;
import org.opens.shiro.dao.EwayUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootTestApplicationTests {

    @Autowired
    private JpaUserDao jpaUserDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EwayUserDao ewayUserDao;

    @Test
    public void contextLoads() {
        List<JpaUser> arr = jpaUserDao.showAll();
        arr.forEach(System.out::println);
        System.out.println(JSON.toJSONString(arr));
    }

    @Test
    public void test2() {
        List<JpaUser> arr = jpaUserDao.showUsersByIdAndName(5, "%王%");
        arr.forEach(System.out::println);
        System.out.println(JSON.toJSONString(arr));
    }

    @Test
    public void test3() {
        Page<JpaUser> arr = getJpaUserList(1, "王", 1, 3);
        arr.forEach(System.out::println);
        System.out.println(JSON.toJSONString(arr));
    }

    private Page<JpaUser> getJpaUserList(int id, String name, int pageNumb, int pageSize) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = PageRequest.of(pageNumb, pageSize, sort);
        Specification<JpaUser> sn = (Root<JpaUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(id > 0) {
                predicates.add(criteriaBuilder.gt(root.get("id"), id));
            }
            if(!StringUtils.isEmpty(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<JpaUser> result = jpaUserDao.findAll(sn, pageable);
        return result;
    }

    @Test
    public void test4() {
        List<UserDemo> arr = userMapper.selectList(null);
        System.out.println(JSON.toJSONString(arr));
    }

    @Test
    public void test5() {
        UserDemo userDemo = userMapper.selectById(2);
        System.out.println(userDemo);
    }

    /**
     * 简介:
     *      shiro的"根据用户账号查询出角色名称Set"测试.
     * 结果:
     *      测试通过.
     */
    @Test
    public void test6() {
        Set<String> res = ewayUserDao.getRolesNameByAccount("test1");
        res.forEach(System.out::println);
    }

}
