package com.yawei.controller;

import com.google.common.collect.Maps;
import com.yawei.dto.DataTablesResult;
import com.yawei.exception.NotFindException;
import com.yawei.pojo.Notice;
import com.yawei.service.NoticeService;
import com.yawei.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Inject
    private NoticeService noticeService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(){
        return "notice/list";
    }

    /**
     * 发表公告
     * @return
     */
    @RequestMapping(value = "/new",method = RequestMethod.GET)
    public String newNotice(){
        return "notice/new";
    }

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public String newNotice(Notice notice, RedirectAttributes redirectAttributes){
        noticeService.saveNotice(notice);
        redirectAttributes.addFlashAttribute("message","公告发表成功");
        return "redirect:/notice";
    }

    /**
     * 显示公告列表
     * @return
     */
    @RequestMapping(value = "/list/load",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult showNotice(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");

        List<Notice> noticeList = noticeService.findAllNotice(start,length);
        Long count = noticeService.findNoticeCount();
        return new DataTablesResult<>(draw,noticeList,count,count);
    }

    /**
     * 根据id查找并显示公告的内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id:\\d+}",method = RequestMethod.GET)
    public String showNoticeById(@PathVariable Integer id, Model model){
        Notice notice = noticeService.findNoticeById(id);
        if(notice==null){
            throw new NotFindException();
        }
        model.addAttribute("notice",notice);
        return "notice/view";
    }

    /**
     * 在线编辑器的文件上传
     * @return
     */
    @RequestMapping(value = "/img/upload",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> fileUpload(MultipartFile file) throws IOException {
        Map<String,Object> result=Maps.newHashMap();
        if(!file.isEmpty()){
            String imgPath = noticeService.saveImg(file.getInputStream(),file.getOriginalFilename());
            result.put("success",true);
            result.put("fail_path",imgPath);
        }else {
            result.put("success",false);
            result.put("msg","请选择文件");
        }
        return result;
    }
}
