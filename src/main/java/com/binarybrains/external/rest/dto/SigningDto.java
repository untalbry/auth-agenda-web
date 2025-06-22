package com.binarybrains.external.rest.dto;

import com.binarybrains.core.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "Signing", description = "Users information to register")
public class SigningDto {
    @NotEmpty(message = "RN001")
    @Schema(description = "User name")
    private String name;

    @NotEmpty(message = "RN001")
    @Schema(description = "User last name")
    private String lastName;

    @NotEmpty(message = "RN001")
    @Schema(description = "User second last name")
    private String secondLastName;

    @NotEmpty(message = "RN001")
    @Email(message = "RN002")
    @Schema(description = "users email", required = true)
    private String email;

    @NotEmpty(message = "RN001")
    @Pattern(message = "RN002", regexp = "((([A-Za-z0-9+/]{4})*)([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==))")
    @Schema(description = "Access password")
    private String password;

    public User toEntity(){
        return User.builder()
                .name(name)
                .lastName(lastName)
                .secondLastName(secondLastName)
                .email(email)
                .password(password)
                .build();
    }
    public static SigningDto fromEntity(User user){
        return SigningDto.builder()
                .name(user.getName())
                .lastName(user.getLastName())
                .secondLastName(user.getSecondLastName())
                .email(user.getEmail())
                .build();
    }
}
