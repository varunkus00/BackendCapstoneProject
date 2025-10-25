package com.ecommerce.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDto {

    private String newPassword;
    private String confirmPassword;

}
