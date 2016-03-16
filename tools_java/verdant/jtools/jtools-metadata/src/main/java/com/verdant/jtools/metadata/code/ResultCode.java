package com.verdant.jtools.metadata.code;

/**
 * ResultCode Web接口返回信息
 * Author: verdant
 * Create: 2016/03/14
 */
public class ResultCode {

    public final static String SUCCESS = "system.operation.success";
    public final static String SUCCESS_ASYNC = "system.operation.success.async";
    public final static String OPERATE_TIMEOUT = "system.operation.timeout";
    public final static String ERROR_INNER = "system.error.inner";
    public final static String ERROR_PARAMETER_REQUIRED = "system.error.required.parameter";
    public final static String ERROR_SEARCH_CONDITION = "system.error.search.condition";
    public final static String ERROR_PARAMETER = "system.error.parameter";
    public final static String ERROR_SAVE = "system.error.save";
    public final static String ERROR_UPDATE = "system.error.update";
    public final static String ERROR_DELETE = "system.error.delete";
    public final static String ERROR_SEARCH = "system.error.search";
    public final static String ERROR_REMOTE_INVOKE_UNKNOWN_ = "system.error.remote.invoke.unknown";
    public final static String ERROR_REMOTE_INVOKE_CONNECT = "system.error.remote.invoke.connect";
    public final static String ERROR_HTTP_STATUS = "system.error.http.status";
    public final static String ERROR_AUTHORITY_VERIFY_TIMEOUT = "system.error.authority.verify.timeout";
    public final static String ERROR_AUTHORITY_VERIFY_INVALID = "system.error.authority.verify.fail";
    public final static String ERROR_AUTHORITY_PARAMETER_INVALID = "system.error.authority.parameter.fail";

}
