package com.ticketfast.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "evento_id", nullable = false, length = 50)
    private String eventoId;

    @Column(name = "cliente_email", nullable = false, length = 150)
    private String clienteEmail;

    @Column(nullable = false, length = 20)
    private String zona;

    @Column(nullable = false)
    private Integer cantidad;
}
