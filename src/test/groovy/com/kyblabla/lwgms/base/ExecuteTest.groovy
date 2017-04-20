package com.kyblabla.lwgms.base

import org.junit.Test

/**
 * Created by hp on 2017/4/20.
 */
class ExecuteTest {

    @Test
    public void test(){
        def process = "cmd /c dir".execute()
        println "${process.text}"
    }
}
