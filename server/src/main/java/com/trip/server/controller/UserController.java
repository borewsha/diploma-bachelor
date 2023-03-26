package com.trip.server.controller;

import com.trip.server.service.*;
import io.swagger.v3.oas.annotations.tags.*;
import lombok.*;
import org.modelmapper.*;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "User")
@RequestMapping("/api/users")
@Validated
public class UserController extends ApiController {

    private final ModelMapper modelMapper;

    private final UserService userService;

}
