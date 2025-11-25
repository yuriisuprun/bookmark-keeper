package com.suprun.bookmark_keeper_api.controller;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import com.suprun.bookmark_keeper_api.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public List<Bookmark> getBookmarks() {
        return bookmarkService.getBookmarks();
    }
}
