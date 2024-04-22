package com.atguigu.lease.common.constant;

/**
 * ClassName:RedisConstant
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/21 23:38
 * @Version 1.0
 */
public class RedisConstant {
   public static final String ADMIN_LOGIN_PREFIX = "admin:login:";
   public static final Integer ADMIN_LOGIN_CAPTCHA_TTL_SEC = 60;
   public static final String APP_LOGIN_PREFIX = "app:login:";
   public static final Integer APP_LOGIN_CODE_RESEND_TIME_SEC = 60;
   public static final Integer APP_LOGIN_CODE_TTL_SEC = 60 * 10;
}