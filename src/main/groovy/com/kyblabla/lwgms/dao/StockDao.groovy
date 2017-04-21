package com.kyblabla.lwgms.dao

import com.kyblabla.lwgms.ds.Storage
import com.kyblabla.lwgms.model.Stock

/**
 * Created by hp on 2017/4/19.
 */
interface StockDao {

    Stock getByGoodsCode(String goodsCode)

    Stock getById(Integer id)

    Stock insert(Stock stock)

    Stock update(Stock stock)

    List<Stock> find(Map param)

    List<Stock> find(String key)

}
