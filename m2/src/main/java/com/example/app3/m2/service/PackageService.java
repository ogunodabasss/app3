package com.example.app3.m2.service;

import com.example.app3.m2.entity.Package;
import com.example.app3.m2.repo.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class PackageService {
    private final PackageRepository repository;
    private final ConfigurableApplicationContext context;

    public void delete(long id) {
        repository.delete(Package.builder().id(id).build());
    }

    public Package insert(Package entity) {
        log.warn(entity.toString());
        return repository.save(entity);
    }

    public Package update(Package updateEntity) {
        var load = this.findById(updateEntity.getId()).orElseThrow();

        if (Objects.nonNull(updateEntity.getAddress())) load.setAddress(updateEntity.getAddress());
        if (Objects.nonNull(updateEntity.getLatitude())) load.setLatitude(updateEntity.getLatitude());
        if (Objects.nonNull(updateEntity.getLongitude())) load.setLongitude(updateEntity.getLongitude());
        if (Objects.nonNull(updateEntity.getUser())) {
            var _ = context.getBean(UserService.class).update(updateEntity.getUser());
        }
        return repository.save(load);
    }

    public Iterable<Package> updateAll(List<Package> updateEntityList) {
        var loads = this.repository.findAllById(updateEntityList.stream().map(Package::getId).toList());

        System.err.println(loads);
        var iterator = loads.iterator();
        for (Package updateEntity : updateEntityList) {
            Package load = iterator.next();
            if (Objects.nonNull(updateEntity.getAddress())) load.setAddress(updateEntity.getAddress());
            if (Objects.nonNull(updateEntity.getLatitude())) load.setLatitude(updateEntity.getLatitude());
            if (Objects.nonNull(updateEntity.getLongitude())) load.setLongitude(updateEntity.getLongitude());
            if (Objects.nonNull(updateEntity.getPackageStatus())) load.setPackageStatus(updateEntity.getPackageStatus());
            if (Objects.nonNull(updateEntity.getCourierId())) load.setCourierId(updateEntity.getCourierId());
            if (Objects.nonNull(updateEntity.getStartDateTime())) load.setStartDateTime(updateEntity.getStartDateTime());
            if (Objects.nonNull(updateEntity.getEndDateTime())) load.setEndDateTime(updateEntity.getEndDateTime());
        }
        var users = updateEntityList.stream().map(Package::getUser).filter(Objects::nonNull).toList();
        if (!users.isEmpty()) {
            var _ = context.getBean(UserService.class).updateAll(users);
        }
        return repository.saveAll(loads);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public List<Package> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Optional<Package> findById(long id) {
        return repository.findById(id);
    }
}
