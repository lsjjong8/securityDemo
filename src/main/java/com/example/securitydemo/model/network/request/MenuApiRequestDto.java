package com.example.securitydemo.model.network.request;

import com.example.securitydemo.model.entity.Menu;
import com.example.securitydemo.model.enumclass.MemberRole;
import com.example.securitydemo.model.enumclass.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuApiRequestDto {

    private Long id;

    private String name;

    private String url;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private Long parentId;

    private List<Menu> children;

}