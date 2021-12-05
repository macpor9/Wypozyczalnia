package com.maciej.poreba.backend.application.resource;

import com.maciej.poreba.backend.application.service.FilterFieldsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterFieldsResource {

    private final FilterFieldsService filterFieldsService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/models")
    public ResponseEntity<?> getModels(){
        return ResponseEntity.ok(filterFieldsService.getModels());
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/brands")
    public ResponseEntity<?> getBrands(){
        return ResponseEntity.ok(filterFieldsService.getBrands());
    }
}
