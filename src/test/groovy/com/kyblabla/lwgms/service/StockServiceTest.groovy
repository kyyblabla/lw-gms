package com.kyblabla.lwgms.service

import com.kyblabla.lwgms.exception.ServiceException
import com.kyblabla.lwgms.ds.Storage
import com.kyblabla.lwgms.model.Goods
import com.kyblabla.lwgms.model.Stock
import groovy.test.GroovyAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by hp on 2017/4/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class StockServiceTest extends GroovyAssert {

    @Autowired
    StockService stockService

    @Before
    void before() {

        //初始化数据
        Storage.storage.add(new Stock(id: 1, amount: 100, assigned: 0, effectiveDate: new Date(), goods: new Goods(name: "鼠标", code: "SB", color: "黑色")))
        Storage.storage.add(new Stock(id: 2, amount: 100, assigned: 20, effectiveDate: new Date(), goods: new Goods(name: "键盘", code: "JP", color: "黑色")))
        Storage.storage.add(new Stock(id: 3, amount: 200, assigned: 0, effectiveDate: new Date(), goods: new Goods(name: "显示器", code: "XSQ", color: "银色")))
        Storage.storage.add(new Stock(id: 4, amount: 200, assigned: 20, effectiveDate: new Date(), goods: new Goods(name: "硬盘", code: "YP", color: "红色")))
        Storage.storage.add(new Stock(id: 5, amount: 100, assigned: 20, effectiveDate: new Date(), goods: new Goods(name: "主板", code: "ZB", color: "无")))


    }

    def static showList() {
        println "--统计--"
        Storage.storage.forEach {
            e ->
                println "${e.id}:${e.goods.name}:${e.amount}:${e.assigned}"
        }
        println "------\n"
    }

    @Test
    void add() throws Exception {
        //增加鼠标库存，目前库存又鼠标
        def mouse = new Goods(name: "鼠标", code: "SB", color: "黑色")
        def mouseStock = stockService.addStock(mouse, 100)
        assertEquals(mouseStock.amount, 200)
        showList()

        //增加U盘库存，目前库存不存在U盘
        def uDisk = new Goods(name: "U盘", code: "UP", color: "银色")
        def uDiskStock = stockService.addStock(uDisk, 20)
        assertEquals(uDiskStock.amount, 20)
        assertEquals(uDiskStock.assigned, 0)
        showList()


    }

    @Test
    void deliveryAssign() throws Exception {
        //键盘出库分配20
        def kbStock = stockService.deliveryAssign("JP", 20)
        assertEquals(kbStock.assigned, 40)
        assertEquals(kbStock.amount, 100)

        //分配量超过库存量，抛出异常
        shouldFail(ServiceException) {
            stockService.deliveryAssign("JP", 120)
        }

    }

    @Test
    void deliveryFinish() throws Exception {
        //主板出库分配
        def kbStock = stockService.deliveryFinish("ZB", 20)
        assertEquals(kbStock.assigned, 0)
        assertEquals(kbStock.amount, 80)
    }

    @Test
    void deliveryAssignAndDeliveryFinish() throws Exception {
        def kbStock = stockService.deliveryAssign("XSQ", 40)
        assertEquals(kbStock.assigned, 40)
        assertEquals(kbStock.amount, 200)
        kbStock = stockService.deliveryFinish("XSQ", 20)
        assertEquals(kbStock.assigned, 20)
        assertEquals(kbStock.amount, 180)
    }


    @Test
    void adjustStock() throws Exception {
        //键盘，库存200 已分配20
        def goodCode = "YP"
        //分配数大于调整后的库存，应当报错
        shouldFail(ServiceException, {
            stockService.adjustStock(goodCode, -190)
        })
        //调整库存为100
        def stock = stockService.adjustStock(goodCode, -100)
        assertEquals(stock.amount, 100)
    }

    @Test
    void search() throws Exception {
        //根据商品名称搜索
        def result = stockService.search("键")
        assertEquals(result[0].goods.name, "键盘")

        //根据商品编号搜索
        result = stockService.search("SB")
        assertEquals(result[0].goods.name, "鼠标")
    }

    @Test
    void getById() throws Exception {
        //根据库存id搜索
        def result = stockService.getById(1)
        assertEquals(result.goods.name, "鼠标")
    }

}
