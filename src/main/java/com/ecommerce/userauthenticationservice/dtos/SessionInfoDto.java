package com.ecommerce.userauthenticationservice.dtos;

import com.ecommerce.userauthenticationservice.models.Status;
import com.ecommerce.userauthenticationservice.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionInfoDto {
    private String token;
    //private Long userId;
    private Status status;
}
