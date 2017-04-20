package com.kyblabla.lwgms.base

import groovy.mock.interceptor.MockFor

/**
 * Created by hp on 2017/4/20.
 */
class MockTest {
    static class Person {
        String first, last
    }

    static class Family {
        Person father, mother
        def nameOfMother() { "$mother.first $mother.last" }
    }

}
