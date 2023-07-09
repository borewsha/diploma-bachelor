package com.trip.server.database.entity;

import com.trip.server.database.enumeration.PlaceType;
import com.trip.server.model.Coordinatable;
import com.trip.server.model.Identifiable;
import com.trip.server.model.OsmIdentifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity(name = "place")
public class Place implements Identifiable, OsmIdentifiable, Coordinatable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Nullable
    private String osmId;

    @Enumerated(EnumType.STRING)
    private PlaceType type;

    @Nullable
    private String name;

    private String address;

    private Double lat;

    private Double lon;

}
