package com.example.app3.m2.service;

import com.example.app3.m2.entity.User;
import com.example.app3.m2.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 2)
public class UserService {
    private final UserRepository repository;

    public void delete(long id) {
        repository.delete(User.builder().id(id).build());
    }

    public User insert(User entity) {
        return repository.save(entity);
    }

    public User update(User updateEntity) {
        var load = this.findById(updateEntity.getId()).orElseThrow();
        if (Objects.nonNull(updateEntity.getId())) load.setId(updateEntity.getId());
        if (Objects.nonNull(updateEntity.getName())) load.setName(updateEntity.getName());
        if (Objects.nonNull(updateEntity.getSurName())) load.setSurName(updateEntity.getSurName());
        if (Objects.nonNull(updateEntity.getTc())) load.setTc(updateEntity.getTc());

        return repository.save(load);
    }

    public List<User> updateAll(List<User> updateEntityList) {
        var loads = this.repository.findAllById(updateEntityList.stream().map(User::getId).toList());

        var iterator = loads.iterator();
        for (User updateEntity : updateEntityList) {
            User load = iterator.next();
            if (Objects.nonNull(updateEntity.getId())) load.setId(updateEntity.getId());
            if (Objects.nonNull(updateEntity.getName())) load.setName(updateEntity.getName());
            if (Objects.nonNull(updateEntity.getSurName())) load.setSurName(updateEntity.getSurName());
            if (Objects.nonNull(updateEntity.getTc())) load.setTc(updateEntity.getTc());
        }
        return repository.saveAll(loads);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public Optional<User> findById(long id) {
        return repository.findById(id);
    }
}
