package com.jewellery.constant;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


public enum ApiErrorCodes implements Error {

    SUCCESS(22221, "Success"),
    NOT_FOUND(22222, "not found"),
    ALREADY_EXIST(22223, "already exist"),
    INVALID_INPUT(22224, "Invalid request input"),
    USER_NOT_FOUND(22225, "User not found"),
    INVALID_USERNAME_OR_PASSWORD(22226, "Invalid username or password" ),

    ERROR_WHILE_SENDING_EMAIL(22232, "error while sending email"),
    INVALID_EMAIL_CODE(22233, "Invalid email code"),
    CANNOT_RESET_PASSWORD(22234, "cannot reset password"),

    CART_ITEM_NOT_FOUND(22231, "Cart item not found"),
    PRODUCT_NOT_FOUND(22235,"Product not found" ),
    WISHLIST_ITEM_NOT_FOUND(22236,"Wishlist item not found"),
    PRODUCT_ALREADY_EXIST(22237,"This item is already present in the wishlist."),
    PRODUCT_CATEGORY_NOT_FOUND(22238,"Product Category not found"),
    PRODUCT_TYPE_NOT_FOUND(22239,"Product type not found"),
    PRODUCT_TYPE_ALREADY_EXIST(22240,"Product Type Already exist"),
    USER_ALREADY_EXIST(22241,"user already exists "),
    INVALID_MOBILE_NUMBER(22242,"invalid mobile number "),
    ROLE_LIST_NOT_PRESENT(22243,"role list not present "),
    INVALID_EMAIL(22244,"invalid email"),
    SPECIFICATION_NOT_FOUND(22245,"Specification Not found"),
    ADDRESS_NOT_FOUND(22246,"Address Not found");


    private int errorCode;
    private String errorMessage;
    private  HttpStatus status;
    private  String message;
    private  LocalDateTime timestamp;


    ApiErrorCodes(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
