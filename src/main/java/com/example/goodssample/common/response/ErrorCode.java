package com.example.goodssample.common.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "400", HttpStatus.BAD_REQUEST.getReasonPhrase()),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "401", HttpStatus.UNAUTHORIZED.getReasonPhrase()),
    FORBIDDEN(HttpStatus.FORBIDDEN, "403", HttpStatus.FORBIDDEN.getReasonPhrase()),
    NOT_FOUND(HttpStatus.NOT_FOUND, "404", HttpStatus.NOT_FOUND.getReasonPhrase()),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "500", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),

    // BAD_REQUEST
    INVALID_PARAM(HttpStatus.BAD_REQUEST, "400001", "%s is invalid"),
    GOODS_NAME_DUP(HttpStatus.BAD_REQUEST, "400002", "Goods name is duplicated"),
    // UNATHORIZED
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, "401001", "Missing token"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "401002", "Invalid token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "401003", "Expired token"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "401004", "Invalid password"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "401005", "Invalid refresh token"),

    // FORBIDDEN
    NOT_PERMITTED(HttpStatus.FORBIDDEN, "403001", "not permitted"),

    // NOT FOUND
    GOODS_NOT_FOUND(HttpStatus.NOT_FOUND, "404001", "goods not found");


    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}