package com.kyblabla.lwgms.dao

import com.kyblabla.lwgms.ds.Storage
import com.kyblabla.lwgms.model.Stock

/**
 * Created by hp on 2017/4/19.
 */
interface StockDao {


    Stock getByGoodCode(String goodCode)

    Stock getById(Integer id)

    Stock insert(Stock stock)

    Stock update(Stock stock)

    List<Stock> find(Map param)

}
