package com.binarybrains.external.rest.dto;

import com.binarybrains.core.entity.User;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Schema(name = "AuthenticationResponse", description = "Response object for successful user authentication.")
public class AuthenticationResponseDto {

    @Schema(description = "Details of the authenticated user.")
    public UserDto userDto;

    @Schema(description = "List of roles assigned to the authenticated user.")
    public List<RolDto> roles;

    @Schema(description = "Authentication token and related information.")
    public AuthenticationDto authentication;

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    @Schema(name = "User", description = "Represents a user's basic information.")
    private static class UserDto {
        @Schema(description = "Unique identifier for the user.")
        private Integer id;

        @Schema(description = "First name of the user.")
        private String name;

        @Schema(description = "Last name of the user.")
        private String lastName;

        @Schema(description = "Second last name of the user (optional).")
        private String secondLastName;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    @Schema(name = "Role", description = "Represents a user role within the system.")
    private static class RolDto {
        @Schema(description = "Unique identifier for the role.")
        private Integer id;

        @Schema(description = "Name of the role (e.g., 'Administrator', 'User').")
        private String name;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    @Builder
    @Schema(name = "Authentication", description = "Contains the authentication token.")
    private static class AuthenticationDto {
        @Schema(description = "The JWT (JSON Web Token) used for subsequent authenticated requests.")
        private String token;
    }
    public static AuthenticationResponseDto fromEntity(User user, String token){
        return AuthenticationResponseDto.builder()
                .userDto(UserDto.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .lastName(user.getLastName())
                                .secondLastName(user.getSecondLastName())
                                .build()
                )
                .roles(
                      new ArrayList<RolDto>()
                )
                .authentication(AuthenticationDto.builder()
                        .token(token)
                        .build())
                .build();
    }
}