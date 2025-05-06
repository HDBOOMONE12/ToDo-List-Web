package ru.Artem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class CommonController {

    @RequestMapping("/home")
    public String getMainPage(){
        return "main-page";
    }
}
