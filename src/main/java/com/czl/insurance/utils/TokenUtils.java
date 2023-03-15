package com.czl.insurance.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.xml.security.algorithms.JCEMapper;

import java.util.Date;

public class TokenUtils {
    /**
     * 生成token
     * @return
     */
    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId)   //将user id 保存到token 里作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2))   //2小时后token过期
                .sign(Algorithm.HMAC256(sign));        //以 password 作为 token 的密钥
    }
}
