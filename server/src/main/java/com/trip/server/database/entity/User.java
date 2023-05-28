package com.trip.server.database.entity;

import java.time.*;

import javax.persistence.*;

import com.trip.server.model.Identifiable;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity(name = "\"user\"")
public class User implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole role;

    @Column(nullable = false)
    private ZonedDateTime registeredAt;

    private String fullName;

}
