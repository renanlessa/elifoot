package dev.java10x.elifoot.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CreateUserRequest {

    @NotBlank
    private String name;
    @NotNull
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotNull
    @NotEmpty
    private List<Long> scopes;
}
