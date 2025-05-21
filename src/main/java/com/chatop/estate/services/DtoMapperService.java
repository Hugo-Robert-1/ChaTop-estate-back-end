package com.chatop.estate.services;

import com.chatop.estate.dtos.RentalDTO;
import com.chatop.estate.dtos.UserDTO;
import com.chatop.estate.dtos.MessageDTO;
import com.chatop.estate.models.Rental;
import com.chatop.estate.models.User;
import com.chatop.estate.models.Message;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DtoMapperService {
    private final ModelMapper modelMapper;

    @Value("${file.base-url}")
    private String imageBaseUrl;

    public DtoMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                map().setCreated_at(source.getCreatedAt());
                map().setUpdated_at(source.getUpdatedAt());
            }
        });
    }

    public RentalDTO toRentalDTO(Rental rental) {
        RentalDTO dto = modelMapper.map(rental, RentalDTO.class);
        dto.setPicture(imageBaseUrl + rental.getPicture());
        dto.setOwner_id(rental.getOwner().getId());
        dto.setCreated_at(rental.getCreatedAt());
        dto.setUpdated_at(rental.getUpdatedAt());
        return dto;
    }

    public UserDTO toUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public MessageDTO toMessageDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setMessage(message.getMessage());
        dto.setRental_id(message.getRental().getId());
        dto.setUser_id(message.getUser().getId());
        return dto;
    }
}
