package com.yuha.report.exception;

/**
 * @author yuhan
 * @date 06.06.2021 - 15:26
 * @purpose
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
