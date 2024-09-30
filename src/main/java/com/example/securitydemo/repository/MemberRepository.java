package com.example.securitydemo.repository;

import com.example.securitydemo.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);

    Optional<Member> findByAccount(String account);

    Optional<Member> getFirstByOrderByIdDesc();

    Optional<Long> countBy();

    boolean existsByAccount(String account);
}
