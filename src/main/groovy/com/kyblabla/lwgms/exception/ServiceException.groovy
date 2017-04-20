package com.kyblabla.lwgms.exception

/**
 * Created by hp on 2017/4/19.
 */
class ServiceException extends RuntimeException {
    ServiceException(String msg) {
        super(msg )
    }

    static void exception(String msg) {
        throw new ServiceException(msg)
    }
}
