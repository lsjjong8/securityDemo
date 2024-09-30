package com.example.securitydemo.service;

// import com.example.securitydemo.model.entity.OrderGroup;
import com.example.securitydemo.model.entity.Member;
import com.example.securitydemo.model.enumclass.MemberRole;
import com.example.securitydemo.model.enumclass.MemberStatus;
import com.example.securitydemo.model.network.Header;
import com.example.securitydemo.model.network.Pagination;
import com.example.securitydemo.model.network.request.MemberApiRequestDto;
import com.example.securitydemo.model.network.response.MemberApiResponseDto;
import com.example.securitydemo.repository.MemberRepository;
import com.example.securitydemo.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberApiLogicService extends BaseService<MemberApiRequestDto, MemberApiResponseDto, Member> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // region CRUD
    @Override
    public Header<MemberApiResponseDto> create(Header<MemberApiRequestDto> request) {

        // 1. request data
        MemberApiRequestDto body = request.getData();

        // 2. USER 생성
        Member entity = Member.builder()
                .account(body.getAccount())
                .password(passwordEncoder.encode(body.getPassword()))
                .status(MemberStatus.REGISTERED)
                .role(MemberRole.USER)
                .email(body.getEmail())
                .phoneNumber(body.getPhoneNumber())
                .registeredAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .createdBy(body.getAccount())
                .updatedAt(LocalDateTime.now())
                .updatedBy(body.getAccount())
                .build();

        Member newEntity = baseRepository.save(entity);

        // 3. 생성된 데이터 -> UserApiResponse Return
        return Header.OK(response(newEntity));
    }

    @Override
    public Header<MemberApiResponseDto> read(Long id) {

        return baseRepository.findById(id)
                .map(entity -> response(entity))
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<MemberApiResponseDto> update(Header<MemberApiRequestDto> request) {

        // 1. data
        MemberApiRequestDto body = request.getData();

        // 2. id -> user 데이터를 찾고
        Optional<Member> optionalEntity = baseRepository.findById(body.getId());


        return optionalEntity.map(entity -> {
                    // 3. update
                    entity.setPassword(body.getPassword())
                            .setPhoneNumber(body.getPhoneNumber())
                            .setEmail(body.getEmail())
                            .setUpdatedAt(LocalDateTime.now());
                    return entity;
                })
                .map(entity -> baseRepository.save(entity))
                .map(newEntity -> response(newEntity))
                .map(Header::OK)
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        // 1. id -> repository -> user
        Optional<Member> optionalEntity = baseRepository.findById(id);

        // 2. repository -> delete를 status 업데이트로
        return optionalEntity.map(entity -> {
            // 3. update
            entity.setStatus(MemberStatus.UNREGISTERED)
                    .setUnregisteredAt(LocalDateTime.now());
            return entity;
        })
        .map(Header::OK)
        .orElseGet(() -> Header.ERROR("데이터 없음"));
    }
    // endregion

    protected MemberApiResponseDto response(Member entity) {
        // user -> userApiResponse

        MemberApiResponseDto memberApiResponseDto = MemberApiResponseDto.builder()
                .id(entity.getId())
                .account(entity.getAccount())
                // .password(entity.getPassword())
                .status(entity.getStatus())
                .role(entity.getRole())
                .phoneNumber(entity.getPhoneNumber())
                .email(entity.getEmail())
                .registeredAt(entity.getRegisteredAt())
                .unregisteredAt(entity.getUnregisteredAt())
                .build();

        return memberApiResponseDto;
    }

    @Override
    public Header<List<MemberApiResponseDto>> search(Pageable pageable) {

        Page<Member> entities = baseRepository.findAll(pageable);

        List<MemberApiResponseDto> memberApiResponseDtoList = entities.stream()
                .map(entity -> response(entity))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(entities.getTotalPages())
                .totalElements(entities.getTotalElements())
                .currentPage(entities.getNumber())
                .currentElements(entities.getNumberOfElements())
                .build();

        return Header.OK(memberApiResponseDtoList, pagination);
    }

    public Optional<Member> findByAccount(String account) {

        return ((MemberRepository)baseRepository).findByAccount(account)
                .map(entity -> entity);
    }

     // loginId 중복 체크 (impl 구현해야하나)
    public Header isDuplicatedByAccount(String account) {

        Optional<Boolean> optionalResult = Optional.ofNullable(Boolean.valueOf(((MemberRepository)baseRepository).existsByAccount(account)));

        return optionalResult
                .map(entity -> Header.OK(entity))
                .orElseGet(() -> Header.ERROR("오류"));
        /*Member prove = Member.builder()
                            .account(account).build();
        Example<Member> example = Example.of(prove);
        return baseRepository.exists(example);*/
    }

    //    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {
//
//        // user
//        User user = baseRepository.getOne(id);
//        UserApiResponse userApiResponse = response(user);
//
//
//        // orderGroup
//        List<OrderGroup> orderGroupList = user.getOrderGroupList();
//        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
//                .map(orderGroup -> {
//                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup);
//
//                    // item api response
//                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
//                            .map(detail -> detail.getItem())
//                            .map(item -> itemApiLogicService.response(item))
//                            .collect(Collectors.toList());
//
//                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
//                    return orderGroupApiResponse;
//                })
//                .collect(Collectors.toList());
//
//        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);
//
//        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
//                .userApiResponse(userApiResponse)
//                .build();
//
//        return Header.OK(userOrderInfoApiResponse);
//    }
}
