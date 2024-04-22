package com.atguigu.lease.common.context;

/**
 * ClassName:LoginUserContext
 * Package: IntelliJ IDEA
 * Description:
 *
 * @Author fsx
 * @Create 2024/4/22 20:15
 * @Version 1.0
 */
public class LoginUserContext {

   private static final ThreadLocal<LoginUser> userThreadLocal = new ThreadLocal<>();

   public static void setLoginUser(LoginUser loginUser) {
      userThreadLocal.set(loginUser);
   }

   public static LoginUser getLoginUser() {
      return userThreadLocal.get();
   }

   public static void clear() {
      userThreadLocal.remove();
   }
}
