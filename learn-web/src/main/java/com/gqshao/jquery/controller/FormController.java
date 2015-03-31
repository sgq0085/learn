package com.gqshao.jquery.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("form")
public class FormController {

    /**
     * test1
     */
    @RequestMapping("/test1")
    public String test1(Model model) {
        List<Map<String, String>> options = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Map<String, String> opt = Maps.newHashMap();
            opt.put("key", "key-1-" + i);
            opt.put("value", "value-1-" + i);
            options.add(opt);
        }
        model.addAttribute("options1", options);
        return "/form/test1";
    }

    /**
     * test2
     */
    @RequestMapping("/test2")
    public String test2(Model model) {
        List<Map<String, String>> options = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            Map<String, String> opt = Maps.newHashMap();
            opt.put("key", "key-1-" + i);
            opt.put("value", "value-1-" + i);
            options.add(opt);
        }
        model.addAttribute("options1", options);

        String[] autoComplete = new String[]{"ActionScript", "AppleScript", "BASIC", "C", "C++"
                , "Clojure", "COBOL", "ColdFusion", "Erlang", "Fortran", "Groovy", "Haskell"
                , "Java", "JavaScript", "Lisp", "Perl", "PHP", "Python", "Ruby", "Scala", "Scheme"};
        model.addAttribute("autoComplete", autoComplete);
        return "/form/test2";
    }
}