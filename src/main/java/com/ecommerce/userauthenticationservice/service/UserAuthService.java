package com.ecommerce.userauthenticationservice.service;

import com.ecommerce.userauthenticationservice.dtos.ResetPasswordRequestDto;
import com.ecommerce.userauthenticationservice.exception.*;
import com.ecommerce.userauthenticationservice.models.Session;
import com.ecommerce.userauthenticationservice.models.User;
import com.ecommerce.userauthenticationservice.models.Status;
import com.ecommerce.userauthenticationservice.repos.SessionRepo;
import com.ecommerce.userauthenticationservice.repos.UserAuthRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserAuthService implements  IAuthService {

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserAuthRepo userAuthRepo;

    @Autowired
    public SessionRepo sessionRepo;

    @Override
    public Pair<User, String> login(String email, String password) {
        Optional<User>  userOp = userAuthRepo.findByEmail(email);
        if(  !userOp.isPresent() ){
            throw new UserNotFoundException("There is no user with email : " + email);
        }

        User user = userOp.get();
        if( !bCryptPasswordEncoder.matches(password, user.getPassword()) ){
            throw new InvalidCredentialsException(" Sorry Incorrect Username or Password !!!");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", user.getId());
        claims.put("issued_by", "scaler");
        claims.put("iat", System.currentTimeMillis());
        claims.put("exp", System.currentTimeMillis()+3600);

        MacAlgorithm algo = Jwts.SIG.HS256;
        SecretKey secretKey = algo.key().build();

        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setStatus(Status.ACTIVE);
        //session.setCreated_date( System );
        sessionRepo.save(session);

        return new Pair<User, String>(user, token);
    }

    @Override
    public User logoutByEmail( String token, String email ) {
        Optional<User>  userOp = userAuthRepo.findByEmail(email);
        if(  !userOp.isPresent() ){
            throw new UserNotFoundException("There is no user with email : " + email);
        }

        User user = userOp.get();

        Optional<Session> sessionOp = sessionRepo.findByToken(token);
        if( sessionOp.isPresent() ){
            sessionRepo.delete(sessionOp.get());
        } else {
            throw new AuthorizationTokenNotFound(" Ahh! No Such Token Found!");
        }

        return user;
    }

    @Override
    public User signUp(User user) {
        Optional<User> userOp = userAuthRepo.findByEmail(user.getEmail());
        if(userOp.isPresent()){
            throw new UserAlreadyExistException(" There is already a user with email : " + user.getEmail());
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword( bCryptPasswordEncoder.encode(user.getPassword()));

        System.out.println(newUser.getName() + newUser.getPassword());
        return userAuthRepo.save(newUser);
        //return newUser;
    }

    @Override
    public User resetProfilePassword(String email, ResetPasswordRequestDto request) {

        if( !request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new PasswordDoesNotMatchException(" Please enter same passwords !! ");
        }

        Optional<User> userOp = userAuthRepo.findByEmail(email);
        if( !userOp.isPresent() ){
            throw new UserNotFoundException(" No users found with email : " + email);
        }

        User newUser = userOp.get();
        //TO-DO ?? Add logic for password validation
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));
        return userAuthRepo.save(newUser);
    }
}
