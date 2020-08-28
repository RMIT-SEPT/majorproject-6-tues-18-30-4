package com.yuesun.core.exception;

import com.yuesun.core.constant.HttpResultCode;


public class NotFoundCurrentUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpResultCode httpResultCode;

    public NotFoundCurrentUserException(){
        super(HttpResultCode.NOT_FOUND_CURRENT_USER.getCodeMsg());
        this.httpResultCode = HttpResultCode.NOT_FOUND_CURRENT_USER;
    }

    public HttpResultCode getHttpResultCode() {
        return httpResultCode;
    }
}
