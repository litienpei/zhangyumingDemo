package org.opens.mybatisplus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "dataModelController")
@RequestMapping(value = "/model")
public class DataModelController {

    /**
     * 简介:
     *      在浏览器发起请求: http://localhost/model/one?id=23, 就可以看到效果, 可以通过thymeleaf将数据展示到页面
     *      上.
     * @param id 实验参数
     * @param model 数据模型类型1
     * @return view视图
     */
    @GetMapping("/one")
    public String modelTestOne(
            @RequestParam(value = "id") int id,
            Model model
    ) {
        model.addAttribute("id", id);
        return "dataModelTest1";
    }

    /**
     * 简介:
     *      这是使用ModelAndView进行后台到页面传递值
     * @param id
     * @param modelAndView
     * @return
     */
    @GetMapping("/two")
    public ModelAndView modelTestTwo(
            @RequestParam(value = "id") int id,
            ModelAndView modelAndView
    ) {
        modelAndView.setViewName("dataModelTest1");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    /**
     * 简介:
     *      可以发现, ModelMap的使用和Model对象的使用时完全相同的, 但是可能因为它的名字, 并不常用
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/three")
    public String modelTestThree(
            @RequestParam(value = "id") int id,
            ModelMap modelMap
    ) {
        modelMap.put("id", id);
        return "dataModelTest1";
    }



}
