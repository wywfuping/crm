package com.yawei.mapper;


import com.yawei.pojo.Sales;

import java.util.List;
import java.util.Map;

public interface SalesMapper {
    void save(Sales sales);
    Sales findById(Integer id);
    void delete(Integer id);
    Sales update(Sales sales);
    List<Sales> findAll();
    List<Sales> findByCustid(Integer id);
    List<Sales> findByUserid(Integer id);
    List<Sales> findSalesByParam(Map<String, Object> params);

    Long count();

    Long findCountByParam(Map<String, Object> params);

    List<Sales> findAllUser();

}
