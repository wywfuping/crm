package com.yawei.mapper;

import com.yawei.pojo.Document;
import java.util.List;

public interface DocumentMapper {
    void save(Document document);
    Document findById(Integer id);
    void delete(Integer id);
    List<Document> findByFid(Integer fid);
}
