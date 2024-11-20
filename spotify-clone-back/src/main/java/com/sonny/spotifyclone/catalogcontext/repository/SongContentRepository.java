package com.sonny.spotifyclone.catalogcontext.repository;

import com.sonny.spotifyclone.catalogcontext.domain.SongContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongContentRepository extends JpaRepository<SongContent, Long> {

}
