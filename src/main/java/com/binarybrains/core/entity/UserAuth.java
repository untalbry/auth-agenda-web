package com.binarybrains.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class UserAuth {
    private Integer id;
    private String name;
    private String lastName;
    private String secondLastName;
    private String token;
    private List<Rol> roles;
}
