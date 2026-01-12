package com.suprun.bookmark_keeper_api.controller;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import com.suprun.bookmark_keeper_api.repository.BookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.CoreMatchers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:14-alpine:///demo"
})
class BookmarkControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookmarkRepository bookmarkRepository;

    private List<Bookmark> bookmarks;

    @BeforeEach
    void setup() {
        bookmarkRepository.deleteAllInBatch();

        bookmarks = new ArrayList<>();
        Instant now = Instant.now();

        bookmarks.add(new Bookmark(null, "Oracle", "https://blogs.oracle.com/java/", now));
        bookmarks.add(new Bookmark(null, "SpringBlog", "https://spring.io/blog/", now));
        bookmarks.add(new Bookmark(null, "Quarkus", "https://quarkus.io/", now));
        bookmarks.add(new Bookmark(null, "Micronaut", "https://micronaut.io/", now));
        bookmarks.add(new Bookmark(null, "JOOQ", "https://blog.jooq.org/", now));
        bookmarks.add(new Bookmark(null, "InRelationTo", "https://in.relation.to/", now));
        bookmarks.add(new Bookmark(null, "Hibernate", "https://hibernate.org/blog/", now));
        bookmarks.add(new Bookmark(null, "Jaxender", "https://jaxenter.de/", now));
        bookmarks.add(new Bookmark(null, "Heise", "https://www.heise.de/devops/", now));
        bookmarks.add(new Bookmark(null, "KubernetesDocs", "https://kubernetes.io/docs/home/", now));
        bookmarks.add(new Bookmark(null, "Nodejs", "https://nodejs.org/en/blog/", now));
        bookmarks.add(new Bookmark(null, "Infog", "https://www.infoq.com/de/", now));
        bookmarks.add(new Bookmark(null, "Entwickler", "https://entwickler.de/", now));
        bookmarks.add(new Bookmark(null, "ComputerWeekly", "https://www.computerweekly.com/europe", now));
        bookmarks.add(new Bookmark(null, "Ubuntu", "https://ubuntu.com/blog", now));

        bookmarkRepository.saveAll(bookmarks);
    }

    @ParameterizedTest
    @CsvSource({
            "1,15,2,1,true,false,true,false",
            "2,15,2,2,false,true,false,true"
    })
    void shouldGetBookmarks(int pageNo, int totalElements, int totalPages, int currentPage,
                            boolean isFirstPage, boolean isLastPage,
                            boolean hasNextPage, boolean hasPreviousPage) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookmarks?page=" + pageNo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(jsonPath("$.isFirstPage", CoreMatchers.equalTo(isFirstPage)))
                .andExpect(jsonPath("$.isLastPage", CoreMatchers.equalTo(isLastPage)))
                .andExpect(jsonPath("$.hasNextPage", CoreMatchers.equalTo(hasNextPage)))
                .andExpect(jsonPath("$.hasPreviousPage", CoreMatchers.equalTo(hasPreviousPage)));
    }

    @Test
    void shouldCreateBookmarkSuccessfully() throws Exception {
        this.mockMvc.perform(
                        post("/api/bookmarks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "title": "Yurii's Blog",
                                          "url": "https://url.com"
                                        }
                                        """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("Yurii's Blog")))
                .andExpect(jsonPath("$.url", is("https://url.com")));
    }

    @Test
    void shouldFailToCreateBookmarkWhenUrlIsNotPresent() throws Exception {
        this.mockMvc.perform(
                        post("/api/bookmarks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                          "title": "Yurii's Blog"
                                        }
                                        """)
                )
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}