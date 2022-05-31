package com.springboot.blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{

   private HttpStatus HTTPStatus;
   private String message;

    public BlogAPIException(HttpStatus HTTPStatus, String message) {
        this.HTTPStatus = HTTPStatus;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus HTTPStatus, String message1) {
        super(message);
        this.HTTPStatus = HTTPStatus;
        this.message = message1;
    }

    public HttpStatus getHTTPStatus() {
        return HTTPStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
