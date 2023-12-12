package com.company;

public class EnterNumException extends Exception{
    public EnterNumException() {
        System.out.println("Alarm! Your num is not correct");
        System.exit(1);
    }

    public EnterNumException(String message) {
        super(message);
    }

    public EnterNumException(String message, Throwable cause) {
        super(message, cause);
    }

    public EnterNumException(Throwable cause) {
        super(cause);
    }

    public EnterNumException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public void exx(){
        System.out.println("123");
    }


}
