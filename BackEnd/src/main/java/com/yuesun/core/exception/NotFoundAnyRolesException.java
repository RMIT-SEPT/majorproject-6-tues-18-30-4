package com.yuesun.core.exception;

import com.yuesun.core.constant.HttpResultCode;

public class NotFoundAnyRolesException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpResultCode httpResultCode;

    public NotFoundAnyRolesException(){
        super(HttpResultCode.NOT_FOUND_ANY_ROLES.getCodeMsg());
        this.httpResultCode = HttpResultCode.NOT_FOUND_ANY_ROLES;
    }

    public HttpResultCode getHttpResultCode() {
        return httpResultCode;
    }
}
