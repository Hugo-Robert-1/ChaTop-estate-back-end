package com.chatop.estate.controllers;

import com.chatop.estate.dtos.RentalDTO;
import com.chatop.estate.models.Rental;
import com.chatop.estate.services.DtoMapperService;
import com.chatop.estate.services.RentalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@Tag(name = "Rental Management", description = "Endpoints for managing rentals")
public class RentalController {

    @Value("${file.base-url}")
    private String imageBaseUrl;

    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private DtoMapperService dtoMapperService;
    
    @Operation(summary = "Get all rentals", description = "Retrieve a list of all rentals")
    @GetMapping
    public ResponseEntity<Map<String, List<RentalDTO>>> getAllRentals() {
    	
    	List<RentalDTO> rentalDTOs = rentalService.getAllRentals().stream()
    	        .map(dtoMapperService::toRentalDTO)
    	        .toList();
        return ResponseEntity.ok(Map.of("rentals", rentalDTOs));
    }
    
    @Operation(summary = "Get rental by ID", description = "Retrieve details of a specific rental by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
    	Rental rental = rentalService.getRentalById(id);
        RentalDTO rentalDTO = dtoMapperService.toRentalDTO(rental);
        return ResponseEntity.ok(rentalDTO);
    }

    @Operation(summary = "Create a new rental", description = "Create a new rental with the provided details")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createRental(
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("picture") MultipartFile picture) {
        rentalService.createRental(name, surface, price, description, picture);
        return ResponseEntity.ok(Map.of("message", "Rental created!"));
    }

    @Operation(summary = "Update an existing rental", description = "Update the details of an existing rental")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRental(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("surface") Double surface,
            @RequestParam("price") Double price,
            @RequestParam("description") String description) {
        rentalService.updateRental(id, name, surface, price, description);
        return ResponseEntity.ok(Map.of("message", "Rental updated !")); 
    }
}
