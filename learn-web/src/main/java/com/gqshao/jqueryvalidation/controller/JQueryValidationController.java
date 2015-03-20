package com.gqshao.jqueryvalidation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/validation")
public class JQueryValidationController {
    /**
     * 页面初始化
     */
    @RequestMapping("/init")
    public String test0() {
        return "/jquery-validation/jquery-validation";
    }


}