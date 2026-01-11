package com.suprun.bookmark_keeper_api.service;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import com.suprun.bookmark_keeper_api.dto.BookmarkDTO;
import com.suprun.bookmark_keeper_api.dto.BookmarksDTO;
import com.suprun.bookmark_keeper_api.dto.CreateBookmarkRequest;
import com.suprun.bookmark_keeper_api.mapper.BookmarkMapper;
import com.suprun.bookmark_keeper_api.repository.BookmarkRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;


@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final BookmarkMapper bookmarkMapper;

    @Transactional(readOnly = true)
    public BookmarksDTO getBookmarks(Integer page) {
        int pageNumber = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.Direction.DESC, "createdAt");
        Page<BookmarkDTO> bookmarkPage = bookmarkRepository.findBookmarks(pageable);
        return new BookmarksDTO(bookmarkPage);
    }

    @Transactional(readOnly = true)
    public BookmarksDTO searchBookmarks(String query, Integer page) {
        int pageNumber = page < 1 ? 0 : page - 1;
        Pageable pageable = PageRequest.of(pageNumber, 10, Sort.Direction.DESC, "createdAt");
        Page<BookmarkDTO> bookmarkPage = bookmarkRepository.findByTitleContainsIgnoreCase(query, pageable);
        return new BookmarksDTO(bookmarkPage);
    }

    public BookmarkDTO createBookmark(@Valid CreateBookmarkRequest request) {
        Bookmark bookmark = new Bookmark(null, request.getTitle(), request.getUrl(), Instant.now());
        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
        return bookmarkMapper.toDTO(savedBookmark);
    }
}
