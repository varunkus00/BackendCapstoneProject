package com.ecommerce.userauthenticationservice.service;

import com.ecommerce.userauthenticationservice.dtos.SessionInfoDto;
import com.ecommerce.userauthenticationservice.exception.ActiveSessionNotFoundException;
import com.ecommerce.userauthenticationservice.exception.UserNotFoundException;
import com.ecommerce.userauthenticationservice.models.Session;
import com.ecommerce.userauthenticationservice.models.User;
import com.ecommerce.userauthenticationservice.repos.SessionRepo;
import com.ecommerce.userauthenticationservice.repos.UserAuthRepo;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileManagementServices implements IProfileServices{

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserAuthRepo userAuthRepo;

    @Autowired
    public IAuthService authService;

    @Autowired
    public SessionRepo sessionRepo;

    @Override
    public List<Session> getUserSessionInfo(User user) {
        List<Session> sessionInfo = sessionRepo.findByUser(user);
        if( sessionInfo.isEmpty()){
            throw new ActiveSessionNotFoundException(" No Active Sessions Found!! ");
        }

        return sessionInfo;
    }

    @Override
    public User getProfile(String email) {

        Optional<User> userOp = userAuthRepo.findByEmail(email);
        if( !userOp.isPresent() ){
            throw new UserNotFoundException("User not found");
        }

        return userOp.get();

    }

    @Override
    public User modifyProfilePassword(String email, String oldPassword, String newPassword) {
            Pair<User, String> pair = authService.login(email, oldPassword);
            pair.a.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return userAuthRepo.save(pair.a);
    }

    @Override
    public User modifyPhoneNumber(String email, String phoneNumber) {
        Optional<User> userOp = userAuthRepo.findByEmail(email);
        if( !userOp.isPresent() ){
            throw new UserNotFoundException(" No users found with email : " + email);
        }
        User newUser = userOp.get();
        //TO-DO ?? Add logic for password validation
        newUser.setPhoneNumber(phoneNumber);
        return userAuthRepo.save(newUser);
    }

    @Override
    public User modifyName(String email, String name) {
        Optional<User> userOp = userAuthRepo.findByEmail(email);
        if( !userOp.isPresent() ){
            throw new UserNotFoundException(" No users found with email : " + email);
        }
        User newUser = userOp.get();
        //TO-DO ?? Add logic for password validation
        newUser.setName(name);
        return userAuthRepo.save(newUser);
    }
}
