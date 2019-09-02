package org.opens.shiro.controller;

import org.opens.shiro.dao.EwayUserDao;
import org.opens.shiro.pojo.EwayUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller(value = "ewayUserController")
@RequestMapping(value = "/message")
public class EwayUserController {

    @Autowired
    private EwayUserDao ewayUserDao;

    @GetMapping(value = "/ewayUser")
    @ResponseBody
    public EwayUser getEwayUserAndRoles(
            @RequestParam(value = "id", defaultValue = "-1") int id
    ) {
        return ewayUserDao.getUserAndRolesById(id);
    }

}
