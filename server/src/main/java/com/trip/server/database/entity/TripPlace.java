package com.trip.server.database.entity;

import com.trip.server.database.enumeration.RouteType;
import com.trip.server.model.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity(name = "trip_place")
public class TripPlace implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    private LocalDate date;

    @Column(name = "\"order\"")
    private Integer order;

    @Enumerated(EnumType.STRING)
    private RouteType type;

}
