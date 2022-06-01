package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Name should be at least 2 character long")
    private String name;
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;
    @NotEmpty
    @Size(min = 10, message = "Comments body should be at least 10 character long")
    private String body;

}
