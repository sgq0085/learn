package com.gqshao.jqueryautocomplete.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autocomplete")
public class JQueryAutocompleteController {

    /**
     * 页面初始化
     */
    @RequestMapping("/init")
    public String test0() {
        return "/jquery-autocomplete/jquery-autocomplete";
    }

}