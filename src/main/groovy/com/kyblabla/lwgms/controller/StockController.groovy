package com.kyblabla.lwgms.controller

import com.kyblabla.lwgms.model.Stock
import com.kyblabla.lwgms.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * Created by hp on 2017/4/21.
 */
@CrossOrigin
@RequestMapping("/stock")
@RestController
class StockController {

    @Autowired
    StockService stockService

    @GetMapping("/{id}")
    Stock getById(@PathVariable("id") int id) {
        stockService.getById(id)
    }

    @GetMapping("/search")
    List<Stock> search(@RequestParam(value = "key", defaultValue = "") String key) {
        stockService.search(key)
    }

    @PostMapping
    Stock addStock(@RequestBody Stock stock) {
        stockService.addStock(stock)
    }

    @PutMapping("/assign")
    Stock deliveryAssign(@RequestParam("stockId") int stockId, @RequestParam("assigned") int assigned) {
        stockService.deliveryAssign(stockId, assigned)
    }

    @PutMapping("/finish")
    Stock deliveryFinish(@RequestParam("stockId") int stockId, @RequestParam("assigned") int assigned) {
        stockService.deliveryFinish(stockId, assigned)
    }

    @PutMapping("/adjust")
    Stock adjust(@RequestBody Stock stock) {
        stockService.adjustStock(stock)
    }

}
