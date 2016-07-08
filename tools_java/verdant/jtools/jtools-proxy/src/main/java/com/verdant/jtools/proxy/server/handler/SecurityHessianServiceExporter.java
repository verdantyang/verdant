package com.verdant.jtools.proxy.server.handler;

import com.commons.log.utils.LoggerContextUtil;
import com.commons.metadata.exception.ServiceException;
import com.commons.proxy.center.model.RequestTrace;
import com.commons.proxy.center.secure.RequestAuthorizeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianServiceExporter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (C), 2015-2016 中盈优创
 * SecurityHessianServiceExporter
 * Author: 龚健
 * Date: 2016/1/21
 */
public class SecurityHessianServiceExporter extends HessianServiceExporter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityHessianServiceExporter.class);

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoggerContextUtil.setTraceId(request.getHeader(RequestTrace.PROXY_TRACE_ID));
            LoggerContextUtil.setSequenceId(request.getHeader(RequestTrace.PROXY_SEQUENCE_ID));
            RequestAuthorizeUtil.valid(request);
        } catch (ServiceException e) {
            logger.error("request security authorize fail", e);
            if (request.getContentType() != null && request.getContentType().equals("x-application/hessian")) {
                throw new ServletException("request security authorize fail", e);
            } else {
                response.setStatus(401);
                response.getWriter().print("invalid credential.");
                response.getWriter().flush();
                return;
            }
        }
        super.handleRequest(request, response);
    }
}
