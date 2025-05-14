package com.chatop.estate.models;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
public class Message {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String message;
	
	@CreationTimestamp
    @Column(name="created_at")
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "rental_id", referencedColumnName = "id", nullable = false)
    private Rental rental;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}
