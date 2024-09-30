package com.example.securitydemo.service;

import com.example.securitydemo.model.entity.Menu;
import com.example.securitydemo.model.network.Header;
import com.example.securitydemo.model.network.Pagination;
import com.example.securitydemo.model.network.request.MenuApiRequestDto;
import com.example.securitydemo.model.network.response.MenuApiResponseDto;
import com.example.securitydemo.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuApiLogicService extends BaseService<MenuApiRequestDto, MenuApiResponseDto, Menu> {

    // region CRUD
    @Override
    public Header<MenuApiResponseDto> create(Header<MenuApiRequestDto> request) {

        // 1. request data
        MenuApiRequestDto body = request.getData();

        // 2. entity 생성
        Menu entity = Menu.builder()
                .name(body.getName())
                .url(body.getUrl())
                .createdAt(LocalDateTime.now())
                // .createdBy(body.getAccount())
                .updatedAt(LocalDateTime.now())
                // .updatedBy(body.getAccount())
                .build();

        Menu newEntity = baseRepository.save(entity);

        // 3. 생성된 데이터 -> UserApiResponse Return
        return Header.OK(response(newEntity));
    }

    @Override
    public Header<MenuApiResponseDto> read(Long id) {

        return baseRepository.findById(id)
                .map(entity -> response(entity))
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<MenuApiResponseDto> update(Header<MenuApiRequestDto> request) {

        // 1. data
        MenuApiRequestDto body = request.getData();

        // 2. id -> user 데이터를 찾고
        Optional<Menu> optionalEntity = baseRepository.findById(body.getId());


        return optionalEntity.map(entity -> {
                    // 3. update
                    entity.setName(body.getName())
                            .setUrl(body.getUrl())
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
        Optional<Menu> optionalEntity = baseRepository.findById(id);

        // 2. repository -> delete
        return optionalEntity.map(entity -> {
                    baseRepository.delete(entity);
                    return Header.OK();
                })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }
    // endregion

    protected MenuApiResponseDto response(Menu entity) {
        // user -> userApiResponse

        MenuApiResponseDto memberApiResponseDto = MenuApiResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .url(entity.getUrl())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .parentId(entity.getParentId()) // entity.getParent() != null? entity.getParent().getId():null
                .children(entity.getChildren())
                .build();

        return memberApiResponseDto;
    }

    @Override
    public Header<List<MenuApiResponseDto>> search(Pageable pageable) {

        Page<Menu> entities = baseRepository.findAll(pageable);

        List<MenuApiResponseDto> memberApiResponseDtoList = entities.stream()
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
    
}
