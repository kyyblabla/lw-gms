package com.kyblabla.lwgms.dao.impl

import com.kyblabla.lwgms.dao.StockDao
import com.kyblabla.lwgms.ds.Storage
import com.kyblabla.lwgms.model.Stock
import org.springframework.stereotype.Component

/**
 * Created by hp on 2017/4/20.
 */
@Component("StockDao")
class StockDaoImpl implements StockDao {

    Stock getByGoodsCode(String goodsCode) {
        Storage.storage.find {
            it.goods?.code == goodsCode
        }
    }

    Stock getById(Integer id) {
        Storage.storage.find {
            it.id == id
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
            it.id
        }
        max ? max.id + 1 : 1
    }

    List<Stock> find(Map param) {
        def findMethod = {
            (param.id && it.id == param.id) || (param.goodsCode && it.goods?.code == param.goodsCode) || (param.goodsName && it.goods?.name.indexOf(param.goodsName) != -1)
        }
        Storage.storage.findAll(findMethod)
    }

    List<Stock> find(String key) {
        def findMethod = {
            if(!key){
                true
            }
            key == it.id || key == it.goods?.code || it.goods?.color == key || (it.goods?.name.indexOf(key) != -1)
        }
        Storage.storage.findAll(findMethod)
    }
}
