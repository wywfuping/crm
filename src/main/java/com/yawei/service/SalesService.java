package com.yawei.service;

import com.yawei.mapper.*;
import com.yawei.pojo.*;
import com.yawei.util.ShiroUtil;
import com.yawei.util.Strings;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Named
public class SalesService {
    @Inject
    private SalesMapper salesMapper;
    @Inject
    private SalesLogMapper salesLogMapper;
    @Inject
    private SalesFileMapper salesFileMapper;
    @Inject
    private UserMapper userMapper;
    @Inject
    private CustomerMapper customerMapper;
    @Value("${filePath}")
    private String savePath;

    /**
     * 查找所有销售机会
     *
     * @return
     */
    public List<Sales> findAllSales() {
        return salesMapper.findAll();
    }

    /**
     * 根据参数获取销售信息
     *
     * @param params
     * @return
     */
    public List<Sales> findSalesByParam(Map<String, Object> params) {
        if (ShiroUtil.isEmployee()) {
            params.put("userid", ShiroUtil.getCurrentUserId());
        }
        return salesMapper.findSalesByParam(params);
    }

    /**
     * 获取销售机会的总数量
     *
     * @return
     */
    public Long count() {
        return salesMapper.count();
    }

    /**
     * 根据参数筛选出销售机会的数量
     *
     * @param params
     * @return
     */
    public Long findCountByParam(Map<String, Object> params) {
        return salesMapper.findCountByParam(params);
    }

    /**
     * 新增销售机会
     *
     * @param sales
     */
    public void saveSales(Sales sales) {

        Integer custid = sales.getCustid();
        Customer customer = customerMapper.findById(custid);

        sales.setCustname(customer.getName());

        sales.setUserid(ShiroUtil.getCurrentUserId());
        sales.setUsername(ShiroUtil.getCurrentUserName());
        salesMapper.save(sales);

        //自动创建日志记录
        SalesLog salesLog = new SalesLog();
        salesLog.setType(SalesLog.LOG_TYPE_AUTO);
        salesLog.setContext(ShiroUtil.getCurrentUserName() + "创建了该机会");
        salesLog.setSalesid(sales.getId());
        salesLogMapper.save(salesLog);
    }

    /**
     * 查找所有的用户
     *
     * @return
     */
    public List<User> findAllUser() {
        return userMapper.findAll();
    }

    /**
     * 通过id查找销售机会
     *
     * @param id
     * @return
     */
    public Sales findSalesById(Integer id) {
        return salesMapper.findById(id);
    }

    /**
     * 查找所有的客户
     *
     * @return
     */
    public List<Customer> findAllCustomer() {
        return customerMapper.findAll();
    }

    /**
     * 修改销售机会信息
     *
     * @param sales
     */
    public void editSales(Sales sales) {
        Integer salesid = sales.getId();
        sales = salesMapper.findById(salesid);

        salesMapper.update(sales);
    }

    /**
     * 根据当前机会的id查找机会的日志记录
     *
     * @param id
     * @return
     */
    public List<SalesLog> findSalesLogBySalesid(Integer id) {
        return salesLogMapper.findBySalesid(id);
    }

    /**
     * 根据当前机会的id查找机会的文件列表
     *
     * @param id
     * @return
     */
    public List<SalesFile> findSalesFileBySalesid(Integer id) {
        return salesFileMapper.findBySalesid(id);
    }


    /**
     * 上传保存文件
     *
     * @param inputStream
     * @param originalFilename
     * @param contentType
     * @param size
     * @param salesid
     */
    @Transactional
    public void uploadFile(InputStream inputStream, String originalFilename, String contentType, long size, Integer salesid) {
        String newFileName = UUID.randomUUID().toString();
        if (originalFilename.lastIndexOf(".") != -1) {
            newFileName += originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        try {

            FileOutputStream outputStream = new FileOutputStream(new File(savePath, newFileName));
            IOUtils.copy(inputStream, outputStream);

            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        SalesFile salesFile = new SalesFile();
        salesFile.setSalesid(salesid);
        salesFile.setContenttype(contentType);
        salesFile.setFilename(newFileName);
        salesFile.setName(originalFilename);
        salesFile.setSize(size);

        salesFileMapper.save(salesFile);

    }

    /**
     * 修改销售进度
     *
     * @param id
     * @param progress
     */
    @Transactional
    public void editProgress(Integer id, String progress) {

        Sales sales = salesMapper.findById(id);
        sales.setProgress(progress);
        sales.setLasttime(DateTime.now().toString());

        if ("完成交易".equals(progress)) {
            sales.setSuccesstime(DateTime.now().toString());
        }
        salesMapper.update(sales);

        //添加日志记录
        SalesLog salesLog = new SalesLog();
        salesLog.setType(SalesLog.LOG_TYPE_AUTO);
        salesLog.setContext(ShiroUtil.getCurrentUserName() + "更改进度为" + progress);
        salesLog.setSalesid(sales.getId());
        salesLogMapper.save(salesLog);
    }

    /**
     * 新增销售日志
     *
     * @param salesLog
     */
    @Transactional
    public void saveLog(SalesLog salesLog) {
        salesLog.setType(SalesLog.LOG_TYPE_AUTO);
        salesLogMapper.save(salesLog);

        Sales sales = salesMapper.findById(salesLog.getSalesid());
        sales.setLasttime(DateTime.now().toString("YYYY-MM-dd"));
        salesMapper.update(sales);
    }

    /**
     * 根据主键获取文件
     *
     * @param id
     * @return
     */
    public SalesFile findSalesFileById(Integer id) {
        return salesFileMapper.findById(id);
    }

    /**
     * 根据主键删除销售机会
     *
     * @param id
     */
    @Transactional
    public void delSales(Integer id) {
        Sales sales = salesMapper.findById(id);
        if (sales != null) {
            //删除对应的文件
            List<SalesFile> salesFileList = salesFileMapper.findBySalesId(id);
            if (!salesFileList.isEmpty()) {
                salesFileMapper.del(salesFileList);
            }
            //删除对应的跟进
            List<SalesLog> salesLogList = salesLogMapper.findBySalesId(id);
            if (!salesLogList.isEmpty()) {
                salesLogMapper.del(salesLogList);
            }

            //删除自己
            salesMapper.del(id);
        }
    }
}
