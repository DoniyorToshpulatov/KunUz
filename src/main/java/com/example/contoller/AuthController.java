package com.example.contoller;

import com.example.dto.AuthorizationDTO;
import com.example.dto.AuthorizationResponseDTO;
import com.example.dto.ProfileDTO;
import com.example.service.AuthService;
import com.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthorizationDTO dto) {
        AuthorizationResponseDTO response = authService.authorization(dto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO profile = profileService.registration(profileDTO);
        return ResponseEntity.ok(profile);
    }



}
