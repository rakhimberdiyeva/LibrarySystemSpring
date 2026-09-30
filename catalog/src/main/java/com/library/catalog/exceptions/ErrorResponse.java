package com.library.catalog.exceptions;

public class ErrorResponse {
    private int code;
    private String message;
    private String timestamp;

    public ErrorResponse(int code, String message, String timestamp) {
        this.code = code;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }
    public String getTimestamp(){
        return timestamp;
    }
}

