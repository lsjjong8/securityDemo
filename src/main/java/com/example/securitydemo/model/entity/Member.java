package com.example.securitydemo.model.entity;

import com.example.securitydemo.model.enumclass.MemberRole;
import com.example.securitydemo.model.enumclass.MemberStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Data
// @ToString(exclude = {"orderGroupList"})
@ToString(callSuper = true)
// @EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class) // auditable 상속
@Builder
@Accessors(chain = true)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="ACCOUNT" , unique=true) // 동작 여부 확인 필요
    private String account;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberStatus status; // REGISTERED / UNREGISTERED

    @Enumerated(EnumType.STRING)
    private MemberRole role; // USER / ADMIN

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    // User 1 : N OrderGroup
    // @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    // private List<OrderGroup> orderGroupList;
}