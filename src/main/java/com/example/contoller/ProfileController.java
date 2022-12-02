package com.example.contoller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.service.ProfileService;
import com.example.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<?> save(@RequestHeader("Authorization") String header, @RequestBody ProfileDTO profileDTO) {
        //   String token =   headers.get("Authorization").get(0)
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getTokenFromHeader(header));
        ProfileDTO profile = profileService.create(profileDTO, jwtDTO);
        return ResponseEntity.ok(profile);
    }
    @PostMapping("/create/admin")
    public  ResponseEntity<?> publisherCreate(@RequestHeader("Authorization") String header,@RequestBody ProfileDTO profileDTO){
        JwtDTO decode = JwtUtil.decode(JwtUtil.getTokenFromHeader(header));
        return ResponseEntity.ok(profileService.createPublisher(profileDTO,decode));
    }
    @PutMapping("/update/admin")
    public  ResponseEntity<?> updateAdmin(@RequestHeader("Authorization") String header,@RequestBody ProfileDTO profileDTO){
        JwtDTO decode = JwtUtil.decode(JwtUtil.getTokenFromHeader(header));
        return ResponseEntity.ok(profileService.updateAdmin(profileDTO,decode));
    }
    @PutMapping("/update/user")
    public  ResponseEntity<?> updateUser(@RequestHeader("Authorization") String header,@RequestBody ProfileDTO profileDTO){
        JwtDTO decode = JwtUtil.decode(JwtUtil.getTokenFromHeader(header));
        return ResponseEntity.ok(profileService.update(profileDTO,decode));
    }




}
