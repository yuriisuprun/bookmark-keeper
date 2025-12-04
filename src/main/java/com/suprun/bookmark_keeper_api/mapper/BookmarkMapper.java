package com.suprun.bookmark_keeper_api.mapper;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import com.suprun.bookmark_keeper_api.dto.BookmarkDTO;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

    public BookmarkDTO toDTO(Bookmark bookmark) {
        BookmarkDTO dto = new BookmarkDTO();
        dto.setId(bookmark.getId());
        dto.setTitle(bookmark.getTitle());
        dto.setUrl(bookmark.getUrl());
        dto.setCreatedAt(bookmark.getCreatedAt());
        return dto;
    }
}
