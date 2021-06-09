package com.yuhan.booking.exceptions;

/**
 * @author yuhan
 * @date 02.06.2021 - 17:48
 * @purpose
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
