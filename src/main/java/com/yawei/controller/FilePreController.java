package com.yawei.controller;

import com.yawei.exception.NotFindException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class FilePreController {
    @Value("${imgPath}")
    private String filePath;

    @RequestMapping(value = "/pre/{fileName}")
    public void preFile(@PathVariable String fileName, HttpServletResponse response) throws IOException{
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            throw new NotFindException();
        }
        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(inputStream,outputStream);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
}
