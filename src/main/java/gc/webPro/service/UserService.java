package gc.webPro.service;

import gc.webPro.pojo.User;

/**
 * @Author: GC
 * @Description: 用户登录注册接口规范
 * @Version: 1.0
 */
public interface UserService {

    boolean isUsernameValid(String username);

    boolean registerUser(User user);

    boolean loginUser(String username,String password);
}
