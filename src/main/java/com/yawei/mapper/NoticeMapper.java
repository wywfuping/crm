package com.yawei.mapper;


import com.yawei.pojo.Notice;

import java.util.List;

public interface NoticeMapper {
    void save(Notice notice);

    List<Notice> findAllNotice();

    Long count();
}
