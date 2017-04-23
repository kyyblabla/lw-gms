package com.kyblabla.lwgms.service.impl

import com.kyblabla.lwgms.dao.StockDao
import com.kyblabla.lwgms.exception.ServiceException
import com.kyblabla.lwgms.model.Goods
import com.kyblabla.lwgms.model.Stock
import com.kyblabla.lwgms.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by hp on 2017/4/19.
 */
@Service("StockService")
class StockServiceImpl implements StockService {

    @Autowired
    StockDao stockDao

    Stock addStock(Stock stock) {
        Stock exitStock = stockDao.getByGoodsCode(stock.goods.code)
        if (exitStock) {
            ServiceException.exception("商品代码为${goodsCode}的库存记录已存在")
        }
        stock.id = null
        stock.assigned = 0
        stockDao.insert(stock)
    }

    Stock addStock(Goods goods, int amount) {
        Stock stock = stockDao.getByGoodsCode(goods.code)
        if (stock == null) {
            stock = new Stock()
            stock.goods = goods
            stock.amount = amount
            stock.assigned = 0
            stockDao.insert(stock)
        } else {
            stock.amount += amount
            stockDao.update(stock)
        }
    }

    /**
     *
     * @param stockId
     * @param assigned
     * @return
     */
    Stock deliveryAssign(Integer stockId, int assigned) {
        Stock stock = stockDao.getById(stockId)
        if (stock == null) {
            ServiceException.exception("Id为${stockId}的库存记录不存在")
        }
        if (stock.amount - stock.assigned - assigned < 0) {
            ServiceException.exception("Id为${stockId}的库存不足。总量${stock.amount},已分配：${stock.assigned}")
        }
        stock.assigned += assigned
        stockDao.update(stock)
    }

    /**
     * 出库分配
     * @param goodsCode
     * @param assigned
     * @return
     */
    Stock deliveryAssign(String goodsCode, int assigned) {
        Stock stock = stockDao.getByGoodsCode(goodsCode)
        if (stock == null) {
            ServiceException.exception("商品代码为${goodsCode}的库存记录不存在")
        }
        if (stock.amount - stock.assigned - assigned < 0) {
            ServiceException.exception("商品代码为${goodsCode}的库存不足。总量${stock.amount},已分配：${stock.assigned}")
        }
        stock.assigned += assigned
        stockDao.update(stock)
    }

    //出库完成
    Stock deliveryFinish(String goodsCode, int assigned) {
        Stock stock = stockDao.getByGoodsCode(goodsCode)
        if (stock == null) {
            ServiceException.exception("商品代码为${goodsCode}的库存记录不存在")
        }
        if (stock.assigned < assigned) {
            ServiceException.exception("商品代码为${goodsCode}的分配数量不足。已分配：${stock.assigned}")
        }
        stock.assigned -= assigned
        stock.amount -= assigned
        stockDao.update(stock)
    }

    Stock deliveryFinish(Integer stockId, int assigned) {
        Stock stock = stockDao.getById(stockId)
        if (stock == null) {
            ServiceException.exception("Id为${stockId}的库存记录不存在")
        }
        if (stock.assigned < assigned) {
            ServiceException.exception("Id为${stockId}的分配数量不足。已分配：${stock.assigned}")
        }
        stock.assigned -= assigned
        stock.amount -= assigned
        stockDao.update(stock)
    }

    @Override
    Stock adjustStock(Stock stock) {
        Stock exitStock = stockDao.getById(stock.id)
        if (stock == null) {
            ServiceException.exception("Id为${stock.id}的库存记录不存在")
        }
        if (exitStock.assigned > stock.amount) {
            ServiceException.exception("Id为${exitStock.id}调整后总量小于已分配数量。已分配：${exitStock.assigned}")
        }
        exitStock.amount = stock.amount
        exitStock.effectiveDate = stock.effectiveDate
        exitStock.goods = stock.goods
        stockDao.update(exitStock)

    }

    //调整库存
    Stock adjustStock(String goodsCode, int adjustAmount) {
        Stock stock = stockDao.getByGoodsCode(goodsCode)
        if (stock == null) {
            ServiceException.exception("商品代码为${goodsCode}的库存记录不存在")
        }
        int finalAmount = stock.amount + adjustAmount
        if (finalAmount < 0) {
            ServiceException.exception("商品代码为${goodsCode}调整后总量小于0。当前总量：${stock.amount}")
        }
        if (stock.assigned > finalAmount) {
            ServiceException.exception("商品代码为${goodsCode}调整后总量小于已分配数量。已分配：${stock.assigned}")
        }
        stock.amount = finalAmount
        stockDao.update(stock)
    }

    @Override
    Stock adjustStock(Integer stockId, int adjustAmount) {
        Stock stock = stockDao.getById(stockId)
        if (stock == null) {
            ServiceException.exception("Id为${stockId}的库存记录不存在")
        }
        int finalAmount = stock.amount + adjustAmount
        if (finalAmount < 0) {
            ServiceException.exception("Id为${stockId}调整后总量小于0。当前总量：${stock.amount}")
        }
        if (stock.assigned > finalAmount) {
            ServiceException.exception("Id为${stockId}调整后总量小于已分配数量。已分配：${stock.assigned}")
        }
        stock.amount = finalAmount
        stockDao.update(stock)
    }

    List<Stock> search(String key) {
        stockDao.find(key)
    }

    Stock getById(Integer id) {
        stockDao.getById(id)
    }

}
