package com.example.securitydemo.model.network.response;

import com.example.securitydemo.model.entity.Menu;
import com.example.securitydemo.model.enumclass.MemberRole;
import com.example.securitydemo.model.enumclass.MemberStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuApiResponseDto {

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
