package com.gqshao.highcharts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/highcharts")
public class HighchartsController {

    @RequestMapping("/test1")
    public String test1() {
        return "/highcharts/test1";
    }

    @RequestMapping("/test2")
    public String test2() {
        return "/highcharts/test2";
    }

    @RequestMapping("/test3")
    public String test3() {
        return "/highcharts/test3";
    }

    @RequestMapping("/test4")
    public String test4() {
        return "/highcharts/test4";
    }

    @RequestMapping("/test5")
    public String test5() {
        return "/highcharts/test5";
    }

    @RequestMapping("/test6")
    public String test6() {
        return "/highcharts/test6";
    }
}