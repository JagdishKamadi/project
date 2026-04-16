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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlogServiceImplTest {

    private static final int VALID_ID = 1;
    private static final int INVALID_ID = 99;
    private static final String TITLE = "Java Tutorial";
    private static final String CONTENT = "This tutorial contains the core java and advance java concepts!";
    private static final String FILTER_TERM = "Java";

    @Mock
    private BlogRepository blogRepository;

    @InjectMocks
    private BlogServiceImpl blogService;

    private Blog blog;

    @BeforeEach
    void setUp() {
        blog = Blog.builder()
                .id(VALID_ID)
                .title(TITLE)
                .content(CONTENT)
                .tags(List.of("Java", "Advance-Java"))
                .build();
    }

    // -------------------------------------------------------------------------
    // post()
    // -------------------------------------------------------------------------

    @Test
    void testPostWhenBlogIsValidThenReturnsSavedBlog() {
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        Blog result = blogService.post(blog);

        assertNotNull(result);
        assertEquals(VALID_ID, result.getId());
        assertEquals(TITLE, result.getTitle());
        assertEquals(CONTENT, result.getContent());
        assertNotNull(blog.getCreatedAt());
        assertNotNull(blog.getUpdatedAt());
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    void testPostWhenBlogSavedThenCreatedAtAndUpdatedAtAreSet() {
        when(blogRepository.save(any(Blog.class))).thenReturn(blog);

        blogService.post(blog);

        assertNotNull(blog.getCreatedAt());
        assertNotNull(blog.getUpdatedAt());
        assertEquals(blog.getCreatedAt(), blog.getUpdatedAt());
    }

    // -------------------------------------------------------------------------
    // update()
    // -------------------------------------------------------------------------

    @Test
    void testUpdateWhenBlogIdIsValidThenReturnUpdatedBlog() {
        when(blogRepository.findById(VALID_ID)).thenReturn(Optional.of(blog));
        when(blogRepository.save(blog)).thenReturn(blog);

        Blog result = blogService.update(VALID_ID, blog);

        assertNotNull(result);
        assertEquals(TITLE, result.getTitle());
        assertEquals(CONTENT, result.getContent());
        verify(blogRepository, times(1)).save(blog);
    }

    @Test
    void testUpdateWhenBlogIdIsInvalidThenThrowsBlogNotFoundException() {
        when(blogRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> blogService.update(INVALID_ID, blog));

        verify(blogRepository, never()).save(any());
    }

    @Test
    void testUpdateWhenTitleIsBlankThenTitleIsNotUpdated() {
        Blog existing = Blog.builder().id(VALID_ID).title(TITLE).content(CONTENT).tags(List.of("Java")).build();
        Blog update = Blog.builder().title("   ").content(CONTENT).tags(List.of("Spring")).build();

        when(blogRepository.findById(VALID_ID)).thenReturn(Optional.of(existing));
        when(blogRepository.save(existing)).thenReturn(existing);

        Blog result = blogService.update(VALID_ID, update);

        assertEquals(TITLE, result.getTitle()); // title must remain unchanged
    }

    @Test
    void testUpdateWhenContentIsBlankThenContentIsNotUpdated() {
        Blog existing = Blog.builder().id(VALID_ID).title(TITLE).content(CONTENT).tags(List.of("Java")).build();
        Blog update = Blog.builder().title(TITLE).content("   ").tags(List.of("Spring")).build();

        when(blogRepository.findById(VALID_ID)).thenReturn(Optional.of(existing));
        when(blogRepository.save(existing)).thenReturn(existing);

        Blog result = blogService.update(VALID_ID, update);

        assertEquals(CONTENT, result.getContent()); // content must remain unchanged
    }

    @Test
    void testUpdateWhenTagsIsNullThenTagsAreNotUpdated() {
        Blog existing = Blog.builder().id(VALID_ID).title(TITLE).content(CONTENT).tags(List.of("Java")).build();
        Blog update = Blog.builder().title(TITLE).content(CONTENT).tags(null).build();

        when(blogRepository.findById(VALID_ID)).thenReturn(Optional.of(existing));
        when(blogRepository.save(existing)).thenReturn(existing);

        Blog result = blogService.update(VALID_ID, update);

        assertEquals(List.of("Java"), result.getTags()); // tags must remain unchanged
    }

    @Test
    void testUpdateWhenTagsIsEmptyThenTagsAreNotUpdated() {
        Blog existing = Blog.builder().id(VALID_ID).title(TITLE).content(CONTENT).tags(List.of("Java")).build();
        Blog update = Blog.builder().title(TITLE).content(CONTENT).tags(Collections.emptyList()).build();

        when(blogRepository.findById(VALID_ID)).thenReturn(Optional.of(existing));
        when(blogRepository.save(existing)).thenReturn(existing);

        Blog result = blogService.update(VALID_ID, update);

        assertEquals(List.of("Java"), result.getTags()); // tags must remain unchanged
    }

    @Test
    void testUpdateWhenAllFieldsValidThenAllFieldsAreUpdated() {
        Blog existing = Blog.builder().id(VALID_ID).title("Old Title").content("Old Content").tags(List.of("Old")).build();
        Blog update = Blog.builder().title("New Title").content("New Content").tags(List.of("New")).build();

        when(blogRepository.findById(VALID_ID)).thenReturn(Optional.of(existing));
        when(blogRepository.save(existing)).thenReturn(existing);

        Blog result = blogService.update(VALID_ID, update);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Content", result.getContent());
        assertEquals(List.of("New"), result.getTags());
        assertNotNull(result.getUpdatedAt());
    }

    // -------------------------------------------------------------------------
    // delete()
    // -------------------------------------------------------------------------

    @Test
    void testDeleteWhenBlogIdIsValidThenDeletesSuccessfully() {
        when(blogRepository.findById(VALID_ID)).thenReturn(Optional.of(blog));

        blogService.delete(VALID_ID);

        verify(blogRepository, times(1)).findById(VALID_ID);
        verify(blogRepository, times(1)).deleteById(VALID_ID);
    }

    @Test
    void testDeleteWhenBlogIdIsInvalidThenThrowsBlogNotFoundException() {
        when(blogRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> blogService.delete(INVALID_ID));

        verify(blogRepository, times(1)).findById(INVALID_ID);
        verify(blogRepository, never()).deleteById(any());
    }

    // -------------------------------------------------------------------------
    // getBlog()
    // -------------------------------------------------------------------------

    @Test
    void testGetBlogWhenBlogIdIsValidThenReturnsBlog() {
        when(blogRepository.findById(VALID_ID)).thenReturn(Optional.of(blog));

        Blog result = blogService.getBlog(VALID_ID);

        assertNotNull(result);
        assertEquals(VALID_ID, result.getId());
        assertEquals(TITLE, result.getTitle());
    }

    @Test
    void testGetBlogWhenBlogIdIsInvalidThenThrowsBlogNotFoundException() {
        when(blogRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        assertThrows(BlogNotFoundException.class, () -> blogService.getBlog(INVALID_ID));
    }

    // -------------------------------------------------------------------------
    // getBlogs()
    // -------------------------------------------------------------------------

    @Test
    void testGetBlogsWhenDataExistsThenReturnsAllBlogs() {
        when(blogRepository.findAll()).thenReturn(List.of(blog));

        List<Blog> result = blogService.getBlogs();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TITLE, result.get(0).getTitle());
    }

    @Test
    void testGetBlogsWhenNoBlogsPresentThenReturnsEmptyList() {
        when(blogRepository.findAll()).thenReturn(Collections.emptyList());

        List<Blog> result = blogService.getBlogs();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // -------------------------------------------------------------------------
    // getBlogsByCustomFilter()
    // -------------------------------------------------------------------------

    @Test
    void testGetBlogsByCustomFilterWhenTermMatchesThenReturnsMatchingBlogs() {
        when(blogRepository.findBlogByCustomTerm(anyString())).thenReturn(List.of(blog));

        List<Blog> result = blogService.getBlogsByCustomFilter(FILTER_TERM);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(blogRepository, times(1)).findBlogByCustomTerm("%" + FILTER_TERM + "%");
    }

    @Test
    void testGetBlogsByCustomFilterWhenTermDoesNotMatchThenReturnsEmptyList() {
        when(blogRepository.findBlogByCustomTerm(anyString())).thenReturn(Collections.emptyList());

        List<Blog> result = blogService.getBlogsByCustomFilter("nonexistent");

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetBlogsByCustomFilterWhenEmptyStringThenReturnsAllBlogs() {
        when(blogRepository.findBlogByCustomTerm("%%")).thenReturn(List.of(blog));

        List<Blog> result = blogService.getBlogsByCustomFilter("");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(blogRepository, times(1)).findBlogByCustomTerm("%%");
    }
}