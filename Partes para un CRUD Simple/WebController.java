package com.prueba06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping
    public String paginainicio(){
        return "index.html";
    }
    
}
