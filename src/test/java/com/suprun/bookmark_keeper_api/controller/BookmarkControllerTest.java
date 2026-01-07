package com.suprun.bookmark_keeper_api.controller;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import com.suprun.bookmark_keeper_api.repository.BookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.CoreMatchers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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

    @Test
    void shouldGetBookmarks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookmarks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(15)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(2)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(1)))
                .andExpect(jsonPath("$.isFirstPage", CoreMatchers.equalTo(true)))
                .andExpect(jsonPath("$.isLastPage", CoreMatchers.equalTo(false)))
                .andExpect(jsonPath("$.hasNextPage", CoreMatchers.equalTo(true)))
                .andExpect(jsonPath("$.hasPreviousPage", CoreMatchers.equalTo(false)));
    }
}