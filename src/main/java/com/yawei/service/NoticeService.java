package com.yawei.service;

import com.yawei.mapper.NoticeMapper;
import com.yawei.pojo.Notice;
import com.yawei.util.ShiroUtil;

import javax.inject.Inject;
import javax.inject.Named;

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
}
