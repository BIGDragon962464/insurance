package com.czl.insurance.common;

public enum VaildationEnum {
    LOGIN(1),FORGET_PASS(2);
    private Integer code;

    public Integer getCode() {
        return code;
    }

    VaildationEnum(Integer code) {
        this.code = code;
    }
}
