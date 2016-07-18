package com.yawei.controller;

import com.google.common.collect.Maps;
import com.yawei.dto.DataTablesResult;
import com.yawei.exception.ForbiddenException;
import com.yawei.exception.NotFindException;
import com.yawei.pojo.*;
import com.yawei.service.CustomerService;
import com.yawei.service.SalesService;
import com.yawei.util.ShiroUtil;
import com.yawei.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sales")
public class SalesController {
    @Inject
    private SalesService salesService;
    @Inject
    private CustomerService customerService;
    @Value("${filePath}")
    private String savePath;

    /**
     * 获取销售机会列表
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("customerList",customerService.findAllCustomer());
        return "sales/list";
    }

    /**
     * 获取销售机会信息（Ajax）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/load", method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult<Sales> loadSales(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        String name = request.getParameter("name");
        name = Strings.toUtf8(name);
        String progress = request.getParameter("progress");
        progress = Strings.toUtf8(progress);
        String startdate = request.getParameter("startdate");
        String enddate = request.getParameter("enddate");

        Map<String, Object> params = Maps.newHashMap();
        params.put("start", start);
        params.put("length", length);
        params.put("name", name);
        params.put("progress", progress);
        params.put("startdate", startdate);
        params.put("enddate", enddate);

        List<Sales> salesList = salesService.findSalesByParam(params);
        Long count = salesService.count();
        Long filterCount = salesService.findCountByParam(params);
        return new DataTablesResult<>(draw, salesList, count, filterCount);
    }

    /**
     * 查看销售机会详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public String salesView(@PathVariable Integer id, Model model) {
        Sales sales = salesService.findSalesById(id);
        if (sales == null) {
            throw new NotFindException();
        }
        //判断当前的销售机会是否为公共、及当前的销售机会是否为自己的和判断自己是否是经理
        if (sales.getUserid() != null && !sales.getUserid().equals(ShiroUtil.getCurrentUserId()) && !ShiroUtil.isManager()) {
            throw new ForbiddenException();
        }
        model.addAttribute("sales", sales);
        //根据当前机会的id查找机会的日志记录
        List<SalesLog> salesLogList = salesService.findSalesLogBySalesid(id);
        model.addAttribute(salesLogList);
        //根据当前机会的id查找机会的文件列表
        List<SalesFile> salesFileList = salesService.findSalesFileBySalesid(id);
        model.addAttribute(salesFileList);

        return "sales/view";
    }

    /**
     * 上传文件
     *
     * @param file
     * @param salesid
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(MultipartFile file, Integer salesid) throws IOException {
        salesService.uploadFile(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), file.getSize(), salesid);
        return "success";
    }

    /**
     * 下载文件
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "file/{id:\\d+}/download", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downLoadFile(@PathVariable Integer id) throws FileNotFoundException, UnsupportedEncodingException {
        SalesFile salesFile = salesService.findSalesFileById(id);
        if (salesFile == null) {
            throw new NotFindException();
        }
        File file = new File(savePath, salesFile.getFilename());
        if (!file.exists()) {
            throw new NotFindException();
        }
        FileInputStream inputStream = new FileInputStream(file);

        String fileName = salesFile.getName();
        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        return ResponseEntity
                .ok()
                .contentLength(salesFile.getSize())
                .contentType(MediaType.parseMediaType(salesFile.getContenttype()))
                .header("Content-Disposition", "attachment;filename=\"" + fileName + "\"")
                .body(new InputStreamResource(inputStream));
    }

    /**
     * 新增销售机会
     *
     * @return
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public String saveSales(Sales sales) {
        salesService.saveSales(sales);
        return "success";
    }

    /**
     * 保存新增日志
     * @return
     */
    @RequestMapping(value = "/log/new",method = RequestMethod.POST)
    public String saveLog(SalesLog salesLog) {
        salesService.saveLog(salesLog);
        return "redirect:/sales/"+salesLog.getSalesid();
    }


    /**
     * 修改销售进度
     *
     * @return
     */
    @RequestMapping(value = "/progress/edit", method = RequestMethod.POST)
    public String editProgress(Integer id, String progress) {
        salesService.editProgress(id, progress);
        return "redirect:/sales/" + id;
    }


    /**
     * 删除销售机会
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/del/{id:\\d+}", method = RequestMethod.GET)
    public String delSales(@PathVariable Integer id) {
        salesService.delSales(id);
        return "redirect:/sales";
    }


}
