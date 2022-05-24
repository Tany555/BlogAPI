package com.springboot.blog.controller;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post REST API

    @PostMapping
    public ResponseEntity <PostDto> createPost(@RequestBody PostDto postDto){

        System.out.println(" Hi" );

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<PostDto> GetAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)int pageSize
    ){

        return postService.getAllPost(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id){

        return ResponseEntity.ok(postService.getPostByID(id));

    }

    @PutMapping("/{id}")
    public PostDto updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") Long id){

        return postService.updatePost(postDto,id);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);

        return new ResponseEntity <>("Post entity deleted successfully", HttpStatus.OK);
    }
}
