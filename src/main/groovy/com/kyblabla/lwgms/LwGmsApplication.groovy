package com.kyblabla.lwgms

import com.kyblabla.lwgms.ds.Storage
import com.kyblabla.lwgms.model.Goods
import com.kyblabla.lwgms.model.Stock
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * Created by hp on 2017/4/21.
 */
@SpringBootApplication
class LwGmsApplication {
    static void main(String[] args) {
        SpringApplication.run(LwGmsApplication.class, args)
        initDb()
    }

    private static void initDb() {
        Storage.storage.add(new Stock(id: 1, amount: 100, assigned: 0, effectiveDate: new Date(), goods: new Goods(name: "鼠标", code: "SB", color: "黑色")))
        Storage.storage.add(new Stock(id: 2, amount: 100, assigned: 20, effectiveDate: new Date(), goods: new Goods(name: "键盘", code: "JP", color: "黑色")))
        Storage.storage.add(new Stock(id: 3, amount: 200, assigned: 0, effectiveDate: new Date(), goods: new Goods(name: "显示器", code: "XSQ", color: "银色")))
        Storage.storage.add(new Stock(id: 4, amount: 200, assigned: 20, effectiveDate: new Date(), goods: new Goods(name: "硬盘", code: "YP", color: "红色")))
        Storage.storage.add(new Stock(id: 5, amount: 100, assigned: 20, effectiveDate: new Date(), goods: new Goods(name: "主板", code: "ZB", color: "无")))
    }
}
