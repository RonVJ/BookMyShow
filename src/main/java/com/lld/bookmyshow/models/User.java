package com.lld.bookmyshow.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private Long age;
    private String email;
    private String phoneNumber;
    private String userName;
    private String password;
}
