package com.verdant.demo.spring.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/1.
 */
@Service
public class AuthTest {
//    public void put(User user) {
//        AuthorizeStorageUtil.put(user.getUserId(), user);
//    }
//
//    public static <T> T getUser(String type) throws ServiceException {
//        String prefix = CommonEnum.TOKEN.name() + "_" + type;
//        String tokenValue = WebUtils2.getCookieValue(prefix);
//        if (tokenValue == null) {
//            return null;
//        }
//        Class clazz = null;
//        if (type.equals(TokenConst.INFO)) {
//            clazz = User.class;
//        }
//        return (T) AuthorizeStorageUtil.get(prefix + ":" + tokenValue, clazz);
//    }

//    @Override
//    public User authentication(User user) throws ServiceException {
//        String password = user.getCustomerPassword();
//        user.setCustomerPassword(null);
//        user = this.get(user);
//        //用户不存在
//        if (user == null) {
//            throw new ApiException(ApiResultCode.USER_ACCOUNT_INVALID);
//        }
//        //密码不符
//        if (!user.getCustomerPassword().equals(password)) {
//            throw new ApiException(ApiResultCode.USER_PASSWORD_INVALID);
//        }
//
//        AuthToken auth = new AuthToken();
//        auth.setValue(user.getUserId() + "_" + SecurityUtils.sha512(String.valueOf(System.currentTimeMillis())));
//        user.setAuth(auth);
//        user.setCustomerPassword(null);
//        return user;
//    }
}
