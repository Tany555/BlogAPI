package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {


   private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<CommentDto>  createComment(@PathVariable(value = "postId") Long postId, @Valid @RequestBody CommentDto commentDto){


        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);

    }

    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostID(@PathVariable(value = "postId") Long postId){

        return  commentService.getAllCommentsByPostId(postId);


    }

    @GetMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByCommentID(@PathVariable(value = "postId") Long postId,
                                                  @PathVariable(value = "commentId") Long commentId){

        CommentDto commentDto = commentService.getCommentById(postId,commentId);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @DeleteMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "commentId") Long commentId){
        commentService.deleteCommentByID(postId,commentId);

        return new ResponseEntity <>("Comment entity for Id: "+commentId+" deleted successfully for Post Id: "+postId, HttpStatus.OK);
    }


    @PutMapping("posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") Long postId,
                                                    @PathVariable(name = "commentId") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto){
        CommentDto updatedComment = commentService.updateComment(postId,commentId,commentDto);

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

}
