package com.suprun.bookmark_keeper_api.dto;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class BookmarkDto {

    private List<Bookmark> bookmarks;
    private int totalElements;
    private int totalPages;
    private int currentPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    public BookmarkDto(Page<Bookmark> bookmarkPage) {
        this.setBookmarks(bookmarkPage.getContent());
        this.setTotalElements(bookmarkPage.getNumberOfElements());
        this.setTotalPages(bookmarkPage.getTotalPages());
        this.setCurrentPage(bookmarkPage.getNumber());
        this.setFirstPage(bookmarkPage.isFirst());
        this.setLastPage(bookmarkPage.isLast());
        this.setHasNextPage(bookmarkPage.hasNext());
        this.setHasPreviousPage(bookmarkPage.hasPrevious());
    }
}
