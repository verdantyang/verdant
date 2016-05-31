package com.verdant.jtools.common.web.model;

/**
 * @Author: verdant
 * @Desc: Response异常报文
 */
public class ResponseErrorMessage extends ResponseMessage {
    public ResponseErrorMessage(Exception e) {
        super(e);
    }
}
