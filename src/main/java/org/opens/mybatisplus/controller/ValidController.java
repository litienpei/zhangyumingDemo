package org.opens.mybatisplus.controller;

import org.opens.mybatisplus.pojo.ValidPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简介:
 *      这个类中只有一个方法, 这个方法用来描述怎么验证数据是否规范.
 */
@Controller(value = "validController")
@RequestMapping(value = "/valid")
public class ValidController {

    Logger logger = LoggerFactory.getLogger(ValidController.class);

    /**
     * 简介:
     *      @Valid注解表示参数验证, 如果给参数标注了这个注解的话, 就表示需要对这个参数进行校验.
     *      -校验原理
     *          如果校验的参数不满足要求, spring会自动将校验信息放到Errors对象中, 所以就需要在参数列表中放置一个这个参
     *          数, 然后遍历这个Errors的实例, 我们根据业务手动遍历这个集合, 返回对应的数据.
     *
     * @param validPojo
     * @param errors
     * @return
     */
    @PostMapping(value = "/value")
    @ResponseBody
    public Map<String, Object> validParams(
            @Valid ValidPojo validPojo,
            Errors errors
    ) {
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> errorMessage = new HashMap<>();
        List<ObjectError> oes = errors.getAllErrors();
        oes.forEach((oe) -> {
            String key = null;
            String value = null;
            if(oe instanceof FieldError) {
                FieldError fe = (FieldError) oe;
                //获取数据验证错误的字段名称
                key = fe.getField() + "ErrorMessage";
            } else {
                //获取对象名称
                key = oe.getObjectName();
            }
            value = oe.getDefaultMessage();
            errorMessage.put(key, value);
        });
        res.put("code", 200);
        res.put("message", "success");
        if(errorMessage.size() != 0) {
            res.put("code", 300);
            res.put("message", errorMessage);
        }
        return res;
    }

}
