package com.example.securitydemo.model.network.response;

import com.example.securitydemo.model.enumclass.MemberRole;
import com.example.securitydemo.model.enumclass.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberApiResponseDto {

    private Long id;

    private String account;

    // private String password;

    private MemberStatus status;

    private MemberRole role;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    // private List<OrderGroupApiResponse> orderGroupApiResponseList;

}
