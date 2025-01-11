package com.epam.Blogging.Platform.API.service;

import com.epam.Blogging.Platform.API.exception.BlogNotFoundException;
import com.epam.Blogging.Platform.API.model.Blog;
import com.epam.Blogging.Platform.API.repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private ZonedDateTime now;

    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
        now = ZonedDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public Blog post(Blog blog) {
        blog.setCreatedAt(now.format(DateTimeFormatter.ISO_INSTANT));
        blog.setUpdatedAt(now.format(DateTimeFormatter.ISO_INSTANT));
        return blogRepository.save(blog);
    }

    @Override
    public Blog update(Integer id, Blog blog) {
        Blog blogToUpdate = blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException(id));
        validator(blog, blogToUpdate);
        return blogRepository.save(blogToUpdate);
    }

    @Override
    public void delete(Integer id) {
        blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException(id));
        blogRepository.deleteById(id);
    }

    @Override
    public Blog getBlog(Integer id) {
        return blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException(id));
    }

    @Override
    public List<Blog> getBlogs() {
        return blogRepository.findAll();
    }

    @Override
    public List<Blog> getBlogsByCustomFilter(String stringToFilter) {
        String term = "%" + stringToFilter + "%";
        return blogRepository.findBlogByCustomTerm(term);
    }

    private void validator(Blog blog, Blog blogToUpdate) {
        if (!blog.getTitle().isBlank()) {
            blogToUpdate.setTitle(blog.getTitle().strip());
        }
        if (!blog.getContent().isBlank()) {
            blogToUpdate.setContent(blog.getContent().strip());
        }
        if (Objects.nonNull(blog.getTags()) && !blog.getTags().isEmpty()) {
            blogToUpdate.setTags(blog.getTags());
        }
        blogToUpdate.setUpdatedAt(now.format(DateTimeFormatter.ISO_INSTANT));
    }
}
