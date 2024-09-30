package com.example.securitydemo.controller;

import com.example.securitydemo.ifs.CrudInterface;
import com.example.securitydemo.model.network.Header;
import com.example.securitydemo.service.base.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Component
public abstract class CrudController<Req,Res,Entity> implements CrudInterface<Req,Res> {

    @Autowired(required = false)
    protected BaseService<Req,Res,Entity> baseService;

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> request) {
        log.info("{}, {}", "create : ", request);
        return baseService.create(request);
    }

    @Operation(parameters = {
            @Parameter(in = ParameterIn.PATH
                    , description = "id you want to retrieve (0..N)"
                    , name = "id"
                    , content = @Content(schema = @Schema(type = "integer", defaultValue = "1")))
    })
    @Override
    @GetMapping("{id}")
    public Header<Res> read(@Parameter(name = "id", description = "Primary key")
            @PathVariable Long id) {
        log.info("{}, {}", "read : ", id);
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> request) {
        log.info("{}, {}", "update : ", request);
        return baseService.update(request);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        log.info("{}, {}", "delete : ", id);
        return baseService.delete(id);
    }

    @GetMapping("")
    public Header<List<Res>> search(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("{}, {}", "search : ", pageable);
        return baseService.search(pageable);
    }
}
