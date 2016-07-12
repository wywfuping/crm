package com.yawei.service;

import com.google.common.collect.Maps;
import com.yawei.mapper.NoticeMapper;
import com.yawei.pojo.Notice;
import com.yawei.util.ShiroUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class NoticeService {
    @Inject
    private NoticeMapper noticeMapper;


    /**
     * 添加新公告
     * @param notice
     */
    public void saveNotice(Notice notice) {
        notice.setUserid(ShiroUtil.getCurrentUserId());
        notice.setRealname(ShiroUtil.getCurrentUserName());
        noticeMapper.save(notice);
        //TODO 进行微信通知
    }

    /**
     * 查找所有的公告
     * @param start
     * @param length
     * @return
     */
    public List<Notice> findAllNotice(String start, String length) {
       /* Map<String,Object> param= Maps.newHashMap();
        param.put("userid",ShiroUtil.getCurrentUserId());
        param.put("start",start);
        param.put("length",length);*/

        return noticeMapper.findAllNotice();
    }

    /**
     * 获取公告的总数量
     * @return
     */
    public Long findNoticeCount() {
        return noticeMapper.count();
    }
}
