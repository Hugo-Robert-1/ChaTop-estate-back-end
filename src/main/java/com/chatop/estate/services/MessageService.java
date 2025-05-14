package com.chatop.estate.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.chatop.estate.dtos.MessageDTO;
import com.chatop.estate.models.Message;
import com.chatop.estate.models.Rental;
import com.chatop.estate.models.User;
import com.chatop.estate.repositories.MessageRepository;
import com.chatop.estate.repositories.RentalRepository;
import com.chatop.estate.repositories.UserRepository;

@Service
public class MessageService {
	
private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, RentalRepository rentalRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }
	
    /**
     * Create a new message and save it to the database
	 * Check if the user and rental exist
     * @param messageDTO
     */
	public void createMessage(MessageDTO messageDTO) {
		// Vérifier que l'utilisateur existe
		User user = userRepository.findById(messageDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID : " + messageDTO.getUser_id()));

        // Vérifier que la location existe
        Rental rental = rentalRepository.findById(messageDTO.getRental_id())
                .orElseThrow(() -> new IllegalArgumentException("Location non trouvée avec l'ID : " + messageDTO.getRental_id()));
        
        Message message = new Message();
        message.setMessage(messageDTO.getMessage());
        message.setUser(user);
        message.setRental(rental);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
		
        messageRepository.save(message);
	}
	
}
