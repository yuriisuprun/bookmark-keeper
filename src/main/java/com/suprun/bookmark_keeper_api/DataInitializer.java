package com.suprun.bookmark_keeper_api;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import com.suprun.bookmark_keeper_api.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public void run(String... args) throws Exception {
        bookmarkRepository.save(new Bookmark(null, "Oracle", "https://blogs.oracle.com/java/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "SpringBlog", "https://spring.io/blog/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Quarkus", "https://quarkus.io/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Micronaut", "https://micronaut.io/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "JOOQ", "https://blog.jooq.org/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "InRelationTo", "https://in.relation.to/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Hibernate", "https://hibernate.org/blog/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Jaxender", "https://jaxenter.de/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Heise", "https://www.heise.de/devops/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "KubernetesDocs", "https://kubernetes.io/docs/home/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Nodejs", "https://nodejs.org/en/blog/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Infog", "https://www.infoq.com/de/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Entwickler", "https://entwickler.de/", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "ComputerWeekly", "https://www.computerweekly.com/europe", Instant.now()));
        bookmarkRepository.save(new Bookmark(null, "Ubuntu", "https://ubuntu.com/blog", Instant.now()));

    }
}
