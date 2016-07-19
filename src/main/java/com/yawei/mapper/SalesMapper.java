package com.yawei.mapper;


import com.yawei.pojo.Sales;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SalesMapper {
    void save(Sales sales);
    Sales findById(Integer id);
    void update(Sales sales);
    List<Sales> findAll();
    List<Sales> findByCustid(Integer custid);
    List<Sales> findSalesByParam(Map<String, Object> params);
    Long count();

    Long findCountByParam(Map<String, Object> params);

    void del(Integer id);

    Long findSalesSuccessCount(@Param("start") String start,@Param("end") String end,@Param("state") String state);

    Float findSalesPrice(@Param("start") String start,@Param("end") String end,@Param("state") String state);
}
