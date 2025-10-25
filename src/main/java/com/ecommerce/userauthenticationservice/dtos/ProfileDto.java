package com.ecommerce.userauthenticationservice.dtos;

import com.ecommerce.userauthenticationservice.models.Roles;
import com.ecommerce.userauthenticationservice.models.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ProfileDto {

    private String name;
    private String email;
    private String password;
    private List<Roles> roles;
    private String phoneNumber;
    private Date createdDate;
    private Status userStatus;
    private Integer sessionCount;
    private List<SessionInfoDto> sesInfo;
}
