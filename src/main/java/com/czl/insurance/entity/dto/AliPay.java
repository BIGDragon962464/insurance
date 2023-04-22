package com.czl.insurance.entity.dto;

import lombok.Data;

@Data
/*public class AliPay {
    private String subject;
    private String traceNo;
    private String totalAmount;
}*/
public class AliPay {
    private String traceNo;
    private double totalAmount;
    private String subject;
    private String alipayTraceNo;
}
