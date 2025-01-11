package com.epam.Blogging.Platform.API.service;

import com.epam.Blogging.Platform.API.exception.BlogNotFoundException;
import com.epam.Blogging.Platform.API.model.Blog;
import com.epam.Blogging.Platform.API.repository.BlogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BlogServiceImplTest {

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogServiceImpl blogService;

    private Blog blog;
    private List<Blog> blogs;

    @BeforeEach
    void setUp() {
        blog = Blog.builder()
                .id(1)
                .title("Java Tutorial")
                .content("This tutorial contains the core java and advance java concepts!")
                .tags(List.of("Java", "Advance-Java"))
                .build();

        blogs = List.of(blog);
    }

    @Test
    void testPost() {
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);
        Blog blog1 = blogService.post(blog);
        assertNotNull(blog1);
        assertAll("Checking all properties",
                () -> assertEquals(1, blog1.getId()),
                () -> assertEquals("Java Tutorial", blog1.getTitle()),
                () -> assertEquals("This tutorial contains the core java and advance java concepts!", blog1.getContent()),
                () -> assertEquals(List.of("Java", "Advance-Java"), blog1.getTags())
        );
    }

    @Test
    void testUpdateWhenBlogIdIsValid() {
        when(blogRepository.findById(1)).thenReturn(Optional.ofNullable(blog));
        when(blogRepository.save(blog)).thenReturn(blog);
        Blog blog1 = blogService.update(1, blog);
        assertNotNull(blog1);
        assertAll("Checking all properties",
                () -> assertEquals(1, blog1.getId()),
                () -> assertEquals("Java Tutorial", blog1.getTitle()),
                () -> assertEquals("This tutorial contains the core java and advance java concepts!", blog1.getContent()),
                () -> assertEquals(List.of("Java", "Advance-Java"), blog1.getTags())
        );
    }

    @Test
    void testUpdateWhenBlogIdIsInvalidThenThrowsBlogNotFoundException() {
        when(blogRepository.findById(4)).thenThrow(BlogNotFoundException.class);
        assertThrows(BlogNotFoundException.class, () -> blogService.update(4, blog));
    }

    @Test
    void testDeleteWhenBlogIdIsValid() {
        when(blogRepository.findById(1)).thenReturn(Optional.ofNullable(blog));

        blogService.delete(1);

        verify(blogRepository, times(1)).findById(1);
        verify(blogRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteWhenBlogIdIsInvalidThenThrowsBlogNotFoundException() {
        when(blogRepository.findById(1)).thenThrow(BlogNotFoundException.class);

        assertThrows(BlogNotFoundException.class, () -> blogService.delete(1));

        verify(blogRepository, times(1)).findById(1);
        verify(blogRepository, never()).deleteById(1);
    }

    @Test
    void testGetBlogWhenBlogIdIsValid() {
        when(blogRepository.findById(anyInt())).thenReturn(Optional.ofNullable(blog));
        assertNotNull(blogService.getBlog(anyInt()));
    }

    @Test
    void testGetBlogWhenBlogIdIsInvalidThenThrowsBlogNotFoundException() {
        when(blogRepository.findById(anyInt())).thenThrow(BlogNotFoundException.class);
        assertThrows(BlogNotFoundException.class, () -> blogService.getBlog(anyInt()));
    }

    @Test
    void testGetBlogs() {
        when(blogRepository.findAll()).thenReturn(blogs);
        assertNotNull(blogService.getBlogs());
        assertEquals(1, blogs.size());
    }

    @Test
    void testGetBlogsWhenNoDataPresentInDb() {
        when(blogRepository.findAll()).thenReturn(List.of());
        assertNotNull(blogService.getBlogs());
    }

    @Test
    void testGetBlogsByCustomFilterWhenStringToFilterIsMatching() {
        when(blogRepository.findBlogByCustomTerm(anyString())).thenReturn(blogs);
        assertNotNull(blogService.getBlogsByCustomFilter(anyString()));
    }

    @Test
    void testGetBlogsByCustomFilterWhenStringToFilterIsNotMatching() {
        when(blogRepository.findBlogByCustomTerm(anyString())).thenReturn(List.of());
        List<Blog> blogs1 = blogService.getBlogsByCustomFilter(anyString());
        assertNotNull(blogs1);
        assertEquals(0, blogs1.size());
    }
}