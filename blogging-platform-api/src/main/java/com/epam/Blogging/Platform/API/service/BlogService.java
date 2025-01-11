package com.epam.Blogging.Platform.API.service;

import com.epam.Blogging.Platform.API.model.Blog;

import java.util.List;

public interface BlogService {
    Blog post(Blog blog);

    Blog update(final Integer id, Blog blog);

    void delete(final Integer id);

    Blog getBlog(final Integer id);

    List<Blog> getBlogs();

    List<Blog> getBlogsByCustomFilter(String matchingTitle);
}
