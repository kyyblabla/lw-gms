package com.kyblabla.lwgms.service

import com.kyblabla.lwgms.exception.ServiceException
import com.kyblabla.lwgms.dao.StockDao
import com.kyblabla.lwgms.model.Good
import com.kyblabla.lwgms.model.Stock

/**
 * Created by hp on 2017/4/19.
 */
class StockService {

    StockDao stockDao

    Stock add(Good good, int amount) {
        Stock stock = stockDao.getByGoodCode(good.code)
        if (stock == null) {
            stock = new Stock()
            stock.good = good
            stock.amount = amount
            stock.assigned = 0
            stockDao.insert(stock)
        } else {
            stock.amount += amount
            stockDao.update(stock)
        }
    }

    /**
     * 出库分配
     * @param goodName
     */
    Stock deliveryAssign(String goodCode, int assigned) {
        Stock stock = stockDao.getByGoodCode(goodCode)
        if (stock == null) {
            ServiceException.exception("商品代码为${goodCode}的库存记录不存在")
        }
        if (stock.amount - stock.assigned - assigned < 0) {
            ServiceException.exception("商品代码为${goodCode}的库存不足。总量${stock.amount},已分配：${stock.assigned}")
        }
        stock.assigned += assigned
        stockDao.update(stock)
    }

    //出库完成
    Stock deliveryFinish(String goodCode, int assigned) {
        Stock stock = stockDao.getByGoodCode(goodCode)
        if (stock == null) {
            ServiceException.exception("商品代码为${goodCode}的库存记录不存在")
        }
        if (stock.assigned < assigned) {
            ServiceException.exception("商品代码为${goodCode}的分配数量不足。已分配：${stock.assigned}")
        }
        stock.assigned -= assigned
        stock.amount -= assigned
        stockDao.update(stock)
    }

    //调整库存
    Stock adjustStock(Good good, int amount) {
        Stock stock = stockDao.getByGoodCode(good.code)
        if (stock == null) {
            ServiceException.exception("商品代码为${good.code}的库存记录不存在")
        }
        if (stock.assigned > amount) {
            ServiceException.exception("商品代码为${good.code}调整总量小于已分配数量。已分配：${stock.assigned}")
        }
        stock.good = good
        stock.amount = amount
        stockDao.update(stock)
    }

    //查询
    def search(String goodName, String goodCode, Integer id) {
        stockDao.search([goodCode: goodCode, id: id, goodName: goodName])
    }


}
