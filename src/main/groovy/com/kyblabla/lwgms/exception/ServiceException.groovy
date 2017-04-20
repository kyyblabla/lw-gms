package com.kyblabla.lwgms.exception

/**
 * Created by hp on 2017/4/19.
 */
class ServiceException extends RuntimeException {
    ServiceException(msg) {
        super(msg)
    }

    static void exception(msg) {
        throw new ServiceException(msg)
    }
}
