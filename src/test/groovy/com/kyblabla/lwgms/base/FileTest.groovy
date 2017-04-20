package com.kyblabla.lwgms.base

import groovy.test.GroovyAssert
import org.junit.Test

/**
 * Created by hp on 2017/4/20.
 */
class FileTest extends GroovyAssert{

    @Test
    public void readLine(){
        new File("D:/test.txt").eachLine {
            line ->
                println line
        }
    }

}
