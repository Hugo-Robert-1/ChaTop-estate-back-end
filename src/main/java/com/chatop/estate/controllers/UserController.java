package com.chatop.estate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.estate.dtos.UserDTO;
import com.chatop.estate.services.DtoMapperService;
import com.chatop.estate.services.UserService;
import com.chatop.estate.models.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User Controller", description = "Handle user-related operations")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
    private UserService userService;

    @Autowired
    private DtoMapperService dtoMapperService;
	
	/**
     * @param id
     * @return ResponseEntity<UserDTO>
     */
    @Operation(summary = "Get a user by his id", description = "Return informations of a user with his ID.")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    	User user = userService.getUserById(id);
        UserDTO userDTO = dtoMapperService.toUserDTO(user);
        return ResponseEntity.ok(userDTO);
    }
}
