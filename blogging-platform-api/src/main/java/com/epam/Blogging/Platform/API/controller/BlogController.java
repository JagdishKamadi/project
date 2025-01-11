package com.epam.Blogging.Platform.API.controller;

import com.epam.Blogging.Platform.API.model.Blog;
import com.epam.Blogging.Platform.API.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<Blog> post(@RequestBody Blog blog) {
        return new ResponseEntity<>(blogService.post(blog), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> update(@PathVariable Integer id, @RequestBody Blog blog) {
        return new ResponseEntity<>(blogService.update(id, blog), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        blogService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable Integer id) {
        return new ResponseEntity<>(blogService.getBlog(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Blog>> getBlogs(@RequestParam(required = false, value = "term") String term) {
        if (term != null) {
            return new ResponseEntity<>(blogService.getBlogsByCustomFilter(term), HttpStatus.OK);
        }
        return new ResponseEntity<>(blogService.getBlogs(), HttpStatus.OK);
    }

}
