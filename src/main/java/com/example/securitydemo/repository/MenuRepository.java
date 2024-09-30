package com.example.securitydemo.repository;

import com.example.securitydemo.model.entity.Member;
import com.example.securitydemo.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findById(Long id);

    Optional<Long> countBy();

}
