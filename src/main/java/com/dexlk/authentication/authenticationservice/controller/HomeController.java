package com.dexlk.authentication.authenticationservice.controller;

import com.dexlk.authentication.authenticationservice.model.*;
import com.dexlk.authentication.authenticationservice.service.UserService;
import com.dexlk.authentication.authenticationservice.utility.JWTUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/")
public class HomeController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/saveUser")
    public void saveWallet(@RequestBody UserCredential userCredential) {
        userService.saveUser(userCredential);
    }

    @GetMapping("/auth")
    public String home() {
        return "Successfully Authenticated";
    }

    @PostMapping("/auth/validate")
    public ValidationResponse validateUser(@RequestBody TokenRequest request) {
        ValidationResponse validationResponse = new ValidationResponse();
        String userName = jwtUtility.getUsernameFromToken(request.getToken());

        for (UserCredential userCredential : userService.getusernames()) {
            if (userName.equals(userCredential.getUsername())) {
                validationResponse.setResponse("true");
                validationResponse.setUserId(userName);
            }
        }
        return validationResponse;

    }

    @PostMapping("/auth/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(jwtRequest.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
