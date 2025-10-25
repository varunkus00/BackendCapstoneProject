package com.ecommerce.userauthenticationservice.dtos;

import com.ecommerce.userauthenticationservice.models.Roles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private String password;
    private List<Roles> roles;
}
