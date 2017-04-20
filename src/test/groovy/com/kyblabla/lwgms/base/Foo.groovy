package com.kyblabla.lwgms.base

/**
 * Created by hp on 2017/4/20.
 */
class Foo {

    def invokeMethod(String name, Object arg){
        println "call me"
    }

    def doIt(){
        println "doit"
    }

    public static void main(String[] args) {

        def tool=new Foo()
        tool.doIt()
        tool.work()

    }

}
