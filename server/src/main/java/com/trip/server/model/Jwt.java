package com.trip.server.model;

import lombok.*;
import lombok.experimental.*;

import static lombok.AccessLevel.*;


@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Jwt {

    private String accessToken;

    private String refreshToken;

}
