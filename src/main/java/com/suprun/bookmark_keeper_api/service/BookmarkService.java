package com.suprun.bookmark_keeper_api.service;

import com.suprun.bookmark_keeper_api.dto.BookmarkDTO;
import com.suprun.bookmark_keeper_api.dto.BookmarksDTO;
import com.suprun.bookmark_keeper_api.mapper.BookmarkMapper;
import com.suprun.bookmark_keeper_api.repository.BookmarkRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
        Page<BookmarkDTO> bookmarkPage = bookmarkRepository.findAll(pageable).map(bookmarkMapper::toDTO);
        return new BookmarksDTO(bookmarkPage);
    }
}
