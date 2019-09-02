package org.opens.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/lg")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @PostMapping(value = "/login")
    @ResponseBody
    public Map<String, Object> login(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password
    ) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        Map<String, Object> res = new HashMap<>();
        try {
            subject.login(token);
            res.put("code", 200);
            res.put("message", "登录成功");
            return res;
        } catch(AccountException e) {
            res.put("code", 300);
            res.put("message", e.getMessage());
            return res;
        } catch(IncorrectCredentialsException e1) {
            //这里有一个问题就是
            res.put("code", 300);
            res.put("message", "密码错误");
            return res;
        } catch(Exception e2) {
            res.put("code", 500);
            res.put("message", "服务器异常");
            logger.error(e2.getMessage());
            return res;
        }
    }

    @GetMapping(value = "/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "login";
    }

}
