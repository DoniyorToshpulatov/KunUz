package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementRef;
import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private  String name;

    @Column(length = 20)
    private  String surname;

    @Column(length = 20)
    private  String phone;

    @Column
    private  String password;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column
    private ProfileRole role;

    @Column(columnDefinition = "boolean default true")
    private  Boolean visible;

    @Column
    private LocalDate createdDate;

    @Column
    private  String photoId;


    @Column
    private Integer prtId; //

}
