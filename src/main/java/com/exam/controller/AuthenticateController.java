package com.exam.controller;

import com.exam.config.JwtUtils;
import com.exam.helper.UserNotFoundException;
import com.exam.model.JwtRequest;
import com.exam.model.Jwtresponse;
import com.exam.model.User;
import com.exam.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    //generate token

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try{
            authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());

        }catch (UserNotFoundException e)
        {
            e.printStackTrace();
            throw new Exception("User not found");
        }
        UserDetails userDetails1 =  this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails1);
        return  ResponseEntity.ok(new Jwtresponse(token));
    }
    private void authenticate(String Username,String password) throws Exception {
            try {
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(Username,password));
            }catch (DisabledException e)
            {
                throw new Exception("USER DISABLED"+ e.getMessage());
            }catch (BadCredentialsException e)
            {
                throw new Exception("Invalid Credentials"+ e.getMessage());
            }

    }

    //return logged in user

    @GetMapping("/current-user")
    public User getCurrentuser(Principal principal)
    {
        return ((User)this.userDetailsService.loadUserByUsername((principal.getName())));
    }


}
