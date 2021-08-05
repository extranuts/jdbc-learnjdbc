package com.dmdev.jdbc.starter.exceptions;

public class DaoException extends RuntimeException{

    public DaoException(Throwable throwable) {
        super(throwable);
    }
}
