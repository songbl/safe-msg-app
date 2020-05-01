package com.example.parttime.net.exception;
/**
 *  Create By  791243928@qq.com
 *
 *
 */

public class FalseException extends RuntimeException {
    private int code;

    public FalseException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
