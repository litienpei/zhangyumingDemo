package org.opens.jpa.controller;

import org.opens.jpa.dao.JpaUserDao;
import org.opens.jpa.pojo.JpaUser;
import org.opens.jpa.vo.SuccessOrFail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController(value = "jpaUserController")
@RequestMapping(value = "/jpa")
public class JpaUserController {

    Logger logger = LoggerFactory.getLogger(JpaUserController.class);

    @Autowired
    private JpaUserDao jpaUserDao;

    @GetMapping(value = "/user")
    public JpaUser getOne(
            @RequestParam(value = "id", required = true, defaultValue = "-1") int id
    ) {
        logger.info("查询了: " + id);
        return jpaUserDao.findById(id).get();
    }

    @PostMapping(value = "/user")
    public SuccessOrFail insert(JpaUser jpaUser) {
        JpaUser jpaUserRes = jpaUserDao.save(jpaUser);
        return SuccessOrFail.success()
                .put("message", "添加成功")
                .put("user", jpaUserRes);
    }

    @DeleteMapping(value = "/user")
    public SuccessOrFail delete(
            @RequestParam(value = "id", required = true, defaultValue = "-1") int id
    ) {
        jpaUserDao.deleteById(id);
        return SuccessOrFail.success().put("message", "删除成功");
    }

    @PutMapping(value = "/user")
    public SuccessOrFail update(
            @RequestBody JpaUser jpaUser
    ) {
        return insert(jpaUser).put("message", "修改成功");
    }

    //jpa的批量删除并不好用, 还是自己手写吧

}
