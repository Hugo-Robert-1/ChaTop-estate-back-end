package com.chatop.estate.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.estate.dtos.AuthDTO;
import com.chatop.estate.dtos.LoginDTO;
import com.chatop.estate.dtos.MessageDTO;
import com.chatop.estate.services.AuthService;
import com.chatop.estate.services.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/messages")
@Tag(name = "Message Management", description = "Endpoints for managing messages")
public class MessageController {

	private final MessageService messageService;
	
	public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    
	@PostMapping("")
    public ResponseEntity<?> create(@RequestBody MessageDTO messageDTO) {
		messageService.createMessage(messageDTO);
        return ResponseEntity.ok(Map.of("message", "Message send with success"));
    }
}
