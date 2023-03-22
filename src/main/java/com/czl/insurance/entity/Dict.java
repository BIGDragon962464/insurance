package com.czl.insurance.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("bysj_dict")
@Data
public class Dict {

    private String name;
    private String value;
    private String type;

}
