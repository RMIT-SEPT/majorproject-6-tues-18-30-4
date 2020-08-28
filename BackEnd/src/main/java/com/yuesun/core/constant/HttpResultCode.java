package com.yuesun.core.constant;

public enum HttpResultCode {
    SYSTEM_ERROR(-1,"SYSTEM_ERROR"),
    SUCCESS(0,"SUCCESS"),
    VALIDATION_ERROR(1,"VALIDATION_ERROR"),
    TRANSFORM_ERROR(2,"TRANSFORM_ERROR"),
    NOT_FOUND_CURRENT_USER(3,"NOT_FOUND_CURRENT_USER"),
    NOT_FOUND_ANY_ROLES(4,"NOT_FOUND_ANY_ROLES"),
    NOT_FOUND_ANY_MENUS(5,"NOT_FOUND_ANY_MENUS"),

    UNKNOW_LOGINNAME_PASSWORD(10,"UNKNOW_LOGINNAME_PASSWORD"),
    INPUT_OLD_PASSWORD_ERROR(11,"INPUT_OLD_PASSWORD_ERROR"),
    ALREADY_LOGIN_ERROR(12,"ALREADY_LOGIN_ERROR"),
    RANDCODE_ERROR(13,"RANDCODE_ERROR")
    ;

    private int code;
    private String codeMsg;

    HttpResultCode(int code, String codeMsg) {
        this.code = code;
        this.codeMsg = codeMsg;
    }

    public int getCode() {
        return code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

    public static String findCodeMsgByCode(int code){
        for(HttpResultCode hrc:HttpResultCode.values()){
            if(equalsCode(hrc,code)){
                return hrc.getCodeMsg();
            }
        }
        throw new NullPointerException("no code");
    }

    private static boolean equalsCode(HttpResultCode hrc, int code){
        return hrc.getCode() == code;
    }
}
