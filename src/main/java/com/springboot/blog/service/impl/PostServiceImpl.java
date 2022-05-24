package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //convert dto to entity

        Post post = mapToPost(postDto);

        Post newPost = postRepository.save(post);

        //convert entity to dto

        PostDto postResponse = maptoDto(post);


        return postResponse;
    }


    private PostDto maptoDto (Post post){

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());

        return postDto;
    }

    private Post mapToPost(PostDto postDto){

        Post post =new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;

    }

    @Override
    public List<PostDto> getAllPost(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        return listOfPosts.stream().map(post -> maptoDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostByID(Long Id) {

        Post post = postRepository.findById(Id).orElseThrow(()-> new ResourceNotFoundException("Post", "id",Id));

        return maptoDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id",id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        postRepository.save(post);

        return maptoDto(post);
    }

    @Override
    public void deletePostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id",id));
        postRepository.deleteById(id);

    }


}
