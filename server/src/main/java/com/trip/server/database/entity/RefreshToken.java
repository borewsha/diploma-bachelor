package com.trip.server.database.entity;

import javax.persistence.*;

import com.trip.server.model.Identifiable;
import lombok.*;
import lombok.experimental.*;

import static lombok.AccessLevel.*;

@Data
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@NoArgsConstructor(access = PROTECTED)
@Entity(name = "refresh_token")
public class RefreshToken implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String token;

}
