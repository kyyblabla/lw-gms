package com.kyblabla.lwgms.service

import com.kyblabla.lwgms.model.Goods
import com.kyblabla.lwgms.model.Stock

/**
 * Created by hp on 2017/4/19.
 */
interface StockService {

    Stock addStock(Stock stock)

    Stock addStock(Goods goods, int amount)

    Stock deliveryAssign(Integer stockId, int assigned)

    Stock deliveryAssign(String goodsCode, int assigned)

    Stock deliveryFinish(String goodsCode, int assigned)

    Stock deliveryFinish(Integer stockId, int assigned)

    Stock adjustStock(Stock stock)

    Stock adjustStock(String goodsCode, int adjustAmount)

    Stock adjustStock(Integer stockId, int adjustAmount)

    List<Stock> search(String key)

    Stock getById(Integer id)

}
