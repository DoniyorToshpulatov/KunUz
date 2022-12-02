package com.example.service;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exp.AlreadyExistObject;
import com.example.exp.ItemNotFoundException;
import com.example.repositroy.ProfileRepository;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    public ProfileDTO create(ProfileDTO profileDTO, JwtDTO jwtDTO) {

        if (!jwtDTO.getRole().equals(ProfileRole.ADMIN)) {
            throw new RuntimeException("Method Not Allowed");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(profileDTO.getName());
        entity.setSurname(profileDTO.getSurname());
        entity.setPhone(profileDTO.getPhone());
        entity.setPassword(MD5Util.encode(profileDTO.getPassword()));

        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(ProfileRole.USER);
        entity.setPrtId(jwtDTO.getId());
        entity.setVisible(Boolean.TRUE);
        profileRepository.save(entity);

        profileDTO.setId(entity.getId());
        return profileDTO;
    }

    public ProfileDTO registration(ProfileDTO profileDTO) {
        ProfileEntity  profile = profileRepository.findByPhone(profileDTO.getPhone());
        if(profile!=null){
            throw new AlreadyExistObject("Is object already exist !!!");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(profileDTO.getName());
        entity.setSurname(profileDTO.getSurname());
        entity.setPhone(profileDTO.getPhone());
        entity.setPassword(MD5Util.encode(profileDTO.getPassword()));

        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(ProfileRole.USER);
        entity.setVisible(true);
        profileRepository.save(entity);

        profileDTO.setId(entity.getId());
        return profileDTO;
    }
    public ProfileDTO createPublisher(ProfileDTO profileDTO,JwtDTO jwtDTO) {

        if(!jwtDTO.getRole().equals(ProfileRole.ADMIN)){
            throw new AlreadyExistObject("You is not Admin!!!");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(profileDTO.getName());
        entity.setSurname(profileDTO.getSurname());
        entity.setPhone(profileDTO.getPhone());
        entity.setPassword(MD5Util.encode(profileDTO.getPassword()));

        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setRole(ProfileRole.valueOf(profileDTO.getRole().toUpperCase()));
        entity.setVisible(true);
        profileRepository.save(entity);

        profileDTO.setId(entity.getId());
        return profileDTO;
    }
    public ProfileDTO updateAdmin(ProfileDTO profileDTO,JwtDTO jwtDTO) {

        if(!jwtDTO.getRole().equals(ProfileRole.ADMIN)){
            throw new AlreadyExistObject("You is not Admin!!!");
        }
        if(profileDTO.getVisible()==1){
            profileRepository.updateAdmin(true, profileDTO.getStatus().toUpperCase());
        }else {
            profileRepository.updateAdmin(false,profileDTO.getStatus().toUpperCase());
        }

        return profileDTO;
    }

    public ProfileDTO update(ProfileDTO profileDTO,JwtDTO jwtDTO) {
        Integer update = profileRepository.update(profileDTO.getName(), profileDTO.getSurname(), profileDTO.getPhone(),
                MD5Util.encode(profileDTO.getPassword()), jwtDTO.getId(),true,ProfileStatus.ACTIVE.name());
        if(update==0){
            throw new RuntimeException("No update!!");
        }
        return profileDTO;
    }


}
