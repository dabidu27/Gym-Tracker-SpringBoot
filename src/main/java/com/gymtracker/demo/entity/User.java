package com.gymtracker.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")

//we use lombook annotations to avoid writting constructors and getters and setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@NotNull (add Column(nullable = false) in the entity, and @NotNull will be in the DTOs
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(updatable = false) //once set, the DB will never update this column, because we will auto generate the values for this column
    private LocalDateTime createdAt;

    //createdAt automatic generation
    @PrePersist
    protected void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
