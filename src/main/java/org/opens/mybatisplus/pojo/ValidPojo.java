package org.opens.mybatisplus.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class ValidPojo implements Serializable {

    @NotNull(message = "id不能为空")
    private Long id;

    //@DateTimeFormat(pattern = "yyyy-MM-dd"), 需要注意的是不要在配置文件中指定spring.mvc.date-format之后再指定这个, 会冲突
    @Future(message = "需要一个将来的日期")
    @NotNull
    private Date date;

    @NotNull
    @DecimalMin(value = "0.1")          //最小值0.1
    @DecimalMax(value = "10000.0")      //最大值10000.0
    private Double aDouble;

    @NotNull
    @Min(value = 1, message = "最小值为1")
    @Max(value = 10000, message = "最大值为10000")
    private Integer aInteger;

    @Range(min = 1, max = 10000, message = "范围:1-10000")
    private Long aLong;

    @Email(message = "邮箱格式错误")
    private String email;

    @Size(min = 20, max = 30, message = "长度: 20-30")
    private String size;

}
