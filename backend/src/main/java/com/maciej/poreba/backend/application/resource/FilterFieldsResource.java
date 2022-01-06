package com.maciej.poreba.backend.application.resource;

import com.maciej.poreba.backend.application.service.FilterFieldsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterFieldsResource {

    private final FilterFieldsService filterFieldsService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/models")
    public ResponseEntity<?> getModels(@RequestParam(value = "brand", required = false) String brand){
        List<String> s = filterFieldsService.getModels(brand);
        return ResponseEntity.ok(filterFieldsService.getModels(brand));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/brands")
    public ResponseEntity<?> getBrands(){
        return ResponseEntity.ok(filterFieldsService.getBrands());
    }
}
