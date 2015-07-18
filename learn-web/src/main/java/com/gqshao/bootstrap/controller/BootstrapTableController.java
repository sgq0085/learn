package com.gqshao.bootstrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bootstrap-table")
public class BootstrapTableController {
    @RequestMapping("/test1")
    public String init() {
        return "/bootstrap-table/test1";
    }
}
