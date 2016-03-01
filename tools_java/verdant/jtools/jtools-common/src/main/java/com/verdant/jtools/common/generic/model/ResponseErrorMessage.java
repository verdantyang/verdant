package com.verdant.jtools.common.generic.model;

/**
 * Created by hadoop on 2015/7/27.
 */
public class ResponseErrorMessage extends ResponseMessage {
    public ResponseErrorMessage(Exception e) {
        super(e);
    }
}
