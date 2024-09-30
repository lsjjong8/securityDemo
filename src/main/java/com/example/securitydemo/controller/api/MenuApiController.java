package com.example.securitydemo.controller.api;

import com.example.securitydemo.controller.CrudController;
import com.example.securitydemo.model.entity.Member;
import com.example.securitydemo.model.entity.Menu;
import com.example.securitydemo.model.network.Header;
import com.example.securitydemo.model.network.request.MemberApiRequestDto;
import com.example.securitydemo.model.network.request.MenuApiRequestDto;
import com.example.securitydemo.model.network.response.MemberApiResponseDto;
import com.example.securitydemo.model.network.response.MenuApiResponseDto;
import com.example.securitydemo.service.MemberApiLogicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "메뉴", description = "메뉴 관련 API")
@RestController
@RequestMapping("/api/menu")
public class MenuApiController extends CrudController<MenuApiRequestDto, MenuApiResponseDto, Menu> {
}
