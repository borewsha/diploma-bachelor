package com.trip.server.entity;

import java.time.*;

import javax.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private ZonedDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

}
