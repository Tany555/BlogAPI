package com.springboot.blog.payload;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SignupDto {

    private String name;
    private String username;
    private String email;
    private String password;

}
