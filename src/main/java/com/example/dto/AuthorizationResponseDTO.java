package com.example.dto;

import com.example.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;

@Getter
@Setter
public class AuthorizationResponseDTO {
    private String name;
    private String surname;
    private ProfileRole role;
    private String token;

}
