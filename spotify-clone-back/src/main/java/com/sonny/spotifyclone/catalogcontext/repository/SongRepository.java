package com.sonny.spotifyclone.catalogcontext.repository;

import com.sonny.spotifyclone.catalogcontext.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {

}
