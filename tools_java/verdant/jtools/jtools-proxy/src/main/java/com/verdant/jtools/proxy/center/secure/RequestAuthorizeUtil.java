package com.verdant.jtools.proxy.center.secure;

import com.commons.common.utils.Identities;
import com.commons.common.utils.PropUtil;
import com.commons.common.utils.SecurityUtils;
import com.commons.common.utils.StringUtil;
import com.commons.metadata.code.ResultCode;
import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.RequestAuthorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (C), 2015-2016 中盈优创
 * RequestAuthorizeUtil
 * Author: 龚健
 * Date: 2016/1/21
 */
public class RequestAuthorizeUtil {

    private static final Logger logger = LoggerFactory.getLogger(RequestAuthorizeUtil.class);

    private static Long expires = null;

    static {
        String timeout = PropUtil.get("proxy.auth.timeout");
        if (timeout == null) {
            expires = 1000l * 60l * 5l;
        } else {
            expires = Long.valueOf(timeout);
        }
    }

    /**
     * 生成签名
     *
     * @return
     */
    public static RequestAuthorize generate() {
        String requestId = Identities.getRandomGUID(true);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String prefix = extractSignPrefix(requestId, timestamp);
        String signature = SecurityUtils.md5(prefix + timestamp).toUpperCase();
        logger.info("SEND request id {},signature {},timestamp {}", requestId, signature, timestamp);
        return new RequestAuthorize(requestId, signature, timestamp);
    }

    /**
     * 按req校验
     *
     * @param request
     * @throws ServiceException
     */
    public static void valid(HttpServletRequest request) throws ServiceException {
        valid(request.getHeader(RequestAuthorize.PROXY_ID), request.getHeader(RequestAuthorize.PROXY_SIGN), request.getHeader(RequestAuthorize.PROXY_TIME));
    }

    public static void valid(String requestId, String signature, String timestamp) throws ServiceException {
        if (StringUtil.isEmpty(requestId) || StringUtil.isEmpty(signature) || StringUtil.isEmpty(timestamp)) {
            throw new ServiceException(ResultCode.ERROR_AUTHORITY_PARAMETER_INVALID);
        }

        long time = System.currentTimeMillis() - Long.valueOf(timestamp);
        if (time > expires) {
            throw new ServiceException(ResultCode.ERROR_AUTHORITY_VERIFY_TIMEOUT);
        }

        String prefix = extractSignPrefix(requestId, timestamp);
        if (!SecurityUtils.md5(prefix + timestamp).toUpperCase().equals(signature)) {
            throw new ServiceException(ResultCode.ERROR_AUTHORITY_VERIFY_INVALID);
        }
    }

    /**
     * requestId 为GUID按-分割，请求会带当前时间戳，按时间戳最后一位 截取GUID分割的数组某段做 md5 参与计算的前缀
     *
     * @param requestId
     * @param timestamp
     * @return
     */
    public static String extractSignPrefix(String requestId, String timestamp) {
        int seg = Integer.parseInt(timestamp.substring(timestamp.length() - 1));
        String[] idSeg = requestId.split("-");
        seg = seg % idSeg.length;
        return idSeg[seg];
    }

}
