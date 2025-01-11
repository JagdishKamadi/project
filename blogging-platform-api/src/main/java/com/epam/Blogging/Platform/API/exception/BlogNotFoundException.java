package com.epam.Blogging.Platform.API.exception;

public class BlogNotFoundException extends RuntimeException {
    public BlogNotFoundException() {
        super("Blog not found for this id ");
    }

    public BlogNotFoundException(Integer id) {
        super("Blog not found for this id " + id);
    }
}
