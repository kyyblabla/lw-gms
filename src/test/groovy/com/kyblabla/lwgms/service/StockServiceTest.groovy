package com.kyblabla.lwgms.service

import com.kyblabla.lwgms.dao.impl.StockDaoImpl
import com.kyblabla.lwgms.exception.ServiceException
import com.kyblabla.lwgms.dao.StockDao
import com.kyblabla.lwgms.ds.Storage
import com.kyblabla.lwgms.model.Good
import com.kyblabla.lwgms.model.Stock
import com.kyblabla.lwgms.service.impl.StockServiceImpl
import groovy.test.GroovyAssert
import org.junit.BeforeClass
import org.junit.Test

/**
 * Created by hp on 2017/4/19.
 */
class StockServiceTest extends GroovyAssert {


    def static StockService stockService

    @BeforeClass
    public static void before() {

        //初始化数据
        Storage.storage.add(new Stock(id: 1, amount: 100, assigned: 0, good: new Good(name: "鼠标", code: "SB", color: "黑色", effectiveDate: new Date())))
        Storage.storage.add(new Stock(id: 2, amount: 100, assigned: 20, good: new Good(name: "键盘", code: "JP", color: "黑色", effectiveDate: new Date())))
        Storage.storage.add(new Stock(id: 3, amount: 200, assigned: 0, good: new Good(name: "显示器", code: "XSQ", color: "银色", effectiveDate: new Date())))
        Storage.storage.add(new Stock(id: 4, amount: 200, assigned: 20, good: new Good(name: "硬盘", code: "YP", color: "红色", effectiveDate: new Date())))
        Storage.storage.add(new Stock(id: 5, amount: 100, assigned: 20, good: new Good(name: "主板", code: "ZB", color: "无", effectiveDate: new Date())))

        stockService = new StockServiceImpl(stockDao: new StockDaoImpl())

    }

    def static showList() {
        println "--统计--"
        Storage.storage.forEach {
            e ->
                println "${e.id}:${e.good.name}:${e.amount}:${e.assigned}"
        }
        println "------\n"
    }

    @Test
    public void add() throws Exception {
        //增加鼠标库存，目前库存又鼠标
        def mouse = new Good(name: "鼠标", code: "SB", color: "黑色", effectiveDate: new Date())
        def mouseStock = stockService.add(mouse, 100)
        assertEquals(mouseStock.amount, 200)
        showList()

        //增加U盘库存，目前库存不存在U盘
        def uDisk = new Good(name: "U盘", code: "UP", color: "银色", effectiveDate: new Date())
        def uDiskStock = stockService.add(uDisk, 20)
        assertEquals(uDiskStock.amount, 20)
        assertEquals(uDiskStock.assigned, 0)
        showList()


    }

    @Test
    public void deliveryAssign() throws Exception {
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
    public void deliveryFinish() throws Exception {
        //主板出库分配
        def kbStock = stockService.deliveryFinish("ZB", 20)
        assertEquals(kbStock.assigned, 0)
        assertEquals(kbStock.amount, 80)
    }

    @Test
    public void deliveryAssignAndDeliveryFinish() throws Exception {
        def kbStock = stockService.deliveryAssign("XSQ", 40)
        assertEquals(kbStock.assigned, 40)
        assertEquals(kbStock.amount, 200)
        kbStock = stockService.deliveryFinish("XSQ", 20)
        assertEquals(kbStock.assigned, 20)
        assertEquals(kbStock.amount, 180)
    }


    @Test
    public void adjustStock() throws Exception {
        //键盘，库存200 已分配20
        def ypGood = new Good(name: "硬盘", code: "YP", color: "红色", effectiveDate: new Date())

        //分配数大于调整后的库存，应当报错
        shouldFail(ServiceException, {
            stockService.adjustStock(ypGood, 10)
        })

        //调整库存为100
        def stock = stockService.adjustStock(ypGood, 100)
        assertEquals(stock.amount, 100)
    }

    @Test
    public void search() throws Exception {
        //根据商品名称搜索
        def result = stockService.search("键", null, null)
        assertEquals(result[0].good.name, "键盘")

        //根据商品编号搜索
        result = stockService.search(null, "SB", null)
        assertEquals(result[0].good.name, "鼠标")

        //根据库存id搜索
        result = stockService.search(null, null, 1)
        assertEquals(result[0].good.name, "鼠标")

    }

}
