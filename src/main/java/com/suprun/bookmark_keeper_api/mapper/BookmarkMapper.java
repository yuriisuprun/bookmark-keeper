package com.suprun.bookmark_keeper_api.mapper;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import com.suprun.bookmark_keeper_api.dto.BookmarkDTO;
import org.springframework.stereotype.Component;

@Component
public class BookmarkMapper {

    public BookmarkDTO toDTO(Bookmark bookmark) {
        return new BookmarkDTO(bookmark.getId(), bookmark.getTitle(), bookmark.getUrl(), bookmark.getCreatedAt());
    }
}
