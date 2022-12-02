package com.example.service;

import com.example.dto.AuthorizationDTO;
import com.example.dto.AuthorizationResponseDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import com.example.repositroy.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;

    public AuthorizationResponseDTO authorization(AuthorizationDTO dto) {

        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndPassword(dto.getPhone(), MD5Util.encode(dto.getPassword()));
        if (optional.isEmpty()) {
            throw new RuntimeException("Login or Password wrong.");
        }

        ProfileEntity profile = optional.get();
        if (!profile.getStatus().equals(ProfileStatus.ACTIVE)) {
            throw new RuntimeException("Profile Not Active");
        }

        AuthorizationResponseDTO respose = new AuthorizationResponseDTO();
        respose.setName(profile.getName());
        respose.setSurname(profile.getSurname());
        respose.setRole(profile.getRole());
        respose.setToken(JwtUtil.encode(profile.getId(), profile.getRole()));

        return respose;
    }
}
