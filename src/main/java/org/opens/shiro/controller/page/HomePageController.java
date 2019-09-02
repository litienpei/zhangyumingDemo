package org.opens.shiro.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 简介:
 *      相当于给当前web应用添加一个首页面, 即浏览器发起http://localhost请求时会跳转的页面.
 */
@Controller
public class HomePageController {

    @RequestMapping(value = "/")
    public String toHome() {
        return "login";
    }

}
