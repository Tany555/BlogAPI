package com.springboot.blog.service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponce;

public interface PostService {

    PostDto createPost (PostDto postDto);

    PostResponce getAllPost(int PageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostByID(Long Id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePostById(Long id);

}
