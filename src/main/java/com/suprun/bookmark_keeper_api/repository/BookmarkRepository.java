package com.suprun.bookmark_keeper_api.repository;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import com.suprun.bookmark_keeper_api.dto.BookmarkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("select new com.suprun.bookmark_keeper_api.dto.BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b")
    Page<BookmarkDTO> findBookmarks(Pageable pageable);

    Page<BookmarkDTO> findByTitleContainsIgnoreCase(String query, Pageable pageable);
}

