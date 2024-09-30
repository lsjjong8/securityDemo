package com.example.securitydemo.model.network.request;

import com.example.securitydemo.model.enumclass.MemberRole;
import com.example.securitydemo.model.enumclass.MemberStatus;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberApiRequestDto {

    private Long id;

    private String account;

    private String password;

    private MemberStatus status;

    private MemberRole role;

    private String email;

    private String phoneNumber;

    public LocalDateTime registeredAt;

    public LocalDateTime unregisteredAt;

}