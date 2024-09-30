package com.example.securitydemo.controller.page;

import com.example.securitydemo.model.entity.Member;
import com.example.securitydemo.model.network.Header;
import com.example.securitydemo.service.MemberApiLogicService;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("")
public class PageController {

    @Autowired
    MemberApiLogicService memberApiLogicService;

    @GetMapping(value = "")
    public String indexPage() {
        return "index";
    }


    @GetMapping(value = "/login")
    public String loginPage() {
        return "login"; // html을 응답하려고 하니 classpath 기준 /을 넣어줘야하는 것 같음.
    }

    @GetMapping(value = "/join")
    public String signUpPage() {
        return "join"; // html을 응답하려고 하니 classpath 기준 /을 넣어줘야하는 것 같음.
    }

    @GetMapping(value = "/admin")
    public String adminPage() {
        return "admin";
    }

    @PostMapping(value = "/check")
    @ResponseBody
    public Header<String> indexPage(Authentication auth) {

        String memberName = "";

        Header<String> response = auth == null?
                Header.ERROR("로그인이 필요합니다.") : Header.OK(auth.getName());

        return response;
    }
}
