package com.kyblabla.lwgms.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by hp on 2017/4/21.
 */
@RequestMapping("/stock")
@RestController
class StockController {

    @GetMapping("/hello")
    String hello() {
        return "hello"
    }

}
