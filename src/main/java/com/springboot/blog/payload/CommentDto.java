package com.springboot.blog.payload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Api("Comment model information")
@Data
public class CommentDto {

    @ApiModelProperty("Comment ID")
    private Long id;

    @ApiModelProperty("Comment name")
    @NotEmpty
    @Size(min = 2, message = "Name should be at least 2 character long")
    private String name;

    @ApiModelProperty("Comment email")
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;

    @ApiModelProperty("Comment body")
    @NotEmpty
    @Size(min = 10, message = "Comments body should be at least 10 character long")
    private String body;

}
