package com.imooc.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User extends Model{

    private Long id;

    private String name;

    private Integer age;

    private String email;

    private Long managerId;

    private LocalDate createTime;

    /*@TableField(exist = false)
    private String remark;*/
}
