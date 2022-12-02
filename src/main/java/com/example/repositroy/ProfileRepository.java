package com.example.repositroy;

import com.example.entity.ProfileEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends PagingAndSortingRepository<ProfileEntity,Integer> {

    Optional<ProfileEntity> findByPhoneAndPassword(String phone, String password);

    ProfileEntity findByPhone(String phone);

    @Query("update ProfileEntity set visible=:visible,status=:status")
    Integer updateAdmin(@Param("visible")Boolean visible,@Param("status")String status);

    @Query("update ProfileEntity set name=:name,surname=:surname,phone=:phone,password=:password where id=:uId and status=:status and visible=:visible")
    Integer update(@Param("name")String name,@Param("surname")String surname,@Param("phone")String phone,@Param("password")String password,@Param("uId")Integer uId,@Param("visible")Boolean visible,@Param("status")String status);

}
