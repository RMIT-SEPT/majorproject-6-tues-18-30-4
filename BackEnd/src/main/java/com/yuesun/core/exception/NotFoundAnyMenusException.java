package com.yuesun.core.exception;

import com.yuesun.core.constant.HttpResultCode;

public class NotFoundAnyMenusException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpResultCode httpResultCode;

    public NotFoundAnyMenusException(){
        super(HttpResultCode.NOT_FOUND_ANY_MENUS.getCodeMsg());
        this.httpResultCode = HttpResultCode.NOT_FOUND_ANY_MENUS;
    }

    public HttpResultCode getHttpResultCode() {
        return httpResultCode;
    }
}
