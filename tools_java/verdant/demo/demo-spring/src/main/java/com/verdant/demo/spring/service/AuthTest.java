package com.verdant.demo.spring.service;

import com.verdant.demo.spring.auth.AuthorizeStorageUtil;
import com.verdant.demo.spring.auth.model.CommonEnum;
import com.verdant.jtools.common.web.utils.WebUtils2;
import com.verdant.jtools.metadata.exception.ServiceException;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/1.
 */
@Service
public class AuthTest {
    public void put() {
        AuthorizeStorageUtil.put(user.getAuth(), user);
    }

    public static <T> T getUser(String type) throws ServiceException {
        String prefix = CommonEnum.TOKEN.name() + "_" + type;
        String tokenValue = WebUtils2.getCookieValue(prefix);
        if (tokenValue == null) {
            return null;
        }
        Class clazz = null;
        if (type.equals(TokenConst.INFO)) {
            clazz = InfoCustomer.class;
        }
        return (T) AuthorizeStorageUtil.get(prefix + ":" + tokenValue, clazz);
    }
}
