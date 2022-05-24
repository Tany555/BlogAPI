package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;

import java.util.List;

public interface PostService {

    PostDto createPost (PostDto postDto);

    List<PostDto> getAllPost(int PageNo, int pageSize);

    PostDto getPostByID(Long Id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePostById(Long id);

}
