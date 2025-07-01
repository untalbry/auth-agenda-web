package com.binarybrains.external.rest.dto;

import com.binarybrains.core.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "Signing", description = "User information to ")
public class SigningDto {
    @NotEmpty(message = "RN0001")
    @Email(message = "RN002")
    @Schema(description = "User email")
    private String email;
    @NotEmpty(message = "RN001")
    @Pattern(message = "RN002",  regexp = "((([A-Za-z0-9+/]{4})*)([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==))")
    @Schema(description = "Access password")
    private String password;
    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .build();
    }
}
