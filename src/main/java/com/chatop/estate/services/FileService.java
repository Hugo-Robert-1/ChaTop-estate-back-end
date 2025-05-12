package com.chatop.estate.services;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;

@Service
@Tag(name = "File Management", description = "Service for managing file uploads")
public class FileService {
	
	@Value("${file.save.path}")
	private String directorySaveFile;

	public String saveImage(MultipartFile image) {
        File directory = new File(directorySaveFile);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String filePath = directorySaveFile + System.currentTimeMillis() + image.getOriginalFilename();
        try {
            image.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'image", e);
        }

        return image.getOriginalFilename();
    }
}
