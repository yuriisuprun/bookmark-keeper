package com.suprun.bookmark_keeper_api.repository;

import com.suprun.bookmark_keeper_api.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {


}
