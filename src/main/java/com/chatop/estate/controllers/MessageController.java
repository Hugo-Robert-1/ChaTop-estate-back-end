package com.chatop.estate.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.estate.dtos.MessageDTO;
import com.chatop.estate.services.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Message Management", description = "Endpoint for managing messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
    
	@PostMapping("")
	@Operation(summary = "Create a message", description = "Create a message linked to a specific rental and the user that send it")
    public ResponseEntity<?> create(@RequestBody MessageDTO messageDTO) {
		messageService.createMessage(messageDTO);
        return ResponseEntity.ok(Map.of("message", "Message send with success"));
    }
}
