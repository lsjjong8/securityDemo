package com.example.securitydemo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString(exclude = {"children"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class) // auditable 상속
@Builder
@Accessors(chain = true)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    private Long parentId;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    /*@ManyToOne(fetch = FetchType.LAZY) // optional = true, cascade = { CascadeType.ALL }
    @JoinColumn(name = "parentId") // , referencedColumnName = "id"
    private Menu parent;*/

    // @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // mappedBy = "parentId", , orphanRemoval = true
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private List<Menu> children;
}
