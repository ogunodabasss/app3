package com.example.app3.m2.rest;

import com.example.app3.m2.entity.Package;
import com.example.app3.m2.rest.dto.PackageInsert;
import com.example.app3.m2.service.PackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/package")
public class PackageRestController {

    private final PackageService service;

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Package> insert(@Valid @RequestBody PackageInsert insert) {
        return ResponseEntity.ok(service.insert(PackageInsert.toEntity(insert)));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping(value = "/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Package>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
