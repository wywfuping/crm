package com.yawei.service;

import com.yawei.mapper.CustomerMapper;
import com.yawei.mapper.SalesMapper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

@Named
public class ChartService {
    @Inject
    private CustomerMapper customerMapper;
    @Inject
    private SalesMapper salesMapper;

    /**
     * 统计查询区间内新增客户的数量
     * @param start
     * @param end
     * @return
     */
    public Long findNewCustCount(String start, String end) {
        DateTime now = DateTime.now();
        //当开始时间为空，则跳到当月第一天
        if(StringUtils.isEmpty(start)){
            start = now.dayOfMonth().withMinimumValue().toString("YYYY-MM-dd");
        }
        //当结束时间为空时，则默认到当天
        if(StringUtils.isEmpty(end)){
            end = now.toString("YYYY-MM-dd");
        }
        return customerMapper.findNewCustCount(start,end);
    }

    /**
     * 查找区间内销售机会（完成交易）的数量
     * @param start
     * @param end
     * @return
     */
    public Long findSalesCount(String start, String end) {
        DateTime now = DateTime.now();
        //当开始时间为空，则跳到当月第一天
        if(StringUtils.isEmpty(start)){
            start = now.dayOfMonth().withMinimumValue().toString("YYYY-MM-dd");
        }
        //当结束时间为空时，则默认到当天
        if(StringUtils.isEmpty(end)){
            end = now.toString("YYYY-MM-dd");
        }
        return salesMapper.findSalesSuccessCount(start,end,"完成交易");
    }

    /**
     * 查询各个完成交易的总金额
     * @param start
     * @param end
     * @return
     */
    public Float findSalesPrice(String start, String end) {
        DateTime now = DateTime.now();
        //当开始时间为空，则跳到当月第一天
        if(StringUtils.isEmpty(start)){
            start = now.dayOfMonth().withMinimumValue().toString("YYYY-MM-dd");
        }
        //当结束时间为空时，则默认到当天
        if(StringUtils.isEmpty(end)){
            end = now.toString("YYYY-MM-dd");
        }
        return salesMapper.findSalesPrice(start,end,"完成交易");
    }

    /**
     * 获取用户的销售金额
     * @param start
     * @param end
     * @return
     */
    public List<Map<String, Object>> userSalesPrice(String start, String end) {
        DateTime now = DateTime.now();
        //当开始时间为空，则跳到当月第一天
        if(StringUtils.isEmpty(start)){
            start = now.dayOfMonth().withMinimumValue().toString("YYYY-MM-dd");
        }
        //当结束时间为空时，则默认到当天
        if(StringUtils.isEmpty(end)){
            end = now.toString("YYYY-MM-dd");
        }
        return salesMapper.userSalesPrice(start,end);
    }

    /**
     * 获取销售进度
     * @param start
     * @param end
     * @return
     */
    public List<Map<String, Object>> progressData(String start, String end) {
        DateTime now = DateTime.now();
        //当开始时间为空，则跳到当月第一天
        if(StringUtils.isEmpty(start)){
            start = now.dayOfMonth().withMinimumValue().toString("YYYY-MM-dd");
        }
        //当结束时间为空时，则默认到当天
        if(StringUtils.isEmpty(end)){
            end = now.toString("YYYY-MM-dd");
        }
        return salesMapper.progressData(start,end);
    }
}
