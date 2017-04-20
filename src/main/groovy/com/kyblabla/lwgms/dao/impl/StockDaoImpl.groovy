package com.kyblabla.lwgms.dao.impl

import com.kyblabla.lwgms.dao.StockDao
import com.kyblabla.lwgms.ds.Storage
import com.kyblabla.lwgms.model.Stock

/**
 * Created by hp on 2017/4/20.
 */
class StockDaoImpl implements StockDao {

    Stock getByGoodCode(String goodCode) {
        Storage.storage.find {
            e ->
                e.good?.code == goodCode
        }
    }

    Stock getById(Integer id) {
        Storage.storage.find {
            e -> e.id == id
        }
    }

    Stock insert(Stock stock) {
        stock.id = generateId()
        Storage.storage.add(stock)
        stock
    }

    Stock update(Stock stock) {
        def exitStock = getById(stock.id)
        if (exitStock != null) {
            exitStock = stock
        }
        exitStock
    }

    /**
     * 生成商品id
     * 规则：取最大id+1
     * @return
     */
    private Integer generateId() {
        def max = Storage.storage.max {
            e -> e.id
        }
        max ? max.id + 1 : 1
    }

    List<Stock> find(Map param) {
        def findMethod = {
            e ->
                (param.id && e.id == param.id) || (param.goodCode && e.good?.code == param.goodCode) || (param.goodName && e.good?.name.indexOf(param.goodName) != -1)
        }
        Storage.storage.findAll(findMethod)
    }

}
