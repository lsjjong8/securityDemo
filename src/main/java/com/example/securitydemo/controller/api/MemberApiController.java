package com.example.securitydemo.controller.api;

import com.example.securitydemo.controller.CrudController;
import com.example.securitydemo.model.entity.Member;
import com.example.securitydemo.model.network.Header;
import com.example.securitydemo.model.network.request.MemberApiRequestDto;
import com.example.securitydemo.model.network.response.MemberApiResponseDto;
import com.example.securitydemo.service.MemberApiLogicService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "사용자", description = "사용자 관련 API")
@RestController
@RequestMapping("/api/member")
public class MemberApiController extends CrudController<MemberApiRequestDto, MemberApiResponseDto, Member> {

    @PostMapping("/isDuplicated")
    public Header isDuplicatedByAccount(@RequestBody Header<MemberApiRequestDto> request) {
        log.info("{}, {}", "orderInfo : ", request);
        return ((MemberApiLogicService)baseService).isDuplicatedByAccount(request.getData().getAccount());
    }
}
