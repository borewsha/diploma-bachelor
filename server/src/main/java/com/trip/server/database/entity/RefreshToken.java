package com.trip.server.database.entity;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.*;

import static lombok.AccessLevel.*;

@Data
@Builder
@Entity(name = "refresh_token")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

}
