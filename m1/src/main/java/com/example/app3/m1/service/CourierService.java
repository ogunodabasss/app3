package com.example.app3.m1.service;

import com.example.app3.m1.entity.Courier;
import com.example.app3.m1.entity.dto.BaseCourierDTO;
import com.example.app3.m1.repo.CourierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class CourierService {
    private final CourierRepository repository;

    public Courier insert(Courier courier) {
        return repository.save(courier);
    }

    public void delete(long id) {
        repository.delete(Courier.builder().id(id).build());
    }

    public Courier update(Courier updateEntity) {
        var load = this.findById(Objects.requireNonNull(updateEntity.getId())).orElseThrow();
        if (Objects.nonNull(updateEntity.getName()))
            load.setName(updateEntity.getName());
        if (Objects.nonNull(updateEntity.getSurName()))
            load.setSurName(updateEntity.getSurName());
        if (Objects.nonNull(updateEntity.getTc()))
            load.setTc(updateEntity.getTc());
        if (Objects.nonNull(updateEntity.getWork()))
            load.setWork(updateEntity.getWork());
        if (Objects.nonNull(updateEntity.getCourierPackages()) && !updateEntity.getCourierPackages().isEmpty())
            load.addAllPackageId(updateEntity.getCourierPackages());

        return repository.save(load);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Optional<Courier> findById(long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<Courier> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public <T extends BaseCourierDTO> T findById(long id, Class<T> clazz) {
        return repository.findById(id, clazz);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Long findCourierPackagesSelectPackageIdById(long id) {
        return repository.findCourierPackagesSelectPackageIdById(id);
    }
}
