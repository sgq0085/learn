package com.gqshao.bootstrap.controller;

import com.google.common.collect.Maps;
import com.gqshao.commons.utils.Files;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bootstrap/fileinput")
public class FileinputController {

    @RequestMapping("/init")
    public String init() {
        return "/bootstrap/fileinput";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> upload(HttpServletRequest request) {
        Map<String, Object> res = Maps.newHashMap();
        res.put("success", false);

        Map<String, Object> multipartFormData = Files.enctypeEqualsMultipartFormData(request, null);
        List<File> files = (List<File>) multipartFormData.get("files");
        if (CollectionUtils.isEmpty(files)) {
            res.put("msg", "请选择文件");
            return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
        }
        Map<String, String> params = (Map<String, String>) multipartFormData.get("params");
        String other = params.get("other");
        double size = 0;
        for (File file : files) {
            size = +file.length();
        }
        // B → MB
        size = size / 1024 / 1024;
        res.put("msg", "共有文件" + files.size() + ", 文件大小共计:" + size + "M, 其它信息: " + other);
        res.put("success", true);
        return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
    }
}