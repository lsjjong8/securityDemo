package com.example.securitydemo.service.base;

import com.example.securitydemo.ifs.CrudInterface;
import com.example.securitydemo.model.network.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public abstract class BaseService<Req,Res,Entity> implements CrudInterface<Req,Res> {

    @Autowired(required = false)
    protected JpaRepository<Entity,Long> baseRepository;

    public abstract Header<List<Res>> search(Pageable pageable);

    abstract protected Res response(Entity entity);

//    abstract protected Header<List<Res>> search(Pageable pageable);

}
