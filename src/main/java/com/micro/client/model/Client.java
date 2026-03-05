package com.micro.client.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clientes")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(name = "createdAt",updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updateAt")
    private LocalDateTime updatedAt;

    @Column(name = "active")
    private boolean active = true;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (!active) {
            active = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}