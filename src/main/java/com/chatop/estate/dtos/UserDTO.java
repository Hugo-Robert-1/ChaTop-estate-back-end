package com.chatop.estate.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	private Long id;
    private String name;
    private String email;
    private Date created_at;
    private Date updated_at;
}

