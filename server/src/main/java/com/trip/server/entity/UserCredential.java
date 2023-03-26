package com.trip.server.entity;

import javax.persistence.*;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_credential")
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
