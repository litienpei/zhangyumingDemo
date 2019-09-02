package org.opens.shiro.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/page")
public class PublicController {

    @GetMapping(value = "/login")
    public String toLogin() {
        return "login";
    }

    @GetMapping(value = "/success")
    public String toSuccess() {
        return "success";
    }

    @GetMapping(value = "/403")
    public String toError() {
        return "403";
    }

}
