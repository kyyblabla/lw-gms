package com.kyblabla.lwgms.service

import com.kyblabla.lwgms.model.Goods
import com.kyblabla.lwgms.model.Stock

/**
 * Created by hp on 2017/4/19.
 */
interface StockService {

    Stock addStock(Goods goods, int amount)

    Stock deliveryAssign(String goodsCode, int assigned)

    Stock deliveryFinish(String goodsCode, int assigned)

    Stock adjustStock(String goodsCode, int adjustAmount)

    List<Stock> search(String key)

    Stock getById(Integer id)

}
