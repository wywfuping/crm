package com.yawei.service;

import com.yawei.mapper.DocumentMapper;
import com.yawei.pojo.Document;
import com.yawei.util.ShiroUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Named
public class DocumentService {

    @Inject
    private DocumentMapper documentMapper;
    @Value("${filePath}")
    private String savePath;

    public List<Document> findDocumentByFid(Integer fid){
        return documentMapper.findByFid(fid);
    }

    /**
     * 新建文件夹
     * @return
     */
    public void saveDir(String name,Integer fid){
        Document document = new Document();

        document.setName(name);
        document.setCreateuser(ShiroUtil.getCurrentUserName());
        document.setFid(fid);
        document.setType(Document.TYPE_DIR);
        documentMapper.save(document);
    }

    /**
     * 上传文件
     * @param inputStream 输入流
     * @param originalFilename 文件真实名称
     * @param contentType 文件MIME类型
     * @param size 文件的大小
     * @param fid 父级Id
     */
    public void saveFile(InputStream inputStream, String originalFilename, String contentType, long size, Integer fid) {
        String suffix="";
        if(originalFilename.lastIndexOf(".")!=-1){
            suffix=originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName= UUID.randomUUID().toString() + suffix;

        try {
            FileOutputStream outputStream = new FileOutputStream(new File(savePath,newFileName));
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Document document = new Document();
        document.setName(originalFilename);
        document.setFilename(newFileName);
        document.setContexttype(contentType);
        document.setFid(fid);
        document.setCreateuser(ShiroUtil.getCurrentUserName());
        document.setSize(FileUtils.byteCountToDisplaySize(size));
        document.setType(Document.TYPE_DOC);
        documentMapper.save(document);
    }


    /**
     * 根据文件ID查找文件
     * @param id
     */
    public Document findDocumentById(Integer id){
        return documentMapper.findById(id);
    }
}
