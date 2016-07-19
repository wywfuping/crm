package com.yawei.mapper;


import com.yawei.pojo.SalesLog;

import java.util.List;

public interface SalesLogMapper {

    void save(SalesLog salesLog);

    List<SalesLog> findBySalesId(Integer id);

    void del(List<SalesLog> salesLogList);
}
