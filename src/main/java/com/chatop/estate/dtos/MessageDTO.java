package com.chatop.estate.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
	private String message;
	private Long rental_id;
	private Long user_id;
}
