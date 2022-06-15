package com.springboot.blog.controller;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignupDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){

        System.out.println("Auth controler 1");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        System.out.println("Auth controler getting context");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        System.out.println("Auth controler getting token from token provider");
        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println("Auth controler returning token");
        return ResponseEntity.ok(new JWTAuthResponse(token));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signupDto){

        if (userRepository.existsByUsername(signupDto.getUsername())){
            return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signupDto.getEmail())){
            return new ResponseEntity<>("Email Id already exist", HttpStatus.BAD_REQUEST);
        }

        User user = new User();

        user.setName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);


    }


}
