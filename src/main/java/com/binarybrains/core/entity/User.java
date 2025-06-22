package com.binarybrains.core.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class User {
    private Integer id;
    private String name;
    private String lastName;
    private String secondLastName;
    private String email;
    private String password;
}
