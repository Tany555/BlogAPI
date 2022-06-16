package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponce;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "CRUD services for Post REST API")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post REST API

    @ApiOperation(value = "create post REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity <PostDto> createPost(@Valid @RequestBody PostDto postDto){

        System.out.println(" Hi" );

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @ApiOperation(value = "Get all post REST API")
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public PostResponce GetAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false)String sortDir
    ){

        return postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
    }
    @ApiOperation(value = "Get post by ID REST API")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){

        return ResponseEntity.ok(postService.getPostByID(id));

    }
    @ApiOperation(value = "Update post by ID REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public PostDto updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") Long id){

        return postService.updatePost(postDto,id);


    }
    @ApiOperation(value = "Delete post by ID REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);

        return new ResponseEntity <>("Post entity deleted successfully", HttpStatus.OK);
    }
}
