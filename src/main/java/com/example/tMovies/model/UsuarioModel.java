package com.example.tMovies.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, name = "nome")
    private String nome;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "senha")
    private String senha;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

}
