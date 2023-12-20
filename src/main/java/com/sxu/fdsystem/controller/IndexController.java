package com.sxu.fdsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Slf4j
public class IndexController {

    @Value("${server.port}")
    private Integer port;

    @RequestMapping("/")
    public String jumpIndex(){
        if (port == 8081)
        return "/backend/index.html";
        return "/client/index.html";
    }
}
