package com.kyblabla.lwgms.controller

import com.kyblabla.lwgms.model.Stock
import com.kyblabla.lwgms.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by hp on 2017/4/21.
 */
@RequestMapping("/stock")
@RestController
class StockController {

    @Autowired
    StockService stockService

    @GetMapping("/{id}")
    Stock getById(@PathVariable("id") int id) {
        stockService.getById(id)
    }

    @PostMapping()
    Stock addStock(@RequestBody Stock stock) {
        stockService.addStock(stock)
    }

    @PostMapping("/delivery")
    Stock deliveryAssign(@RequestBody Stock stock) {
        stockService.addStock(stock)
    }

}
