package com.suprun.bookmark_keeper_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class BookmarksDTO {

    private List<BookmarkDTO> bookmarks;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    @JsonProperty("isFirstPage")
    private boolean isFirstPage;
    @JsonProperty("isLastPage")
    private boolean isLastPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    public BookmarksDTO(Page<BookmarkDTO> bookmarkPage) {
        this.setBookmarks(bookmarkPage.getContent());
        this.setTotalElements(bookmarkPage.getTotalElements());
        this.setTotalPages(bookmarkPage.getTotalPages());
        this.setCurrentPage(bookmarkPage.getNumber() + 1);
        this.setFirstPage(bookmarkPage.isFirst());
        this.setLastPage(bookmarkPage.isLast());
        this.setHasNextPage(bookmarkPage.hasNext());
        this.setHasPreviousPage(bookmarkPage.hasPrevious());
    }
}
