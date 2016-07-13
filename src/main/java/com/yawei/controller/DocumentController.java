package com.yawei.controller;

import com.yawei.exception.NotFindException;
import com.yawei.pojo.Document;
import com.yawei.service.DocumentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/doc")
public class DocumentController {
    @Inject
    private DocumentService documentService;
    @Value("${filePath}")
    private String savePath;

    @RequestMapping(method = RequestMethod.GET)
    public String showList(Model model,@RequestParam(required = false,defaultValue = "0") Integer fid) {
        List<Document> documentList = documentService.findDocumentByFid(fid);
        model.addAttribute("documentList", documentList);
        model.addAttribute("fid", fid);
        return "document/list";
    }

    /**
     * 保存文件夹
     * @return
     */
    @RequestMapping(value = "/dir/new",method = RequestMethod.POST)
    public String saveDir(String name,Integer fid){
        documentService.saveDir(name, fid);
        return "redirect:/doc?fid="+fid;
    }

    /**
     * 文件上传
     * @param file
     * @param fid
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/file/upload",method = RequestMethod.POST)
    @ResponseBody
    public String saveFile(MultipartFile file,Integer fid) throws IOException {
        if(file.isEmpty()){
            throw new NotFindException();
        }else {
            documentService.saveFile(file.getInputStream(),file.getOriginalFilename(),file.getContentType(),file.getSize(),fid);
        }
        return "success";
    }


    /**
     * 文件下载
     * @param id
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/download/{id:\\d+}",method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Integer id) throws FileNotFoundException, UnsupportedEncodingException {
        Document document = documentService.findDocumentById(id);
        if(document==null){
            throw new NotFindException();
        }
        File file = new File(savePath,document.getFilename());
        if(!file.exists()){
            throw new NotFindException();
        }

        FileInputStream inputStream = new FileInputStream(file);
        String fileName=document.getName();
        fileName = new String(fileName.getBytes("UTF-8"),"ISO8859-1");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getContexttype()))
                .contentLength(file.length())
                .header("Content-Disposition","attachment;filename=\""+fileName+"\"")
                .body(new InputStreamResource(inputStream));
    }
}
