package com.springboot.blog.payload;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {

    private long id;

    @NotEmpty
    @Size(min = 2, message = "Title should be at least 2 character long")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Description should be at least 10 character long")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
