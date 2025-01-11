package com.epam.Blogging.Platform.API.controller;

import com.epam.Blogging.Platform.API.exception.BlogNotFoundException;
import com.epam.Blogging.Platform.API.model.Blog;
import com.epam.Blogging.Platform.API.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {

    @MockBean
    private BlogService blogService;

    @InjectMocks
    private BlogController blogController;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Blog blog;
    private List<Blog> blogs;
    private String blogDataAsString;
    Integer notExistedId;

    @BeforeEach
    void setUp() throws Exception {
        blog = Blog.builder()
                .id(1)
                .title("Java Tutorial")
                .content("This tutorial contains the core java and advance java concepts!")
                .tags(List.of("Java", "Advance-Java"))
                .build();

        blogs = List.of(blog);

        objectMapper = new ObjectMapper();
        blogDataAsString = objectMapper.writeValueAsString(blog);

        notExistedId = 5;
    }

    @Test
    void testPost() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(blogDataAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    void testUpdateWhenBlogIdIsValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/posts/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(blogDataAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testUpdateWhenBlogIdIsInvalidThenStatusCodeIs404() throws Exception {
        doThrow(new BlogNotFoundException(notExistedId)).when(blogService).update(notExistedId, blog);
        mockMvc.perform(MockMvcRequestBuilders.put("/posts/{id}", notExistedId)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(blogDataAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteWhenBlogIdIsInvalidThenStatusCodeIs404() throws Exception {
        doThrow(new BlogNotFoundException(notExistedId)).when(blogService).delete(notExistedId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/posts/{id}", notExistedId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBlog() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBlogWhenBlogIdIsInvalidThenStatusCodeIs404() throws Exception {
        doThrow(new BlogNotFoundException(notExistedId)).when(blogService).getBlog(notExistedId);
        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{id}", notExistedId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBlogsWhenDoNotPassAnyFilterPattern() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBlogsWhenPassedFilterPattern() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/posts")
                        .param("term", "Tech"))
                .andExpect(status().isOk());
    }
}