package com.kyblabla.lwgms.service

import com.kyblabla.lwgms.model.Good
import com.kyblabla.lwgms.model.Stock

/**
 * Created by hp on 2017/4/19.
 */
interface StockService {

    Stock add(Good good, int amount)

    Stock deliveryAssign(String goodCode, int assigned)

    Stock deliveryFinish(String goodCode, int assigned)

    Stock adjustStock(Good good, int amount)

    List<Stock> search(String goodName, String goodCode, Integer id)

}
