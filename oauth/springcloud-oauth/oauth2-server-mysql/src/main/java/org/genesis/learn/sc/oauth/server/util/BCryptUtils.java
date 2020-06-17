package org.genesis.learn.sc.oauth.server.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author: KG
 * @description:
 * @date: Created in 5:40 下午 2020/6/17
 * @modified by:
 */

public class BCryptUtils {
    public static void main(String[] args) {
        String password = "123456";
        // 加密
        String pwt = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(pwt);
    }
}
