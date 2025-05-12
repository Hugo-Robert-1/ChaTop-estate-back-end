package com.chatop.estate.services;

import com.chatop.estate.models.Rental;
import com.chatop.estate.models.User;
import com.chatop.estate.repositories.RentalRepository;
import com.chatop.estate.repositories.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, FileService fileService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rental non trouvé avec l'ID : " + id));
    }

    public Rental createRental(String name, Double surface, Double price, String description, MultipartFile image) {
    	String curentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    	User currentUser = userRepository.findByEmail(curentUserEmail);

    	User owner = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Propriétaire non trouvé avec l'ID : " + currentUser.getId()));

        String imagePath = fileService.saveImage(image);
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setOwner(owner);
        rental.setPicture(imagePath);
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        return rentalRepository.save(rental);
    }

    public Rental updateRental(Long id, String name, Double surface, Double price, String description) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rental non trouvé avec l'ID : " + id));

        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);

        return rentalRepository.save(rental);
    }
}
