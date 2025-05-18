package com.chatop.estate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop.estate.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

}
