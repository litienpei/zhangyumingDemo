package org.opens.mybatisplus.converter;

import org.opens.mybatisplus.pojo.UserDemo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 简介:
 *      这是一个自定义转换器, 自定义转换器需要继承Converter<String, UserDemo>, 并且指定了转换方式, 就是哪两个类型之间
 *      的转化, 然后重写方法convert定义转换规则.
 * 注意:
 *      之所以只需要将这个类注册进IOC容器中就可以被应用到, 是因为Spring boot的自动注册机制, 它会自动扫描 Converter接口
 *      的实现类, 然后注册到ConverterService类中, 然后spring mvc就知道怎么转换这两种数据类型了.
 *
 */
@Component(value = "string2User")
public class String2User implements Converter<String, UserDemo> {

    @Override
    public UserDemo convert(String source) {
        UserDemo user = new UserDemo();
        String[] arr = source.split("-");
        user.setId(Integer.valueOf(arr[0]));
        user.setName(arr[1]);
        user.setOld(Integer.valueOf(arr[2]));
        return user;
    }
}
