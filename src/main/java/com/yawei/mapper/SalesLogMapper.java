package com.yawei.mapper;


import com.yawei.pojo.SalesLog;

import java.util.List;

public interface SalesLogMapper {

    List<SalesLog> findBySalesid(Integer id);

    void save(SalesLog salesLog);

    List<SalesLog> findBySalesId(Integer id);

    void del(List<SalesLog> salesLogList);
}
