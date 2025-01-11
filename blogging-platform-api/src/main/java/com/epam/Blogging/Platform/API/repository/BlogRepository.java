package com.epam.Blogging.Platform.API.repository;

import com.epam.Blogging.Platform.API.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    // below is the java 17 text block  features for sql query
    // references : https://medium.com/javarevisited/java-17-vs-java-11-exploring-the-latest-features-and-improvements-6d13290e4e1a
    @Query(value = """
            SELECT *\s\
            FROM blog b\s\
            WHERE EXISTS (
            SELECT *\s\
            FROM unnest(tags) AS tag\s\
            WHERE tag ILIKE  :term OR\s\
            b.title ILIKE :term OR\s\
            b.content ILIKE :term)""",
            nativeQuery = true)
    List<Blog> findBlogByCustomTerm(String term);
}
