package com.example.securitydemo.model.entity.base;

import com.example.securitydemo.config.auth.PrincipalDetails;
import com.example.securitydemo.model.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;

@Data
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class}) // auditable 상속
public abstract class BaseEntity /* implements Auditable<PrincipalDetails, Long, LocalDateTime>*/ {
    @CreatedBy
    @Column(nullable = false, updatable = false)
    String createdBy;
    @CreatedDate
    @Column(nullable = false)
    LocalDateTime createdDate;
    @LastModifiedBy
    @Column(nullable = false)
    String lastModifiedBy;
    @LastModifiedDate
    @Column(nullable = false)
    LocalDateTime lastModifiedDate; // Optional?

}
