package com.chatop.estate.controllers;

import com.chatop.estate.dtos.RentalDTO;
import com.chatop.estate.models.Rental;
import com.chatop.estate.services.RentalService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Value("${file.base-url}")
    private String imageBaseUrl;

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }
    
    @GetMapping
    public ResponseEntity<Map<String, List<RentalDTO>>> getAllRentals() {
    	
        List<RentalDTO> rentalDTOs = rentalService.getAllRentals().stream()
            .map(rental -> new RentalDTO(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                imageBaseUrl + rental.getPicture(), 
                rental.getDescription(),
                rental.getOwner().getId(),
                rental.getCreatedAt(),
                rental.getUpdatedAt()                
            ))
            .toList();
        return ResponseEntity.ok(Map.of("rentals", rentalDTOs));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        Rental rental = rentalService.getRentalById(id);
        RentalDTO rentalDTO = new RentalDTO(
            rental.getId(),
            rental.getName(),
            rental.getSurface(),
            rental.getPrice(),
            imageBaseUrl + rental.getPicture(),
            rental.getDescription(),
            rental.getOwner().getId(),
            rental.getCreatedAt(),
            rental.getUpdatedAt()    
        );
        return ResponseEntity.ok(rentalDTO);
    }

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
