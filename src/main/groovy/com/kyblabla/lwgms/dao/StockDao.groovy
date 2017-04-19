package com.kyblabla.lwgms.dao

import com.kyblabla.lwgms.ds.Storage

/**
 * Created by hp on 2017/4/19.
 */
class StockDao {

    def getByGoodCode(goodCode) {
        Storage.storage.find {
            e ->
                e.good?.code == goodCode
        }
    }

    def getById(id) {
        Storage.storage.find {
            e -> e.id == id
        }
    }

    def insert(stock) {
        def id = generateId()
        stock.id = generateId()
        Storage.storage.add(stock)
    }

    def update(stock) {
        def exitStock = getById(stock.id)
        if (exitStock != null) {
            exitStock = stock
        }
    }

    /**
     * 生成商品id
     * 规则：去最大id+1
     * @return
     */
    private def generateId() {
        def max = Storage.storage.max {
            e -> e.id
        }
        max ? max.id + 1 : 1
    }

    def search(param) {

    }

}
