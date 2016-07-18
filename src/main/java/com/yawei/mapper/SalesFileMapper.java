package com.yawei.mapper;


import com.yawei.pojo.SalesFile;

import java.util.List;

public interface SalesFileMapper {
    List<SalesFile> findBySalesid(Integer id);

    SalesFile findById(Integer id);

    void save(SalesFile salesFile);

    List<SalesFile> findBySalesId(Integer id);

    void del(List<SalesFile> salesFileList);
}
