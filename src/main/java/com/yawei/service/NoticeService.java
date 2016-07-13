package com.yawei.service;

import com.google.common.collect.Maps;
import com.yawei.mapper.NoticeMapper;
import com.yawei.pojo.Notice;
import com.yawei.util.ShiroUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Named
public class NoticeService {
    @Inject
    private NoticeMapper noticeMapper;

    @Value("${filePath}")
    private String imgSavePath;

    /**
     * 添加新公告
     *
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
     *
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
     *
     * @return
     */
    public Long findNoticeCount() {
        return noticeMapper.count();
    }

    /**
     * 根据公告id查找到公告的信息
     *
     * @param id
     * @return
     */
    public Notice findNoticeById(Integer id) {
        return noticeMapper.findNoticeById(id);
    }

    /**
     * 在线编辑器中对文件进行上传保存
     *
     * @param inputStream
     * @param originalFilename
     * @return
     */
    public String saveImg(InputStream inputStream, String originalFilename) throws IOException{
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        String newFileName = UUID.randomUUID().toString() + suffix;
        FileOutputStream outputStream = new FileOutputStream(new File(imgSavePath,newFileName));

        IOUtils.copy(inputStream,outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();

        return "/pre/"+newFileName;
    }
}
