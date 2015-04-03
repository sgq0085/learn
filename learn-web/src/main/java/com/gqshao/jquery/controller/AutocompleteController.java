package com.gqshao.jquery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autocomplete")
public class AutocompleteController {

    /**
     * 页面初始化
     */
    @RequestMapping("/init")
    public String test0() {
        return "/jquery-autocomplete/jquery-autocomplete";
    }

}