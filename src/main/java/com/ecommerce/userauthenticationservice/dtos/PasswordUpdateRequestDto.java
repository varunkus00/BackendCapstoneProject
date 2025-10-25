package com.ecommerce.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequestDto {

    private String oldPassword;
    private String newPassword;

}
