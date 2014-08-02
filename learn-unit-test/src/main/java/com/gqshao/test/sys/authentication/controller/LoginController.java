package com.gqshao.test.sys.authentication.controller;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import com.gqshao.test.sys.authentication.filter.*;

@Controller
public class LoginController {
    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "common/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String fail(@RequestParam(CustomFormAuthenticationFilter.DEFAULT_LOGINNAME_PARAM) String userName,
            Model model) {
        model.addAttribute(CustomFormAuthenticationFilter.DEFAULT_LOGINNAME_PARAM, userName);
        return "common/login";
    }

}
