package com.suprun.bookmark_keeper_api.controller;

import com.suprun.bookmark_keeper_api.dto.BookmarkDTO;
import com.suprun.bookmark_keeper_api.dto.CreateBookmarkRequest;
import com.suprun.bookmark_keeper_api.dto.BookmarksDTO;
import com.suprun.bookmark_keeper_api.service.BookmarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public BookmarksDTO getBookmarks(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "query", defaultValue = "") String query) {
        if (query == null || query.trim().isEmpty()) {
            return bookmarkService.getBookmarks(page);
        }
        return bookmarkService.searchBookmarks(query, page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BookmarkDTO createBookmark(@RequestBody @Valid CreateBookmarkRequest request) {
        return bookmarkService.createBookmark(request);
    }
}
