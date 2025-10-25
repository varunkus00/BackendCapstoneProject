package com.ecommerce.userauthenticationservice.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    @ManyToMany( cascade = CascadeType.ALL )
    private List<Roles> roles = new ArrayList<>();

}
