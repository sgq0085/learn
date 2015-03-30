package com.gqshao.bootstrap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bootstrap/datetimepicker")
public class DatetimepickerController {

    @RequestMapping("/init")
    public String init() {
        return "/bootstrap/datetimepicker";
    }
}