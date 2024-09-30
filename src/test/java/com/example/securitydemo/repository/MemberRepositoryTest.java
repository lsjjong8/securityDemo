package com.example.securitydemo.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.example.securitydemo.model.entity.Member;
import com.example.securitydemo.model.enumclass.MemberStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;


@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    // region CRUD
    @Test
    @Transactional
    void create() {

        try {
            Member entity = Member.builder()
                    .account("Test Account")
                    .password("1234")
                    .status(MemberStatus.REGISTERED)
                    .registeredAt(LocalDateTime.now())
                    .build();

            Member newEntity = memberRepository.save(entity);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (JpaObjectRetrievalFailureException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    void read() {
        Long id = 1L;


        try {
            Optional<Member> selectedEntity = memberRepository.findById(id);
            assertNotNull(selectedEntity.get());

            selectedEntity.ifPresent(entity -> {
                System.out.println("Result : " + entity);
            });

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    void update() {

        try {

            Optional<Member> maxRow = memberRepository.getFirstByOrderByIdDesc();

            Member entity = maxRow.get();

            maxRow.ifPresent(perform -> {
                entity.setPassword("0000")
                        .setUpdatedAt(LocalDateTime.now());
            });

            Member newEntity = memberRepository.save(entity);

            assertEquals(entity, newEntity);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (JpaObjectRetrievalFailureException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Transactional
    void delete() {
        Optional<Long> before = memberRepository.countBy();
        Long beforeCount = before.get();

        try {
            Optional<Member> maxRow = memberRepository.getFirstByOrderByIdDesc();

            memberRepository.delete(maxRow.get());

            Optional<Long> after = memberRepository.countBy();
            Long afterCount = after.get();

            assertEquals(beforeCount, afterCount + 1L);

        } catch (ConstraintViolationException e){
            e.printStackTrace();
        } catch (DataIntegrityViolationException e){
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion
}